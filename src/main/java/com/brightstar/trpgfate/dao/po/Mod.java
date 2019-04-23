package com.brightstar.trpgfate.dao.po;

public final class Mod {
    private byte[] guid;
    private int userId;
    private byte[] originModGuid;

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

    public byte[] getOriginModGuid() {
        return originModGuid;
    }

    public void setOriginModGuid(byte[] originModGuid) {
        this.originModGuid = originModGuid;
    }
}
