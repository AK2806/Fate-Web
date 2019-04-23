package com.brightstar.trpgfate.controller.restful.userdata.vo;

import java.util.Date;
import java.util.List;

public final class GameGetResp {
    public static final class Player {
        private int userId;
        private String characterUuid;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getCharacterUuid() {
            return characterUuid;
        }

        public void setCharacterUuid(String characterUuid) {
            this.characterUuid = characterUuid;
        }
    }
    private String uuid;
    private int belongUserId;
    private String modUuid;
    private Date createTime;
    private int status;
    private String title;
    private List<Player> players;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getBelongUserId() {
        return belongUserId;
    }

    public void setBelongUserId(int belongUserId) {
        this.belongUserId = belongUserId;
    }

    public String getModUuid() {
        return modUuid;
    }

    public void setModUuid(String modUuid) {
        this.modUuid = modUuid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
