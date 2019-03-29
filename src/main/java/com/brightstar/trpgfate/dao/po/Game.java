package com.brightstar.trpgfate.dao.po;

import java.sql.Timestamp;

public final class Game {
    private byte[] guid;
    private Timestamp createTime;
    private int status;
    private byte[] modGuid;

    public Game() {
    }

    public Game(byte[] guid, Timestamp createTime, int status, byte[] modGuid) {
        this.guid = guid;
        this.createTime = createTime;
        this.status = status;
        this.modGuid = modGuid;
    }

    public byte[] getGuid() {
        return guid;
    }

    public void setGuid(byte[] guid) {
        this.guid = guid;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public byte[] getModGuid() {
        return modGuid;
    }

    public void setModGuid(byte[] modGuid) {
        this.modGuid = modGuid;
    }
}
