package com.brightstar.trpgfate.component.ioc.token;

import java.util.Calendar;

/**
 * Simple interface of random token
 * @author AK2806, 18268291381@163.com
 */
public interface Token {
    /**
     * Get the id of this token
     * @return the id of this token
     */
    String getId();

    /**
     * Check if this token is expired
     * @return expire status of this token
     */
    boolean isExpired();

    /**
     * Expire this token and set the content to be null.
     */
    void expire();

    /**
     * Set the expire time of this token.
     * If this token has already been expired, this method sets the content to null first.
     * @param calendarField time field of {@link java.util.Calendar}
     * @param duration period of validity
     */
    void refresh(int calendarField, int duration);

    /**
     * Set the expire time of this token.
     * If this token has already been expired, this method sets the content to null first.
     * @param expireTime new expire time of this token
     */
    void refresh(Calendar expireTime);

    /**
     * Get the expire time of this token.
     * @return the expire time of this token
     */
    Calendar getExpireTime();

    /**
     * Set the content of this token.
     * @param content the content object which needs to be bound
     */
    void setContent(Object content);

    /**
     * Get the content of this token.
     * If this token is expired, this method returns null.
     * @return the content object of this token. Will return null if this token is expired.
     */
    Object getContent();
}
