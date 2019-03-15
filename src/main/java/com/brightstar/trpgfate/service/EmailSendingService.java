package com.brightstar.trpgfate.service;

import javax.mail.MessagingException;

public interface EmailSendingService {
    void sendHtmlMail(String address, String senderName, String subject, String htmlContent) throws MessagingException;
}
