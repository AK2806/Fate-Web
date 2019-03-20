package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.service.EmailSendService;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

@Service
public final class EmailSendServiceImpl implements EmailSendService {
    private static final String QQ_MAIL_ADDRESS = "642207429@qq.com";
    private static final String MAIL_AUTHORIZATION = "roiuqwjheotbbejg";
    private static final Properties PROPS = new Properties();
    private static final Authenticator authenticator = new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(QQ_MAIL_ADDRESS, MAIL_AUTHORIZATION);
        }
    };

    static {
        PROPS.put("mail.transport.protocol", "smtp");
        PROPS.put("mail.smtp.host", "smtp.qq.com");
        PROPS.put("mail.smtp.port", "465");
        PROPS.put("mail.smtp.auth", "true");
        PROPS.put("mail.smtp.starttls.enable", "true");
        PROPS.put("mail.smtp.ssl.enable", "true");
    }

    public void sendHtmlMail(String address, String senderName, String subject, String htmlContent) throws MessagingException {
        Session session = Session.getInstance(PROPS, authenticator);

        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(QQ_MAIL_ADDRESS, senderName, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
        msg.setSubject(subject, "utf-8");
        msg.setContent(htmlContent, "text/html; charset=utf-8");
        msg.setSentDate(new Date());

        Transport.send(msg);
    }
}
