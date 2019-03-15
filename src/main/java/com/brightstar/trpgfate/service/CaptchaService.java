package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.exception.CaptchaExpiredException;

public interface CaptchaService {
    Captcha generateCaptcha();
    Captcha getCaptcha(String id);
    boolean validate(String captchaId, String text) throws CaptchaExpiredException;
}
