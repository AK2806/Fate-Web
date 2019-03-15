package com.brightstar.trpgfate.rest_controller.vo;

public final class EmailVerifierPostReq {
    private String emailAddr;
    private String captchaToken;
    private String captchaText;

    public EmailVerifierPostReq() {
    }

    public EmailVerifierPostReq(String emailAddr, String captchaToken, String captchaText) {
        this.emailAddr = emailAddr;
        this.captchaToken = captchaToken;
        this.captchaText = captchaText;
    }

    public String getCaptchaText() {
        return captchaText;
    }

    public void setCaptchaText(String captchaText) {
        this.captchaText = captchaText;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getCaptchaToken() {
        return captchaToken;
    }

    public void setCaptchaToken(String captchaToken) {
        this.captchaToken = captchaToken;
    }
}
