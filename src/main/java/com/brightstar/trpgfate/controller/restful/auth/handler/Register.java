package com.brightstar.trpgfate.controller.restful.auth.handler;

import com.brightstar.trpgfate.controller.restful.auth.vo.RegisterPostEmailReq;
import com.brightstar.trpgfate.service.AuthenticationService;
import com.brightstar.trpgfate.service.EmailVerifyService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserConflictException;
import com.brightstar.trpgfate.service.exception.EmailVerifyExpiredException;
import com.brightstar.trpgfate.service.exception.UserDisabledException;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth/register")
public final class Register {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authService;
    @Autowired
    private EmailVerifyService emailVerifyService;

    @PostMapping
    @RequestMapping("/email")
    public void registerWithEmail(@RequestBody @Valid RegisterPostEmailReq req, HttpServletRequest httpRequest) {
        try {
            String email = req.getEmail();
            if (!emailVerifyService.verify(email, req.getVerifyCode())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "验证码错误");
            }
            emailVerifyService.expireEmail(email);
        } catch (EmailVerifyExpiredException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "验证码已过期", e);
        }
        String email = req.getEmail();
        String password = req.getPasswd();
        try {
            userService.registerWithEmail(email, password, User.ROLE_USER);
        } catch (UserConflictException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "这个邮箱已被注册", e);
        }
        User user;
        try {
            user = userService.getUser(email);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "UserDoesntExistException", e);
        }
        HttpSession session = httpRequest.getSession();
        try {
            authService.authenticate(user, password, session, 86400);
        } catch (UserDisabledException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "UserDisabledException", e);
        }
    }
}
