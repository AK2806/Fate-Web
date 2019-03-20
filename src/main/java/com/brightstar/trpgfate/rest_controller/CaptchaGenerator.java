package com.brightstar.trpgfate.rest_controller;

import com.brightstar.trpgfate.rest_controller.vo.response.CaptchaPostResp;
import com.brightstar.trpgfate.service.Captcha;
import com.brightstar.trpgfate.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/captcha")
public class CaptchaGenerator {
    @Autowired
    private CaptchaService captchaService;

    @PostMapping
    public @ResponseBody
    CaptchaPostResp createCaptcha() {
        Captcha captcha = captchaService.generateCaptcha();
        CaptchaPostResp captchaResp = new CaptchaPostResp();
        captchaResp.setToken(captcha.getId());
        captchaResp.setImgBs64(captcha.getImageBase64());
        return captchaResp;
    }
}
