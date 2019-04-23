package com.brightstar.trpgfate.dao.po;

import java.sql.Timestamp;

public final class Game {
    private byte[] guid;
    private int userId;
    private byte[] modGuid;
    private Timestamp createTime;
    private int status;
    private String title;

    public byte[] getGuid() {
        return guid;
    }

    public void setGuid(byte[] guid) {
        this.guid = guid;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte[] getModGuid() {
        return modGuid;
    }

    public void setModGuid(byte[] modGuid) {
        this.modGuid = modGuid;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
