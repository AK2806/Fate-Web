package com.brightstar.trpgfate.controller.restful.persona.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public final class GamePostReq {
    @Pattern(regexp = "([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})?")
    private String modGuid;
    @NotBlank
    private String title;

    public String getModGuid() {
        return modGuid;
    }

    public void setModGuid(String modGuid) {
        this.modGuid = modGuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
