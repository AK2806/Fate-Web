package com.brightstar.trpgfate.rest_controller.vo;

public final class RegisterPostReq {
    private String email;
    private String passwd;
    private String emailVerifyToken;
    private String emailVerifyCode;

    public RegisterPostReq() {
    }

    public RegisterPostReq(String email, String passwd, String emailVerifyToken, String emailVerifyCode) {
        this.email = email;
        this.passwd = passwd;
        this.emailVerifyToken = emailVerifyToken;
        this.emailVerifyCode = emailVerifyCode;
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

    public String getEmailVerifyToken() {
        return emailVerifyToken;
    }

    public void setEmailVerifyToken(String emailVerifyToken) {
        this.emailVerifyToken = emailVerifyToken;
    }

    public String getEmailVerifyCode() {
        return emailVerifyCode;
    }

    public void setEmailVerifyCode(String emailVerifyCode) {
        this.emailVerifyCode = emailVerifyCode;
    }
}
