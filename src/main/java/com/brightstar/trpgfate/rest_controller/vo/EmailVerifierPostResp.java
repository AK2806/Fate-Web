package com.brightstar.trpgfate.rest_controller.vo;

public final class EmailVerifierPostResp {
    private String mailToken;

    public EmailVerifierPostResp() {
    }

    public EmailVerifierPostResp(String mailToken) {
        this.mailToken = mailToken;
    }

    public String getMailToken() {
        return mailToken;
    }

    public void setMailToken(String mailToken) {
        this.mailToken = mailToken;
    }
}
