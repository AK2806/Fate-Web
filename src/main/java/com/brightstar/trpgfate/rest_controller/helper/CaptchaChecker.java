package com.brightstar.trpgfate.rest_controller.helper;

import com.brightstar.trpgfate.service.CaptchaService;
import com.brightstar.trpgfate.service.exception.CaptchaExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Validator;

@Component
public class CaptchaChecker {
    @Autowired
    private Validator validator;
    @Autowired
    private CaptchaService captchaService;

    public void validate(RequestCaptchaBase req) {
        try {
            if (!validator.validate(req).isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "参数格式错误");
            }
            if (!captchaService.validate(req.getCaptchaToken(), req.getCaptchaText())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "验证码错误");
            }
        } catch (CaptchaExpiredException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "验证码已过期", e);
        }
    }
}
