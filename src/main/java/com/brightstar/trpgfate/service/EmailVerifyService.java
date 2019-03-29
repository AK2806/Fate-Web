package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.exception.EmailVerifyExpiredException;
import com.brightstar.trpgfate.service.exception.MessageFailedException;

/**
 * Email address verifier
 * @author AK2806, 18268291381@163.com
 */
public interface EmailVerifyService {
    /**
     * Generate an verification email and send it to the specified address
     * @param emailAddr the email address which need to be verified
     * @throws MessageFailedException
     */
    void generateEmail(String emailAddr) throws MessageFailedException;

    /**
     * Check if the verification code matches email address
     * @param emailAddr the email address
     * @param verifyCode the verification code
     * @return the result of whether email address matches the code
     * @throws EmailVerifyExpiredException
     */
    boolean verify(String emailAddr, String verifyCode) throws EmailVerifyExpiredException;

    /**
     * Invalid a verification email by its address
     * @param emailAddr the verification token's id in <code>String</code>
     */
    void expireEmail(String emailAddr);
}
