package com.brightstar.trpgfate.rest_controller;

import com.brightstar.trpgfate.rest_controller.helper.CaptchaChecker;
import com.brightstar.trpgfate.rest_controller.vo.request.PasswordReseterPostReq;
import com.brightstar.trpgfate.rest_controller.vo.request.PasswordReseterPutReq;
import com.brightstar.trpgfate.service.PasswordResetService;
import com.brightstar.trpgfate.service.exception.EmailDoesntExistException;
import com.brightstar.trpgfate.service.exception.ResetterExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth/password-reset")
public class PasswordResetter {
    @Autowired
    private CaptchaChecker captchaChecker;
    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping
    public void generateResetLink(@RequestBody @Valid PasswordReseterPostReq req, BindingResult br) {
        if (br.hasErrors()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "参数格式错误");
        captchaChecker.validate(req);
        try {
            passwordResetService.generateResetter(req.getEmail());
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "发送邮件时发生未知错误", e);
        } catch (EmailDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "账户不存在", e);
        }
    }

    @PutMapping
    public void resetPassword(@RequestBody @Valid PasswordReseterPutReq req, BindingResult br) {
        if (br.hasErrors()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "参数格式错误");
        try {
            String token = req.getToken();
            passwordResetService.resetPassword(token, req.getPasswd());
            passwordResetService.expireResetter(token);
        } catch (ResetterExpiredException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "链接已过期", e);
        }
    }
}
