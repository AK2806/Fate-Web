package com.brightstar.trpgfate.service.dto;

import java.util.Calendar;
import java.util.UUID;

public interface Game {
    int STATUS_CREATED = 0;
    int STATUS_PLAYABLE = 1;
    int STATUS_GAME_OVER = 2;
    
    UUID getUuid();
    int getBelongUserId();
    UUID getModUuid();
    Calendar getCreateTime();
    int getStatus();
    void setStatus(int status);
    String getTitle();
    void setTitle(String title);
    default boolean isFastGame() {
        return getModUuid() == null;
    }
}
