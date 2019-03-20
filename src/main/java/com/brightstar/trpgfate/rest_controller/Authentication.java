package com.brightstar.trpgfate.rest_controller;

import com.brightstar.trpgfate.rest_controller.helper.CaptchaChecker;
import com.brightstar.trpgfate.rest_controller.vo.request.AuthenticationPostReq;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth/authentication")
public class Authentication {
    @Autowired
    private CaptchaChecker captchaChecker;
    @Autowired
    private UserService userService;

    @PostMapping
    public void login(@RequestBody @Valid AuthenticationPostReq req,
                      BindingResult br,
                      HttpServletRequest httpRequest) {
        if (br.hasErrors()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "参数格式错误");
        captchaChecker.validate(req);
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(req.getPasswd());
        HttpSession session = httpRequest.getSession();
        if (!userService.authenticate(user, session, 86400)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "账号或密码错误");
        }
    }

    @DeleteMapping
    public void logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        session.invalidate();
    }
}
