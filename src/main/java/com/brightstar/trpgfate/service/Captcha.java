package com.brightstar.trpgfate.service;

public interface Captcha {
    String getId();
    String getImageBase64();
    boolean isExpired();
}
