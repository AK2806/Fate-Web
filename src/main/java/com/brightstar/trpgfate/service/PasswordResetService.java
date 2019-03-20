package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.exception.EmailDoesntExistException;
import com.brightstar.trpgfate.service.exception.ResetterExpiredException;

import javax.mail.MessagingException;

public interface PasswordResetService {
    /**
     * Generate a password resetter and send its link with the token's id to the specified email address
     * @param emailAddr the email address which is sent to
     * @throws MessagingException
     */
    void generateResetter(String emailAddr) throws MessagingException, EmailDoesntExistException;

    /**
     * Reset password by resetter's <code>tokenId</code>
     * @param tokenId token's id of the resetter in <code>String</code>
     * @param newPassword new password for matched user
     * @throws ResetterExpiredException
     */
    void resetPassword(String tokenId, String newPassword) throws ResetterExpiredException;

    /**
     * Invalid a resetter by its <code>tokenId</code>
     * @param tokenId the verification token's id in <code>String</code>
     */
    void expireResetter(String tokenId);
}
