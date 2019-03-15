package com.brightstar.trpgfate.rest_controller;

import com.brightstar.trpgfate.rest_controller.vo.AuthorizationPostReq;
import com.brightstar.trpgfate.service.CaptchaService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.CaptchaExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/authorization")
public class Authorization {
    @Autowired
    private UserService userService;
    @Autowired
    private CaptchaService captchaService;

    @PostMapping()
    public ResponseEntity Login(@RequestBody AuthorizationPostReq req, HttpServletRequest httpRequest) {
        try {
            if (!captchaService.validate(req.getCaptchaToken(), req.getCaptchaText())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Incorrect captcha text");
            }
        } catch (CaptchaExpiredException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Captcha is expired", e);
        }
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(req.getPasswd());
        HttpSession session = httpRequest.getSession();
        boolean success = userService.authenticate(user, session, 86400);
        if (success) return ResponseEntity.ok(null);
        else return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping()
    public ResponseEntity Logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        session.invalidate();
        return ResponseEntity.ok(null);
    }
}
