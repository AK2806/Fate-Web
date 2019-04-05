package com.brightstar.trpgfate.service.dto.character;

import java.util.UUID;

public final class Stunt {
    public static final int TYPE_PRESET = 0;
    public static final int TYPE_CUSTOM = 1;

    private int type;

    // PRESET
    private String presetId;
    // CUSTOM
    private UUID uuid;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPresetId() {
        return presetId;
    }

    public void setPresetId(String presetId) {
        this.presetId = presetId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
