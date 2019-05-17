package com.brightstar.trpgfate.service.dto;

import java.util.Calendar;
import java.util.UUID;

public final class GamingRecord {
    private UUID gameUuid;
    private UUID gameTableUuid;
    private Calendar beginTime;
    private Calendar endTime;

    public UUID getGameUuid() {
        return gameUuid;
    }

    public void setGameUuid(UUID gameUuid) {
        this.gameUuid = gameUuid;
    }

    public UUID getGameTableUuid() {
        return gameTableUuid;
    }

    public void setGameTableUuid(UUID gameTableUuid) {
        this.gameTableUuid = gameTableUuid;
    }

    public Calendar getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Calendar beginTime) {
        this.beginTime = beginTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }
}
