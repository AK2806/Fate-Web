package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.component.token.Token;
import com.brightstar.trpgfate.component.token.TokenManager;
import com.brightstar.trpgfate.service.EmailSendingService;
import com.brightstar.trpgfate.service.EmailVerifyService;
import com.brightstar.trpgfate.service.exception.EmailVerifyExpiredException;
import com.brightstar.trpgfate.service.exception.EmailVerifySendingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Locale;

@Service
public class EmailVerifyServiceImpl implements EmailVerifyService {
    @Autowired
    private ITemplateEngine templateEngine;
    @Autowired
    private EmailSendingService emailSendingService;
    @Autowired
    private TokenManager tokenManager;

    private SecureRandom secureRandom = new SecureRandom();

    @Override
    public String generateEmail(String address) throws EmailVerifySendingException {
        Token token = tokenManager.generateToken();
        byte[] verifyCode = new byte[3];
        secureRandom.nextBytes(verifyCode);
        token.setContent(verifyCode);
        Context ctx = new Context(Locale.CHINA);
        ctx.setVariable("verifyCode", String.valueOf(Hex.encode(verifyCode)));
        String htmlContent = templateEngine.process("verification_email.html", ctx);
        try {
            emailSendingService.sendHtmlMail(
                    "18268291381@163.com",
                    "上海璀璨星屑网络科技工作室",
                    "《命运™》邮箱验证",
                    htmlContent);
        } catch (MessagingException e) {
            throw new EmailVerifySendingException("Exception occurs while sending verification email.", e);
        }
        return token.getId();
    }

    @Override
    public boolean verify(String emailId, String verifyCode) throws EmailVerifyExpiredException {
        Token token = tokenManager.getToken(emailId, false);
        if (token == null) throw new EmailVerifyExpiredException();
        byte[] verifyBytes = Hex.decode(verifyCode);
        return Arrays.equals((byte[]) token.getContent(), verifyBytes);
    }

    @Override
    public void expireEmail(String emailId) {
        Token token = tokenManager.getToken(emailId, false);
        if (token != null) token.expire();
    }
}
