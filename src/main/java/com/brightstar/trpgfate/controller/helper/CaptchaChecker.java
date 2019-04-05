package com.brightstar.trpgfate.controller.helper;

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

    public boolean validate(RequestCaptchaBase req) throws CaptchaExpiredException {
        return validator.validate(req).isEmpty()
                && captchaService.validate(req.getCaptchaToken(), req.getCaptchaText());
    }
}
