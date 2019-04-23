package com.brightstar.trpgfate.application.game_instance;

import com.brightstar.trpgfate.config.custom_property.GameConfig;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

@Component
public class GameInstanceManager {
    private static final class GameInstanceImpl implements GameInstance {
        private UUID gameUuid;
        private Process process;
        private InetAddress socketAddress;
        private int socketPort;
        private Calendar createTime;

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

        public void setCreateTime(Calendar createTime) {
            this.createTime = createTime;
        }

        @Override
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
    }

    private ConcurrentMap<UUID, GameInstance> gameInstances = new ConcurrentHashMap<>();
    private boolean[] portUsage;
    private GameConfig gameConfig;

    @Autowired
    public GameInstanceManager(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        this.portUsage = new boolean[gameConfig.getMaxPort() - gameConfig.getMinPort() + 1];
    }

    public GameInstance createInstance(UUID uuid) throws IOException {
        GameInstanceImpl gameInstance = new GameInstanceImpl();
        gameInstance.setGameUuid(uuid);
        int usablePort = -1;
        for (int i = 0; i < portUsage.length; ++i) {
            if (!portUsage[i]) {
                usablePort = gameConfig.getMinPort() + i;
                portUsage[i] = true;
                break;
            }
        }
        if (usablePort < 0
                || usablePort < gameConfig.getMinPort() || usablePort > gameConfig.getMaxPort()) {
            throw new BindException("There is no more available port for creating instance");
        }
        gameInstance.setSocketPort(usablePort);
        InetAddress ipAddr = InetAddress.getByName(gameConfig.getIpAddress());
        gameInstance.setSocketAddress(ipAddr);
        String saveDir = FilenameUtils.concat(gameConfig.getSaveLocation(), uuid.toString());
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
        ProcessBuilder processBuilder = new ProcessBuilder(gameConfig.getServerProgram(),
                "-p", String.valueOf(usablePort),
                "-d", saveFilename);
        Process gameProcess = processBuilder.start();
        gameInstance.setProcess(gameProcess);
        gameInstance.setCreateTime(Calendar.getInstance());
        gameInstances.put(uuid, gameInstance);
        return gameInstance;
    }

    public GameInstance getRunningInstance(UUID uuid) {
        return gameInstances.get(uuid);
    }

    public void forEachInstance(Consumer<? super GameInstance> action) {
        gameInstances.forEach((uuid, gameInstance) -> action.accept(gameInstance));
    }

    public void destroyInstance(GameInstance instance) {
        Process process = instance.getProcess();
        if (process.isAlive()) process.destroy();
        int port = instance.getSocketPort();
        portUsage[port] = false;
        gameInstances.remove(instance.getGameUuid(), instance);
    }
}
