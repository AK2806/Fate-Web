package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.application.game_instance.GameInstance;
import com.brightstar.trpgfate.application.game_instance.GameInstanceManager;
import com.brightstar.trpgfate.application.game_instance.LaunchConfig;
import com.brightstar.trpgfate.component.staticly.datetime.DatetimeConverter;
import com.brightstar.trpgfate.component.staticly.uuid.UUIDHelper;
import com.brightstar.trpgfate.dao.GamingRecordDAO;
import com.brightstar.trpgfate.dao.po.GamingRecord;
import com.brightstar.trpgfate.service.GameService;
import com.brightstar.trpgfate.service.GameTableService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.Game;
import com.brightstar.trpgfate.service.dto.GameTable;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.InvalidGameStateException;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameTableServiceImpl implements GameTableService {
    private static class GameTableData {
        private UUID gameUuid;
        private Calendar createTime;
        private User dm;
        private final ArrayList<User> players = new ArrayList<>();
        private Calendar dmReactiveTime = Calendar.getInstance();
        private final ArrayList<Calendar> playersReactiveTime = new ArrayList<>();

        public UUID getGameUuid() {
            return gameUuid;
        }

        public void setGameUuid(UUID gameUuid) {
            this.gameUuid = gameUuid;
        }

        public Calendar getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Calendar createTime) {
            this.createTime = createTime;
        }

        public User getDm() {
            return dm;
        }

        public void setDm(User dm) {
            this.dm = dm;
        }

        public ArrayList<User> getPlayers() {
            return players;
        }

        public Calendar getDmReactiveTime() {
            return dmReactiveTime;
        }

        public void setDmReactiveTime(Calendar dmReactiveTime) {
            this.dmReactiveTime = dmReactiveTime;
        }

        public ArrayList<Calendar> getPlayersReactiveTime() {
            return playersReactiveTime;
        }
    }

    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;
    @Autowired
    private GamingRecordDAO gamingRecordDAO;

    private GameInstanceManager gameInstanceManager;

    private ConcurrentHashMap<UUID, GameTableData> gameTables = new ConcurrentHashMap<>();

    @Autowired
    public GameTableServiceImpl(GameInstanceManager gameInstanceManager) {
        this.gameInstanceManager = gameInstanceManager;
        gameInstanceManager.addInstanceAutoDestroyListener(this::runningGameStopped);
    }

    @Override
    public void createGameTable(User dm, Game game) throws InvalidGameStateException {
        UUID uuid = game.getUuid();
        if (game.getStatus() != Game.STATUS_PLAYABLE) throw new InvalidGameStateException("This game is not in playable status.");
        if (gameTables.get(uuid) != null) throw new InvalidGameStateException("This game has already opened a table.");
        GameTableData data = new GameTableData();
        data.setGameUuid(uuid);
        data.setCreateTime(Calendar.getInstance());
        data.setDm(dm);
        data.setDmReactiveTime(Calendar.getInstance());
        gameTables.put(uuid, data);
    }

    @Override
    public void keepJoinGameTable(User user, Game game) throws InvalidGameStateException, UserDoesntExistException {
        GameTableData data = gameTables.get(game.getUuid());
        if (data == null) throw new InvalidGameStateException("This game doesn't open the table.");
        if (game.getBelongUserId() == user.getId()) {
            if (data.getDm() == null) {
                data.setDm(user);
            }
            data.setDmReactiveTime(Calendar.getInstance());
        } else {
            List<User> players = gameService.getPlayersOfGame(game);
            boolean found = false;
            for (User player : players) {
                if (player.getId() == user.getId()) {
                    ArrayList<User> innerPlayers = data.getPlayers();
                    int innerIdx = -1;
                    for (int i = 0; i < innerPlayers.size(); ++i) {
                        if (innerPlayers.get(i).getId() == user.getId()) {
                            innerIdx = i;
                            break;
                        }
                    }
                    if (innerIdx == -1) {
                        innerPlayers.add(user);
                        data.getPlayersReactiveTime().add(Calendar.getInstance());
                    } else {
                        data.getPlayersReactiveTime().set(innerIdx, Calendar.getInstance());
                    }
                    found = true;
                    break;
                }
            }
            if (!found) throw new UserDoesntExistException();
        }
    }

    @Override
    public void gameTableUserLeaved(Game game, User user) {
        GameTableData data = gameTables.get(game.getUuid());
        if (data == null) return;
        User dm = data.getDm();
        if (dm != null && dm.getId() == user.getId()) {
            data.setDm(null);
        } else {
            ArrayList<User> players = data.getPlayers();
            for (int i = players.size() - 1; i >= 0; --i) {
                User player = players.get(i);
                if (player.getId() == user.getId()) {
                    players.remove(i);
                    data.getPlayersReactiveTime().remove(i);
                    break;
                }
            }
        }
    }

    @Scheduled(fixedDelay = 3000L)
    private void autoReleaseGameTable() {
        gameTables.entrySet().removeIf(entry -> {
            GameTableData data = entry.getValue();
            GameInstance gameInstance = gameInstanceManager.getRunningInstance(entry.getKey());
            if (data.getDm() == null && data.getPlayers().size() <= 0
                && gameInstance == null)
                return true;
            if (gameInstance == null) removeConnBrokenUser(data); // delay for next 3 seconds
            return false;
        });
    }

    private void removeConnBrokenUser(GameTableData data) {
        final int timeout = 5;
        Calendar now = Calendar.getInstance();
        Calendar dmTime = (Calendar)data.getDmReactiveTime().clone();
        dmTime.add(Calendar.SECOND, timeout);
        if (dmTime.before(now)) {
            data.setDm(null);
        }
        ArrayList<Calendar> playersTime = data.getPlayersReactiveTime();
        for (int i = playersTime.size() - 1; i >= 0 ; --i) {
            Calendar playerTime = (Calendar)playersTime.get(i).clone();
            playerTime.add(Calendar.SECOND, timeout);
            if (playerTime.before(now)) {
                playersTime.remove(i);
                data.getPlayers().remove(i);
            }
        }
    }

    @Override
    public GameTable getGameTable(Game game) {
        UUID uuid = game.getUuid();
        GameTableData data = gameTables.get(uuid);
        if (data == null) return null;
        GameInstance gameInstance = gameInstanceManager.getRunningInstance(uuid);
        return generateGameTableData(data, gameInstance);
    }

    @Override
    public List<GameTable> getAllGameTables() {
        ArrayList<GameTable> ret = new ArrayList<>();
        for (Map.Entry<UUID, GameTableData> entry : gameTables.entrySet()) {
            GameInstance gameInstance = gameInstanceManager.getRunningInstance(entry.getKey());
            ret.add(generateGameTableData(entry.getValue(), gameInstance));
        }
        return ret;
    }

    @Override
    public void runGame(Game game) throws InvalidGameStateException {
        UUID uuid = game.getUuid();
        if (gameTables.get(uuid) == null) throw new InvalidGameStateException("This game doesn't open the table.");
        if (gameInstanceManager.getRunningInstance(uuid) != null)
            throw new InvalidGameStateException("This game has already been running.");
        try {
            LaunchConfig gameLaunchConfig = new LaunchConfig();
            User dm;
            try {
                dm = userService.getUser(game.getBelongUserId());
            } catch (UserDoesntExistException e) {
                throw new IllegalArgumentException(e);
            }
            gameLaunchConfig.setDmUserId(dm.getId());
            List<User> players = gameService.getPlayersOfGame(game);
            int[] playersId = new int[players.size()];
            int i = 0;
            for (User player : players) {
                playersId[i++] = player.getId();
            }
            gameLaunchConfig.setPlayersUserId(playersId);
            gameInstanceManager.createInstance(uuid, gameLaunchConfig);
        } catch (IOException e) {
            throw new InvalidGameStateException(e);
        }
    }

    @Override
    public void forceStopRunningGame(Game game) {
        GameInstance gameInstance = gameInstanceManager.getRunningInstance(game.getUuid());
        if (gameInstance == null) return;
        gameInstanceManager.destroyInstance(gameInstance.getGameUuid());
        runningGameStopped(gameInstance);
    }

    private void runningGameStopped(GameInstance gameInstance) {
        GamingRecord po = new GamingRecord();
        po.setGameGuid(UUIDHelper.toBytes(gameInstance.getGameUuid()));
        po.setInstanceGuid(UUIDHelper.toBytes(UUID.randomUUID()));
        po.setBeginTime(DatetimeConverter.calendar2SqlTimestamp(gameInstance.getCreateTime()));
        po.setEndTime(DatetimeConverter.calendar2SqlTimestamp(Calendar.getInstance()));
        gamingRecordDAO.insert(po);
    }

    private GameTable generateGameTableData(GameTableData gameTableData, GameInstance gameInstance) {
        GameTable ret = new GameTable();
        ret.setGameUuid(gameTableData.getGameUuid());
        ret.setCreateTime(gameTableData.getCreateTime());
        ret.setDm(gameTableData.getDm());
        User[] players = new User[gameTableData.getPlayers().size()];
        gameTableData.getPlayers().toArray(players);
        ret.setPlayers(players);
        ret.setRunningGame(null);
        if (gameInstance != null) {
            GameTable.RunningGame runningGame = new GameTable.RunningGame();
            runningGame.setLog(gameInstance.getLog());
            runningGame.setAddress(gameInstance.getSocketAddress());
            runningGame.setPort(gameInstance.getSocketPort());
            gameInstance.getAccessKeys().forEach((k, v) -> {
                try {
                    runningGame.setAccessKey(userService.getUser(k), v);
                } catch (UserDoesntExistException e) {
                    e.printStackTrace();
                }
            });
            ret.setRunningGame(runningGame);
        }
        return ret;
    }
}
