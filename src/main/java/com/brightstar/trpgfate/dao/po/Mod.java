package com.brightstar.trpgfate.dao.po;

import java.sql.Timestamp;

public final class Mod {
    private byte[] guid;
    private int userId;
    private Integer authorId;
    private Timestamp createTime;
    private Timestamp lastPublishTime;
    private String title;
    private String description;

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

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastPublishTime() {
        return lastPublishTime;
    }

    public void setLastPublishTime(Timestamp lastPublishTime) {
        this.lastPublishTime = lastPublishTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
