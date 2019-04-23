package com.brightstar.trpgfate.controller.helper;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public abstract class RequestClientAuthBase {
    @Min(0)
    private int userId;
    @Size(min = 6, max = 24)
    private String passwd;
    @Min(0)
    private int nounce;
    @NotBlank
    private String signature;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
