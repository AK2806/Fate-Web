package com.brightstar.trpgfate.controller.restful.auth.handler;

import com.brightstar.trpgfate.controller.helper.CaptchaChecker;
import com.brightstar.trpgfate.controller.restful.auth.vo.AuthenticationGetResp;
import com.brightstar.trpgfate.controller.restful.auth.vo.AuthenticationPostEmailReq;
import com.brightstar.trpgfate.service.AuthenticationService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.CaptchaExpiredException;
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
@RequestMapping("/auth/authentication")
public final class Authentication {
    @Autowired
    private CaptchaChecker captchaChecker;
    @Autowired
    private AuthenticationService authService;
    @Autowired
    private UserService userService;

    @GetMapping
    public AuthenticationGetResp loggedIn(HttpServletRequest request) {
        String id = authService.getAuthPrincipalName(request.getSession());
        if (id == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        AuthenticationGetResp ret = new AuthenticationGetResp();
        ret.setUserId(Integer.parseInt(id));
        return ret;
    }

    @PostMapping
    @RequestMapping("/email")
    public void loginWithEmail(@RequestBody @Valid AuthenticationPostEmailReq req, HttpServletRequest httpRequest) {
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "账号或密码错误");
        }
        HttpSession session = httpRequest.getSession();
        try {
            if (!authService.authenticate(user, req.getPasswd(), session, 86400)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "账号或密码错误");
            }
        } catch (UserDisabledException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "账号已被冻结");
        }
    }

    @DeleteMapping
    public void logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        session.invalidate();
    }
}
