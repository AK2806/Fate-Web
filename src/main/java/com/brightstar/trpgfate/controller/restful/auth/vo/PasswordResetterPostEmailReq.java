package com.brightstar.trpgfate.controller.restful.auth.vo;

import com.brightstar.trpgfate.controller.helper.RequestCaptchaBase;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public final class PasswordResetterPostEmailReq extends RequestCaptchaBase {
    @Email
    private String email;

    public PasswordResetterPostEmailReq() {
    }

    public PasswordResetterPostEmailReq(@NotNull String captchaToken, @NotNull String captchaText, @Email String email) {
        super(captchaToken, captchaText);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
