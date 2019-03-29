package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.MessageFailedException;
import com.brightstar.trpgfate.service.exception.ResetterExpiredException;

public interface PasswordResetService {
    int METHOD_E_MAIL = 0;
    int METHOD_PHONE_MAIL = 1;

    /**
     * Generate a password resetter and send its link to the specified email address
     * @param user the user who wants to reset password
     * @param method the way to reset password
     * @return token's id of the generated resetter
     * @throws MessageFailedException if sending message is failed
     */
    String generateResetter(User user, int method) throws MessageFailedException;

    /**
     * Reset password by resetter's <code>tokenId</code>
     * @param tokenId token's id of the resetter in <code>String</code>
     * @param user the user attached to the token
     * @param newPassword new password for matched user
     * @throws ResetterExpiredException if resetter is expired
     */
    void resetPassword(String tokenId, User user, String newPassword) throws ResetterExpiredException;

    /**
     * Invalid a resetter by its <code>tokenId</code>
     * @param tokenId the verification token's id in <code>String</code>
     */
    void expireResetter(String tokenId);
}
