package com.brightstar.trpgfate.controller.restful.auth.handler;

import com.brightstar.trpgfate.controller.helper.CaptchaChecker;
import com.brightstar.trpgfate.controller.restful.auth.vo.RegmailGeneratorPostReq;
import com.brightstar.trpgfate.service.EmailVerifyService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.exception.MessageFailedException;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth/register/email/verify")
public final class RegmailGenerator {
    @Autowired
    private CaptchaChecker captchaChecker;
    @Autowired
    private EmailVerifyService emailVerifyService;
    @Autowired
    private UserService userService;

    @PostMapping
    public void createVerificationMail(@RequestBody @Valid RegmailGeneratorPostReq req) {
        captchaChecker.validate(req);
        try {
            userService.getUser(req.getEmailAddr());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "这个邮箱已被注册");
        } catch (UserDoesntExistException ignore) { }
        try {
            emailVerifyService.generateEmail(req.getEmailAddr());
        } catch (MessageFailedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "发送邮件时发生错误，请检查邮箱地址是否正确", e);
        }
    }
}
