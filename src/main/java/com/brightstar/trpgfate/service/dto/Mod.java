package com.brightstar.trpgfate.service.dto;

import com.alibaba.fastjson.JSONObject;

import java.util.UUID;

public final class Mod {
    private UUID uuid;
    private UUID originModUuid;
    private int ownUserId;
    private String title;
    private String description;
    private JSONObject document;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getOriginModUuid() {
        return originModUuid;
    }

    public void setOriginModUuid(UUID originModUuid) {
        this.originModUuid = originModUuid;
    }

    public int getOwnUserId() {
        return ownUserId;
    }

    public void setOwnUserId(int ownUserId) {
        this.ownUserId = ownUserId;
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

    public JSONObject getDocument() {
        return document;
    }

    public void setDocument(JSONObject document) {
        this.document = document;
    }
}
