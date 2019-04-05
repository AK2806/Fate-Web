package com.brightstar.trpgfate.controller.restful.userdata.vo;

import java.util.List;

public final class CharacterGetListByUserResp {
    public final static class CharacterListItem {
        private String uuid;
        private String name;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private List<CharacterListItem> characters;

    public List<CharacterListItem> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharacterListItem> characters) {
        this.characters = characters;
    }
}
