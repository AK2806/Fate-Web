package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.component.token.Token;
import com.brightstar.trpgfate.component.token.TokenManager;
import com.brightstar.trpgfate.service.EmailSendService;
import com.brightstar.trpgfate.service.PasswordResetService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.EmailDoesntExistException;
import com.brightstar.trpgfate.service.exception.ResetterExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.util.Calendar;
import java.util.Locale;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSendService emailSendService;
    @Autowired
    private ITemplateEngine templateEngine;

    @Override
    public void generateResetter(String emailAddr) throws MessagingException, EmailDoesntExistException {
        if (!userService.userExists(emailAddr)) throw new EmailDoesntExistException();
        Token token = tokenManager.generateToken();
        token.refresh(Calendar.MINUTE, 10);
        token.setContent(emailAddr);
        Context ctx = new Context(Locale.CHINA);
        ctx.setVariable("username", emailAddr);
        ctx.setVariable("token", token.getId());
        String htmlContent = templateEngine.process("passwd_reset_email.html", ctx);
        emailSendService.sendHtmlMail(
                emailAddr,
                "上海璀璨星屑网络科技工作室",
                "《命运™》账户密码重置",
                htmlContent);
    }

    @Override
    public void resetPassword(String tokenId, String newPassword) throws ResetterExpiredException {
        Token token = tokenManager.getToken(tokenId, false);
        if (token == null) throw new ResetterExpiredException();
        String email = (String) token.getContent();
        User user = new User();
        user.setEmail(email);
        user.setPassword(newPassword);
        try {
            userService.modifyPassword(user);
        } catch (EmailDoesntExistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void expireResetter(String tokenId) {
        Token token = tokenManager.getToken(tokenId, false);
        if (token != null) token.expire();
    }
}
