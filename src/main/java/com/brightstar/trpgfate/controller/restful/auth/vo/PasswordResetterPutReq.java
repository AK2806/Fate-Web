package com.brightstar.trpgfate.controller.restful.auth.vo;

import javax.validation.constraints.*;

public final class PasswordResetterPutReq {
    @Min(0)
    private int pid;
    @NotNull
    private String token;
    @Size(min = 6, max = 24)
    private String passwd;

    public PasswordResetterPutReq() {
    }

    public PasswordResetterPutReq(@Min(0) int pid, @NotNull String token, @Size(min = 6, max = 24) String passwd) {
        this.pid = pid;
        this.token = token;
        this.passwd = passwd;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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
