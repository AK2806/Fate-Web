package com.brightstar.trpgfate.component.ioc.token;

import java.util.UUID;

/**
 * Simple manager for random token
 * @author AK2806, 18268291381@163.com
 */
public interface TokenManager {
    /**
     * <p>
     * Get the token associated with the specific id or,
     * if there is no token matching the id or the matched token is expired,
     * and <code>create</code> is true, returns a new token.
     * </p>
     * <p>
     * If <code>create</code> is <code>false</code> and, there is no token matching
     * <code>tokenId</code> or the matched token is expired, this method returns <code>null</code>.
     * </p>
     * @param tokenId the token's id
     * @param create <code>true</code> to create a new <code>Token</code> with the specific id if necessary;
     *               <code>false</code> to return <code>null</code>
     *               if there's no matched token or the matched token is expired
     * @return the <code>Token</code> associated with the token id or
     *         <code>null</code> if <code>create</code> is <code>false</code>
     * @see #getToken(String)
     */
    Token getToken(String tokenId, boolean create);

    /**
     * Get the token associated with the specific id or,
     * if there is no token matching the id or the matched token is expired,
     * returns a new token.
     *
     * @param tokenId the token's id
     * @return the <code>Token</code> associated with the token id
     * @see #generateToken()
     */
    default Token getToken(String tokenId) {
        return getToken(tokenId, true);
    }

    /**
     * Generate a new token using UUID as its id.
     * @return a new token which uses UUID to generate its id
     * @see #getToken(String)
     */
    default Token generateToken() {
        return getToken(UUID.randomUUID().toString());
    }
}
