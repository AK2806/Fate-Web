package com.brightstar.trpgfate.controller.restful.persona.vo;

import com.brightstar.trpgfate.controller.restful.nested_vo.character.PortraitData;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public final class CharacterPortraitPatchReq {
    @Pattern(regexp = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}")
    private String uuid;
    @Valid @NotNull
    private PortraitData portrait;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public PortraitData getPortrait() {
        return portrait;
    }

    public void setPortrait(PortraitData portrait) {
        this.portrait = portrait;
    }
}
