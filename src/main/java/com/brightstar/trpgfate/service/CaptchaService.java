package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.exception.CaptchaExpiredException;

public interface CaptchaService {
    Captcha generateCaptcha();
    Captcha getCaptcha(String id);
    boolean validate(String id, String text) throws CaptchaExpiredException;
}
