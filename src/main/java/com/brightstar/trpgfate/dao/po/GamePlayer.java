package com.brightstar.trpgfate.dao.po;

public final class GamePlayer {
    private int userId;
    private byte[] gameGuid;
    private int role;
    private byte[] characterGuid;

    public GamePlayer() {
    }

    public GamePlayer(int userId, byte[] gameGuid, int role, byte[] characterGuid) {
        this.userId = userId;
        this.gameGuid = gameGuid;
        this.role = role;
        this.characterGuid = characterGuid;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte[] getGameGuid() {
        return gameGuid;
    }

    public void setGameGuid(byte[] gameGuid) {
        this.gameGuid = gameGuid;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public byte[] getCharacterGuid() {
        return characterGuid;
    }

    public void setCharacterGuid(byte[] characterGuid) {
        this.characterGuid = characterGuid;
    }
}
