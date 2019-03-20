package com.brightstar.trpgfate.rest_controller.vo.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordReseterPutReq {
    @NotNull
    private String token;
    @Size(min = 6, max = 24)
    private String passwd;

    public PasswordReseterPutReq() {
    }

    public PasswordReseterPutReq(@NotNull String token, @Size(min = 6, max = 24) String passwd) {
        this.token = token;
        this.passwd = passwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
