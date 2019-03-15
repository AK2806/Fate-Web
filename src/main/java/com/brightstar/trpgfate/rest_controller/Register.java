package com.brightstar.trpgfate.rest_controller;

import com.brightstar.trpgfate.rest_controller.vo.RegisterPostReq;
import com.brightstar.trpgfate.service.EmailVerifyService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.EmailExistsException;
import com.brightstar.trpgfate.service.exception.EmailVerifyExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/register")
public final class Register {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailVerifyService emailVerifyService;

    @PostMapping()
    public ResponseEntity register(@RequestBody RegisterPostReq req) {
        try {
            String emailToken = req.getEmailVerifyToken();
            String emailCode = req.getEmailVerifyCode();
            if (!emailVerifyService.verify(emailToken, emailCode)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Incorrect verification code.");
            }
            emailVerifyService.expireEmail(emailToken);
        } catch (EmailVerifyExpiredException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email verification is expired.", e);
        }
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(req.getPasswd());
        try {
            userService.register(user, UserService.ROLE_USER);
            return ResponseEntity.ok(null);
        } catch (EmailExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
}
