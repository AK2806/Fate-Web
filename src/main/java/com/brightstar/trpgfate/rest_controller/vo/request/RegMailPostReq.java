package com.brightstar.trpgfate.rest_controller.vo.request;

import com.brightstar.trpgfate.rest_controller.helper.RequestCaptchaBase;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public final class RegMailPostReq extends RequestCaptchaBase {
    @Email
    private String emailAddr;

    public RegMailPostReq() {
    }

    public RegMailPostReq(@NotNull String captchaToken, @NotNull String captchaText, @Email String emailAddr) {
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
