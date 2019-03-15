package com.brightstar.trpgfate.rest_controller.vo;

public class EmailVerifierGetReq {
    private String emailToken;
    private String verifyCode;

    public EmailVerifierGetReq() {
    }

    public EmailVerifierGetReq(String emailToken, String verifyCode) {
        this.emailToken = emailToken;
        this.verifyCode = verifyCode;
    }

    public String getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
