package com.brightstar.trpgfate.controller.restful.general.handler;

import com.brightstar.trpgfate.controller.restful.general.vo.CaptchaPostResp;
import com.brightstar.trpgfate.service.Captcha;
import com.brightstar.trpgfate.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/captcha")
public final class CaptchaGenerator {
    @Autowired
    private CaptchaService captchaService;

    @PostMapping
    public CaptchaPostResp createCaptcha() {
        Captcha captcha = captchaService.generateCaptcha();
        CaptchaPostResp captchaResp = new CaptchaPostResp();
        captchaResp.setToken(captcha.getId());
        captchaResp.setImgBs64(captcha.getImageBase64());
        return captchaResp;
    }
}
