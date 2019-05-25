package com.brightstar.trpgfate.controller.restful.persona.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class ModPostReq {
    @NotBlank
    private String title;
    @NotNull
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
