package com.brightstar.trpgfate.rest_controller.vo.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public final class RegisterPostReq {
    @Email
    private String email;
    @Size(min = 6, max = 24)
    private String passwd;
    @NotNull
    private String verifyCode;

    public RegisterPostReq() {
    }

    public RegisterPostReq(@Email String email, @Size(min = 6, max = 24) String passwd, @NotNull String verifyCode) {
        this.email = email;
        this.passwd = passwd;
        this.verifyCode = verifyCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
