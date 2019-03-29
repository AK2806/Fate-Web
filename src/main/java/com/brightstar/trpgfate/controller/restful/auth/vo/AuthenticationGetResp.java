package com.brightstar.trpgfate.controller.restful.auth.vo;

public final class AuthenticationGetResp {
    private int userId;

    public AuthenticationGetResp() {
    }

    public AuthenticationGetResp(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
