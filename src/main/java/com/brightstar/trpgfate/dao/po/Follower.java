package com.brightstar.trpgfate.dao.po;

import java.sql.Timestamp;

public final class Follower {
    private int userId;
    private int followerId;
    private Timestamp time;

    public Follower() {
    }

    public Follower(int userId, int followerId, Timestamp time) {
        this.userId = userId;
        this.followerId = followerId;
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
