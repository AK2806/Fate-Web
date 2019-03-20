package com.brightstar.trpgfate.rest_controller.vo.request;

import com.brightstar.trpgfate.rest_controller.helper.RequestCaptchaBase;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class PasswordReseterPostReq extends RequestCaptchaBase {
    @Email
    private String email;

    public PasswordReseterPostReq() {
    }

    public PasswordReseterPostReq(@NotNull String captchaToken, @NotNull String captchaText, @Email String email) {
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
