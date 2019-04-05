package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.component.ioc.token.Token;
import com.brightstar.trpgfate.component.ioc.token.TokenManager;
import com.brightstar.trpgfate.component.ioc.email.EmailSender;
import com.brightstar.trpgfate.service.PasswordResetService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.AccountInfo;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.MessageFailedException;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import com.brightstar.trpgfate.service.exception.ResetterExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.Locale;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private ITemplateEngine templateEngine;

    public String generateResetterByEmail(User user) throws MessagingException {
        int id = user.getId();
        String emailAddr = user.getEmail();
        Token token = tokenManager.generateToken();
        token.refresh(Calendar.MINUTE, 10);
        token.setContent(id);
        Context ctx = new Context(Locale.CHINA);
        ctx.setVariable("pid", id);
        ctx.setVariable("tokenBs64", Base64.getEncoder().encodeToString(token.getId().getBytes(StandardCharsets.UTF_8)));
        String htmlContent = templateEngine.process("passwd_reset_email.html", ctx);
        emailSender.sendHtmlMail(
                emailAddr,
                "上海璀璨星屑网络科技工作室",
                "《命运™》账户密码重置",
                htmlContent);
        return token.getId();
    }

    public String generateResetterByPhoneMail(User user) {
        throw new IllegalArgumentException("method phone mail is not implemented");
    }

    @Override
    public String generateResetter(User user, int method) throws MessageFailedException {
        switch (method) {
            case METHOD_E_MAIL:
                try {
                    return generateResetterByEmail(user);
                } catch (MessagingException e) {
                    throw new MessageFailedException(e);
                }
            case METHOD_PHONE_MAIL:
                return generateResetterByPhoneMail(user);
            default:
                throw new IllegalArgumentException("method is not supported");
        }
    }

    @Override
    public void resetPassword(String tokenId, User user, String newPassword) throws ResetterExpiredException {
        Token token = tokenManager.getToken(tokenId, false);
        if (token == null) throw new ResetterExpiredException();
        int id = (int) token.getContent();
        if (user.getId() != id) throw new ResetterExpiredException();
        userService.modifyPassword(user, newPassword);
    }

    @Override
    public void expireResetter(String tokenId) {
        Token token = tokenManager.getToken(tokenId, false);
        if (token != null) token.expire();
    }
}
