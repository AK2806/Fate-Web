package com.brightstar.trpgfate.controller.restful.persona.vo;

import com.brightstar.trpgfate.controller.restful.nested_vo.character.CharacterData;
import com.brightstar.trpgfate.controller.restful.nested_vo.character.PortraitData;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public final class CharacterPostReq {
    @Valid @NotNull
    private PortraitData portrait;
    @Valid @NotNull
    private CharacterData data;

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
