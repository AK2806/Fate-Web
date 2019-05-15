package com.brightstar.trpgfate.application.game_instance;

public final class LaunchConfig {
    private int dmUserId;
    private int[] playersUserId;

    public int getDmUserId() {
        return dmUserId;
    }

    public void setDmUserId(int dmUserId) {
        this.dmUserId = dmUserId;
    }

    public int[] getPlayersUserId() {
        return playersUserId;
    }

    public void setPlayersUserId(int[] playersUserId) {
        this.playersUserId = playersUserId;
    }
}
