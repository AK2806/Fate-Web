package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.component.token.Token;
import com.brightstar.trpgfate.component.token.TokenManager;
import com.brightstar.trpgfate.service.EmailSendService;
import com.brightstar.trpgfate.service.EmailVerifyService;
import com.brightstar.trpgfate.service.exception.EmailVerifyExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

@Service
public class EmailVerifyServiceImpl implements EmailVerifyService {
    @Autowired
    private ITemplateEngine templateEngine;
    @Autowired
    private EmailSendService emailSendService;
    @Autowired
    private TokenManager tokenManager;

    private SecureRandom secureRandom = new SecureRandom();

    @Override
    public void generateEmail(String emailAddr) throws MessagingException {
        Token token = tokenManager.getToken(emailAddr);
        token.refresh(Calendar.MINUTE, 30);
        byte[] verifyCode = new byte[3];
        secureRandom.nextBytes(verifyCode);
        token.setContent(verifyCode);
        Context ctx = new Context(Locale.CHINA);
        ctx.setVariable("verifyCode", String.valueOf(Hex.encode(verifyCode)).toUpperCase(Locale.CHINA));
        String htmlContent = templateEngine.process("verification_email.html", ctx);
        emailSendService.sendHtmlMail(
                emailAddr,
                "上海璀璨星屑网络科技工作室",
                "《命运™》邮箱验证",
                htmlContent);
    }

    @Override
    public boolean verify(String emailAddr, String verifyCode) throws EmailVerifyExpiredException {
        Token token = tokenManager.getToken(emailAddr, false);
        if (token == null) throw new EmailVerifyExpiredException();
        byte[] verifyBytes = Hex.decode(verifyCode);
        return Arrays.equals((byte[]) token.getContent(), verifyBytes);
    }

    @Override
    public void expireEmail(String emailAddr) {
        Token token = tokenManager.getToken(emailAddr, false);
        if (token != null) token.expire();
    }
}
