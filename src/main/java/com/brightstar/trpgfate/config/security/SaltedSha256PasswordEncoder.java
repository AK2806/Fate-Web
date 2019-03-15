package com.brightstar.trpgfate.config.security;


import org.springframework.security.crypto.password.AbstractPasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
public final class SaltedSha256PasswordEncoder extends AbstractPasswordEncoder {

    @Override
    protected byte[] encode(CharSequence rawPassword, byte[] salt) {
        byte[] utf8Bytes = rawPassword.toString().getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < utf8Bytes.length; ++i) {
            utf8Bytes[i] ^= salt[i % salt.length];
        }
        byte[] ret = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            ret = digest.digest(utf8Bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}