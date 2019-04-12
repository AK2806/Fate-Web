package com.brightstar.trpgfate.dao.po;

import java.sql.Timestamp;

public final class Notification {
    private int userId;
    private Timestamp lastTimeRead;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getLastTimeRead() {
        return lastTimeRead;
    }

    public void setLastTimeRead(Timestamp lastTimeRead) {
        this.lastTimeRead = lastTimeRead;
    }
}
