package com.brightstar.trpgfate.service.dto;

import java.util.Calendar;
import java.util.UUID;

public final class Mod {
    private UUID uuid;
    private int ownUserId;
    private int authorUserId;
    private Calendar createTime;
    private Calendar lastPublishTime;
    private String title;
    private String description;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getOwnUserId() {
        return ownUserId;
    }

    public void setOwnUserId(int ownUserId) {
        this.ownUserId = ownUserId;
    }

    public int getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(int authorUserId) {
        this.authorUserId = authorUserId;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    public Calendar getLastPublishTime() {
        return lastPublishTime;
    }

    public void setLastPublishTime(Calendar lastPublishTime) {
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

    private boolean isPublished() {
        return lastPublishTime != null;
    }
}
