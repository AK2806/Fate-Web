package com.brightstar.trpgfate.dao.po;

import java.sql.Timestamp;

public final class Notification {
    private int userId;
    private Timestamp lastViewFollowerTime;
    private int lastReadAnnouncementId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getLastViewFollowerTime() {
        return lastViewFollowerTime;
    }

    public void setLastViewFollowerTime(Timestamp lastViewFollowerTime) {
        this.lastViewFollowerTime = lastViewFollowerTime;
    }

    public int getLastReadAnnouncementId() {
        return lastReadAnnouncementId;
    }

    public void setLastReadAnnouncementId(int lastReadAnnouncementId) {
        this.lastReadAnnouncementId = lastReadAnnouncementId;
    }
}
