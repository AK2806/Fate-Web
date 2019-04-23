package com.brightstar.trpgfate.dao.po;

import java.sql.Timestamp;

public final class GamingRecord {
    private byte[] gameGuid;
    private byte[] instanceGuid;
    private Timestamp beginTime;
    private Timestamp endTime;

    public GamingRecord() {
    }

    public byte[] getGameGuid() {
        return gameGuid;
    }

    public void setGameGuid(byte[] gameGuid) {
        this.gameGuid = gameGuid;
    }

    public byte[] getInstanceGuid() {
        return instanceGuid;
    }

    public void setInstanceGuid(byte[] instanceGuid) {
        this.instanceGuid = instanceGuid;
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
