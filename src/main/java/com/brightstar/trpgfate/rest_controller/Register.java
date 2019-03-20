package com.brightstar.trpgfate.rest_controller;

import com.brightstar.trpgfate.rest_controller.vo.request.RegisterPostReq;
import com.brightstar.trpgfate.service.EmailVerifyService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.EmailExistsException;
import com.brightstar.trpgfate.service.exception.EmailVerifyExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
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
    private EmailVerifyService emailVerifyService;

    @PostMapping
    public void register(@RequestBody @Valid RegisterPostReq req,
                         BindingResult br,
                         HttpServletRequest httpRequest) {
        if (br.hasErrors()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "参数格式错误");
        try {
            String email = req.getEmail();
            if (!emailVerifyService.verify(email, req.getVerifyCode())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "验证码错误");
            }
            emailVerifyService.expireEmail(email);
        } catch (EmailVerifyExpiredException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "验证码已过期", e);
        }
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(req.getPasswd());
        try {
            userService.register(user, UserService.ROLE_USER);
        } catch (EmailExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "账户已存在", e);
        }
        HttpSession session = httpRequest.getSession();
        userService.authenticate(user, session, 86400);
    }
}
