package com.brightstar.trpgfate.controller.restful.auth.vo;

import com.brightstar.trpgfate.controller.helper.RequestCaptchaBase;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public final class RegmailGeneratorPostReq extends RequestCaptchaBase {
    @Email
    private String emailAddr;

    public RegmailGeneratorPostReq() {
    }

    public RegmailGeneratorPostReq(@NotNull String captchaToken, @NotNull String captchaText, @Email String emailAddr) {
        super(captchaToken, captchaText);
        this.emailAddr = emailAddr;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }
}
