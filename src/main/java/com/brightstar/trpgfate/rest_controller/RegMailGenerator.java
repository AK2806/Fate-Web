package com.brightstar.trpgfate.rest_controller;

import com.brightstar.trpgfate.rest_controller.helper.CaptchaChecker;
import com.brightstar.trpgfate.rest_controller.vo.request.RegMailPostReq;
import com.brightstar.trpgfate.service.EmailVerifyService;
import com.brightstar.trpgfate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth/register/verify-mail")
public final class RegMailGenerator {
    @Autowired
    private CaptchaChecker captchaChecker;
    @Autowired
    private EmailVerifyService emailVerifyService;
    @Autowired
    private UserService userService;

    @PostMapping
    public void createVerificationMail(
            @RequestBody @Valid RegMailPostReq req,
            BindingResult br) {
        if (br.hasErrors()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "参数格式错误");
        captchaChecker.validate(req);
        if (userService.userExists(req.getEmailAddr())) throw new ResponseStatusException(HttpStatus.CONFLICT, "账户已存在");
        try {
            emailVerifyService.generateEmail(req.getEmailAddr());
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "发送邮件时发生错误，请检查邮箱地址是否正确", e);
        }
    }
}
