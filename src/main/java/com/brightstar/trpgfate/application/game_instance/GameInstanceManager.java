package com.brightstar.trpgfate.application.game_instance;

import com.brightstar.trpgfate.component.staticly.uuid.UUIDHelper;
import com.brightstar.trpgfate.config.custom_property.GameConfig;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.*;
import java.net.BindException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
public class GameInstanceManager {
    private static final class GameInstanceImpl implements GameInstance, Runnable {
        private UUID gameUuid;
        private Process process;
        private InetAddress socketAddress;
        private int socketPort;
        private Calendar createTime;
        private final HashMap<Integer, byte[]> accessKeyMap = new HashMap<>();
        private final StringBuffer stringBuffer = new StringBuffer();

        public GameInstanceImpl() {
            new Thread(this).start();
        }

        @Override
        public UUID getGameUuid() {
            return gameUuid;
        }

        public void setGameUuid(UUID gameUuid) {
            this.gameUuid = gameUuid;
        }

        @Override
        public Calendar getCreateTime() {
            return createTime;
        }

        @Override
        public String getLog() {
            return stringBuffer.toString();
        }

        public void setCreateTime(Calendar createTime) {
            this.createTime = createTime;
        }

        public Process getProcess() {
            return process;
        }

        public void setProcess(Process process) {
            this.process = process;
        }

        @Override
        public InetAddress getSocketAddress() {
            return socketAddress;
        }

        public void setSocketAddress(InetAddress socketAddress) {
            this.socketAddress = socketAddress;
        }

        @Override
        public int getSocketPort() {
            return socketPort;
        }

        public void setSocketPort(int socketPort) {
            this.socketPort = socketPort;
        }

        @Override
        public Map<Integer, byte[]> getAccessKeys() {
            return Collections.unmodifiableMap(accessKeyMap);
        }

        public void setAccessKey(int userId, UUID accessKey) {
            accessKeyMap.put(userId, UUIDHelper.toBytes(accessKey));
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (process != null && process.isAlive()) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
                    try {
                        String line = reader.readLine();
                        while (line != null) {
                            stringBuffer.append(line);
                            stringBuffer.append('\n');
                            line = reader.readLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private final ConcurrentHashMap<UUID, GameInstanceImpl> gameInstances = new ConcurrentHashMap<>();
    private final boolean[] portUsage;
    private final GameConfig gameConfig;
    private final CopyOnWriteArrayList<Consumer<? super GameInstance>> autoDestroyCallbacks = new CopyOnWriteArrayList<>();

    @Autowired
    public GameInstanceManager(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        this.portUsage = new boolean[gameConfig.getMaxPort() - gameConfig.getMinPort() + 1];
    }

    public void addInstanceAutoDestroyListener(Consumer<? super GameInstance> callback) {
        autoDestroyCallbacks.add(callback);
    }

    public void clearInstanceAutoDestroyListener() {
        autoDestroyCallbacks.clear();
    }

    public GameInstance createInstance(UUID gameUuid, LaunchConfig launchConfig) throws IOException {
        clearDeadGameInstance();
        if (gameInstances.get(gameUuid) != null) throw new IOException("The game is running.");
        GameInstanceImpl ret = new GameInstanceImpl();
        ret.setGameUuid(gameUuid);
        int usablePort = -1;
        synchronized (this.portUsage) {
            for (int i = 0; i < portUsage.length; ++i) {
                if (!portUsage[i]) {
                    usablePort = gameConfig.getMinPort() + i;
                    portUsage[i] = true;
                    break;
                }
            }
        }
        if (usablePort < 0
                || usablePort < gameConfig.getMinPort() || usablePort > gameConfig.getMaxPort()) {
            throw new BindException("There is no more available port for creating instance");
        }
        ret.setSocketPort(usablePort);
        InetAddress ipAddr = InetAddress.getByName(gameConfig.getIpAddress());
        ret.setSocketAddress(ipAddr);
        String saveDir = FilenameUtils.concat(gameConfig.getSaveLocation(), gameUuid.toString());
        String saveFilename = FilenameUtils.concat(saveDir, "save.dat");
        File dir = new File(saveDir);
        if (!dir.exists()) dir.mkdir();
        else if (!dir.isDirectory()) {
            dir.delete();
            dir.mkdir();
        }
        File file = new File(saveFilename);
        if (!file.exists()) file.createNewFile();
        else if (!file.isFile()) {
            file.delete();
            file.createNewFile();
        }
        int[] players = launchConfig.getPlayersUserId();
        UUID[] keys = new UUID[players.length + 1];
        ret.setAccessKey(launchConfig.getDmUserId(), keys[0] = UUID.randomUUID());
        int i = 1;
        for (int player : players) {
            ret.setAccessKey(player, keys[i++] = UUID.randomUUID());
        }
        LinkedList<String> commands = new LinkedList<>();
        commands.addLast(gameConfig.getServerProgram());
        commands.addLast("--port");
        commands.addLast(String.valueOf(usablePort));
        commands.addLast("--save-data");
        commands.addLast(saveFilename);
        commands.addLast("--player-keys");
        commands.addLast(String.valueOf(keys.length));
        for (UUID key : keys) {
            String hex = String.valueOf(Hex.encode(UUIDHelper.toBytes(key)));
            commands.addLast(hex);
        }
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        Process gameProcess = processBuilder.start();
        ret.setProcess(gameProcess);
        ret.setCreateTime(Calendar.getInstance());
        gameInstances.put(gameUuid, ret);
        return ret;
    }

    public GameInstance getRunningInstance(UUID gameUuid) {
        clearDeadGameInstance();
        return gameInstances.get(gameUuid);
    }

    public void forEachInstance(Consumer<? super GameInstance> action) {
        clearDeadGameInstance();
        gameInstances.forEach((uuid, gameInstance) -> action.accept(gameInstance));
    }

    public void destroyInstance(UUID gameUuid) {
        GameInstanceImpl instance = gameInstances.get(gameUuid);
        if (instance != null) {
            Process process = instance.getProcess();
            if (process.isAlive()) process.destroy();
            int port = instance.getSocketPort();
            synchronized (this.portUsage) {
                portUsage[port - gameConfig.getMinPort()] = false;
            }
            gameInstances.remove(instance.getGameUuid());
        }
    }

    @Scheduled(fixedDelay = 5000L)
    private void clearDeadGameInstance() {
        ArrayList<GameInstanceImpl> toBeDestroying = new ArrayList<>();
        gameInstances.forEach((uuid, gameInstance) -> {
            if (!gameInstance.getProcess().isAlive()) {
                toBeDestroying.add(gameInstance);
            }
        });
        for (GameInstanceImpl instance : toBeDestroying) {
            destroyInstance(instance.getGameUuid());
            for (Consumer<? super GameInstance> autoDestroyCallback : autoDestroyCallbacks) {
                autoDestroyCallback.accept(instance);
            }
        }
    }

    @PreDestroy
    private void destroy() {
        Collection<GameInstanceImpl> collection = gameInstances.values();
        GameInstanceImpl[] instances = new GameInstanceImpl[collection.size()];
        instances = collection.toArray(instances);
        for (GameInstanceImpl instance : instances) {
            destroyInstance(instance.getGameUuid());
            for (Consumer<? super GameInstance> autoDestroyCallback : autoDestroyCallbacks) {
                autoDestroyCallback.accept(instance);
            }
        }
    }
}
