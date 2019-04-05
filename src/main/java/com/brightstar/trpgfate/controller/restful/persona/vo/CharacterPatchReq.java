package com.brightstar.trpgfate.controller.restful.persona.vo;

import com.brightstar.trpgfate.controller.restful.nested_vo.character.CharacterData;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public final class CharacterPatchReq {
    @Pattern(regexp = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}")
    private String uuid;
    @Valid @NotNull
    private CharacterData data;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public CharacterData getData() {
        return data;
    }

    public void setData(CharacterData data) {
        this.data = data;
    }
}
