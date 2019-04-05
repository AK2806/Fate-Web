package com.brightstar.trpgfate.dao.po;

public final class Character {
    private byte[] guid;
    private int userId;
    public Character() {
    }

    public Character(byte[] guid, int userId) {
        this.guid = guid;
        this.userId = userId;
    }

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
}
