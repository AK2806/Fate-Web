package com.brightstar.trpgfate.service.dto;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public final class GameTable {
    public static final class RunningGame {
        private String log;
        private InetAddress address;
        private int port;
        private HashMap<Integer, byte[]> accessKeyMap = new HashMap<>();

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }

        public InetAddress getAddress() {
            return address;
        }

        public void setAddress(InetAddress address) {
            this.address = address;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public byte[] getAccessKey(User user) {
            return accessKeyMap.get(user.getId());
        }

        public void setAccessKey(User user, byte[] accessKey) {
            accessKeyMap.put(user.getId(), accessKey);
        }
    }

    private UUID gameUuid;
    private Calendar createTime;
    private User dm;
    private User[] players;
    private RunningGame runningGame;

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

    public User[] getPlayers() {
        return players;
    }

    public void setPlayers(User[] players) {
        this.players = players;
    }

    public RunningGame getRunningGame() {
        return runningGame;
    }

    public void setRunningGame(RunningGame runningGame) {
        this.runningGame = runningGame;
    }
}
