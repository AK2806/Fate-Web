package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.exception.EmailVerifyExpiredException;
import com.brightstar.trpgfate.service.exception.EmailVerifySendingException;

public interface EmailVerifyService {
    String generateEmail(String address) throws EmailVerifySendingException;
    boolean verify(String emailId, String verifyCode) throws EmailVerifyExpiredException;
    void expireEmail(String emailId);
}
