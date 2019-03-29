package com.brightstar.trpgfate.dao.po;

public final class Mod {
    private byte[] guid;
    private int userId;
    private String title;
    private byte[] thumbnail;
    private byte[] originModGuid;

    public Mod() {
    }

    public Mod(byte[] guid, int userId, String title, byte[] thumbnail, byte[] originModGuid) {
        this.guid = guid;
        this.userId = userId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.originModGuid = originModGuid;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public byte[] getOriginModGuid() {
        return originModGuid;
    }

    public void setOriginModGuid(byte[] originModGuid) {
        this.originModGuid = originModGuid;
    }
}
