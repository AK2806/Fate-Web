package com.brightstar.trpgfate.controller.restful.persona.vo;

import javax.validation.constraints.Size;

public final class PasswordSetterPutReq {
    @Size(min = 6, max = 24)
    private String oldPasswd;
    @Size(min = 6, max = 24)
    private String newPasswd;

    public PasswordSetterPutReq() {
    }

    public PasswordSetterPutReq(@Size(min = 6, max = 24) String oldPasswd, @Size(min = 6, max = 24) String newPasswd) {
        this.oldPasswd = oldPasswd;
        this.newPasswd = newPasswd;
    }

    public String getOldPasswd() {
        return oldPasswd;
    }

    public void setOldPasswd(String oldPasswd) {
        this.oldPasswd = oldPasswd;
    }

    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }
}
