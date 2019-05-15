package com.brightstar.trpgfate.controller.restful.persona.vo;

public class GameTableGetResp {
    public static final class GameTableData {
        private String gameUuid;
        private int dmId;
        private int[] playersId;
        private boolean ableToParticipate;
        private boolean playing;

        public String getGameUuid() {
            return gameUuid;
        }

        public void setGameUuid(String gameUuid) {
            this.gameUuid = gameUuid;
        }

        public int getDmId() {
            return dmId;
        }

        public void setDmId(int dmId) {
            this.dmId = dmId;
        }

        public int[] getPlayersId() {
            return playersId;
        }

        public void setPlayersId(int[] playersId) {
            this.playersId = playersId;
        }

        public boolean isAbleToParticipate() {
            return ableToParticipate;
        }

        public void setAbleToParticipate(boolean ableToParticipate) {
            this.ableToParticipate = ableToParticipate;
        }

        public boolean isPlaying() {
            return playing;
        }

        public void setPlaying(boolean playing) {
            this.playing = playing;
        }
    }

    private GameTableData[] gameTableData;

    public GameTableData[] getGameTableData() {
        return gameTableData;
    }

    public void setGameTableData(GameTableData[] gameTableData) {
        this.gameTableData = gameTableData;
    }
}
