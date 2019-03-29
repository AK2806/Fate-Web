package com.brightstar.trpgfate.dao.po;

import java.sql.Timestamp;

public final class GamingRecord {
    private int userId;
    private byte[] gameGuid;
    private Timestamp beginTime;
    private Timestamp endTime;

    public GamingRecord() {
    }

    public GamingRecord(int userId, byte[] gameGuid, Timestamp beginTime, Timestamp endTime) {
        this.userId = userId;
        this.gameGuid = gameGuid;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte[] getGameGuid() {
        return gameGuid;
    }

    public void setGameGuid(byte[] gameGuid) {
        this.gameGuid = gameGuid;
    }

    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
