package com.brightstar.trpgfate.controller.helper;

import javax.validation.constraints.NotNull;

public abstract class RequestCaptchaBase {
    @NotNull
    private String captchaToken;
    @NotNull
    private String captchaText;

    protected RequestCaptchaBase() {
    }

    protected RequestCaptchaBase(@NotNull String captchaToken, @NotNull String captchaText) {
        this.captchaToken = captchaToken;
        this.captchaText = captchaText;
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
