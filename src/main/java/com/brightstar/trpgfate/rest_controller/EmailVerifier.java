package com.brightstar.trpgfate.rest_controller;

import com.brightstar.trpgfate.rest_controller.vo.EmailVerifierGetReq;
import com.brightstar.trpgfate.rest_controller.vo.EmailVerifierPostReq;
import com.brightstar.trpgfate.rest_controller.vo.EmailVerifierPostResp;
import com.brightstar.trpgfate.service.CaptchaService;
import com.brightstar.trpgfate.service.EmailVerifyService;
import com.brightstar.trpgfate.service.exception.CaptchaExpiredException;
import com.brightstar.trpgfate.service.exception.EmailVerifyExpiredException;
import com.brightstar.trpgfate.service.exception.EmailVerifySendingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/verifyMail")
public final class EmailVerifier {
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private EmailVerifyService emailVerifyService;

    @PostMapping()
    public @ResponseBody
    EmailVerifierPostResp generateVerifyMail(@RequestBody EmailVerifierPostReq req) {
        try {
            if (!captchaService.validate(req.getCaptchaToken(), req.getCaptchaText())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Incorrect captcha text");
            }
        } catch (CaptchaExpiredException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Captcha is expired", e);
        }
        try {
            EmailVerifierPostResp resp = new EmailVerifierPostResp();
            resp.setMailToken(emailVerifyService.generateEmail(req.getEmailAddr()));
            return resp;
        } catch (EmailVerifySendingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while sending email", e);
        }
    }

    @GetMapping()
    public ResponseEntity verifyMailCode(@RequestBody EmailVerifierGetReq req) {
        try {
            if (emailVerifyService.verify(req.getEmailToken(), req.getVerifyCode())) {
                return ResponseEntity.ok(null);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (EmailVerifyExpiredException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email verification is expired", e);
        }
    }
}
