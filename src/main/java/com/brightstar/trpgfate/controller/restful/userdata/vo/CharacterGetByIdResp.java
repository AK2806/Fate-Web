package com.brightstar.trpgfate.controller.restful.userdata.vo;

import com.brightstar.trpgfate.controller.restful.nested_vo.character.CharacterData;
import com.brightstar.trpgfate.controller.restful.nested_vo.character.PortraitData;

public final class CharacterGetByIdResp {
    private String uuid;
    private int belongUserId;
    private PortraitData portrait;
    private CharacterData data;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getBelongUserId() {
        return belongUserId;
    }

    public void setBelongUserId(int belongUserId) {
        this.belongUserId = belongUserId;
    }

    public PortraitData getPortrait() {
        return portrait;
    }

    public void setPortrait(PortraitData portrait) {
        this.portrait = portrait;
    }

    public CharacterData getData() {
        return data;
    }

    public void setData(CharacterData data) {
        this.data = data;
    }
}
