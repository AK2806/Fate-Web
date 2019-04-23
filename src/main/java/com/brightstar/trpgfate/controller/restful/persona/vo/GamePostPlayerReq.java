package com.brightstar.trpgfate.controller.restful.persona.vo;

import javax.validation.constraints.Min;

public final class GamePostPlayerReq {
    @Min(0)
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
