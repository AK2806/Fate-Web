package com.brightstar.trpgfate.rest_controller.vo.request;

import com.brightstar.trpgfate.rest_controller.helper.RequestCaptchaBase;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public final class AuthenticationPostReq extends RequestCaptchaBase {
    @Email
    private String email;
    @Size(min = 6, max = 24)
    private String passwd;

    public AuthenticationPostReq() {
    }

    public AuthenticationPostReq(@NotNull String captchaToken, @NotNull String captchaText, @Email String email, @Size(min = 6, max = 24) String passwd) {
        super(captchaToken, captchaText);
        this.email = email;
        this.passwd = passwd;
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
}
