package com.brightstar.trpgfate.controller.restful.console.vo;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public final class GameInstanceGetResp {
    public static final class GameInstance {
        private UUID gameUuid;
        private int dmId;
        private int[] playersId;
        private String log;
        private String socketAddress;
        private int socketPort;
        private Date createTime;

        public UUID getGameUuid() {
            return gameUuid;
        }

        public void setGameUuid(UUID gameUuid) {
            this.gameUuid = gameUuid;
        }

        public int getDmId() {
            return dmId;
        }

        public void setDmId(int dmId) {
            this.dmId = dmId;
        }

        public int[] getPlayersId() {
            return playersId;
        }

        public void setPlayersId(int[] playersId) {
            this.playersId = playersId;
        }

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }

        public String getSocketAddress() {
            return socketAddress;
        }

        public void setSocketAddress(String socketAddress) {
            this.socketAddress = socketAddress;
        }

        public int getSocketPort() {
            return socketPort;
        }

        public void setSocketPort(int socketPort) {
            this.socketPort = socketPort;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }
    }

    private List<GameInstance> gameInstances;

    public List<GameInstance> getGameInstances() {
        return gameInstances;
    }

    public void setGameInstances(List<GameInstance> gameInstances) {
        this.gameInstances = gameInstances;
    }
}
