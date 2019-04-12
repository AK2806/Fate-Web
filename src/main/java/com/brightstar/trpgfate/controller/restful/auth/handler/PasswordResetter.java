package com.brightstar.trpgfate.controller.restful.auth.handler;

import com.brightstar.trpgfate.controller.helper.CaptchaChecker;
import com.brightstar.trpgfate.controller.restful.auth.vo.PasswordResetterPostEmailReq;
import com.brightstar.trpgfate.controller.restful.auth.vo.PasswordResetterPutReq;
import com.brightstar.trpgfate.service.PasswordResetService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.CaptchaExpiredException;
import com.brightstar.trpgfate.service.exception.MessageFailedException;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import com.brightstar.trpgfate.service.exception.ResetterExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth/password-reset")
public final class PasswordResetter {
    @Autowired
    private CaptchaChecker captchaChecker;
    @Autowired
    private PasswordResetService passwordResetService;
    @Autowired
    private UserService userService;

    @PostMapping
    @RequestMapping("/email")
    public void generateResetLink(@RequestBody @Valid PasswordResetterPostEmailReq req) {
        try {
            if (!captchaChecker.validate(req))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "验证码错误");
        } catch (CaptchaExpiredException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "验证码已过期", e);
        }

        User user;
        try {
            user = userService.getUser(req.getEmail());
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "这个邮箱尚未注册", e);
        }
        try {
            passwordResetService.generateResetter(user, PasswordResetService.METHOD_E_MAIL);
        } catch (MessageFailedException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "发送邮件时发生未知错误", e);
        }
    }

    @PutMapping
    public void resetPassword(@RequestBody @Valid PasswordResetterPutReq req) {
        try {
            User user = userService.getUser(req.getPid());
            String token = req.getToken();
            passwordResetService.resetPassword(token, user, req.getPasswd());
            passwordResetService.expireResetter(token);
        } catch (UserDoesntExistException | ResetterExpiredException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "链接已过期", e);
        }
    }
}
