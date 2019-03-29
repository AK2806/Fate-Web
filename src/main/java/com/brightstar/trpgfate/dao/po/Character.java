package com.brightstar.trpgfate.dao.po;

public final class Character {
    private byte[] guid;
    private int userId;
    private String name;
    private byte[] portraitId;

    public Character() {
    }

    public Character(byte[] guid, int userId, String name, byte[] portraitId) {
        this.guid = guid;
        this.userId = userId;
        this.name = name;
        this.portraitId = portraitId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPortraitId() {
        return portraitId;
    }

    public void setPortraitId(byte[] portraitId) {
        this.portraitId = portraitId;
    }
}
