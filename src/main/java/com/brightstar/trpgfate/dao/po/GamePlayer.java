package com.brightstar.trpgfate.dao.po;

public final class GamePlayer {
    private int userId;
    private byte[] gameGuid;
    private byte[] characterGuid;

    public GamePlayer() {
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

    public byte[] getCharacterGuid() {
        return characterGuid;
    }

    public void setCharacterGuid(byte[] characterGuid) {
        this.characterGuid = characterGuid;
    }
}
