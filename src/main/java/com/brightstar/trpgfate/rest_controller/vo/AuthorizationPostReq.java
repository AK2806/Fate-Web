package com.brightstar.trpgfate.rest_controller.vo;

public final class AuthorizationPostReq {
    private String email;
    private String passwd;
    private String captchaToken;
    private String captchaText;

    public AuthorizationPostReq() {
    }

    public AuthorizationPostReq(String email, String passwd, String captchaToken, String captchaText) {
        this.email = email;
        this.passwd = passwd;
        this.captchaToken = captchaToken;
        this.captchaText = captchaText;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getCaptchaToken() {
        return captchaToken;
    }

    public void setCaptchaToken(String captchaToken) {
        this.captchaToken = captchaToken;
    }

    public String getCaptchaText() {
        return captchaText;
    }

    public void setCaptchaText(String captchaText) {
        this.captchaText = captchaText;
    }
}
