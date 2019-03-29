package com.brightstar.trpgfate.component.ioc.email;

import javax.mail.MessagingException;

public interface EmailSender {
    void sendHtmlMail(String address, String senderName, String subject, String htmlContent) throws MessagingException;
}
