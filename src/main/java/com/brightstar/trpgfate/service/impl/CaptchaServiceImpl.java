package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.component.ioc.token.Token;
import com.brightstar.trpgfate.component.ioc.token.TokenManager;
import com.brightstar.trpgfate.service.Captcha;
import com.brightstar.trpgfate.service.CaptchaService;
import com.brightstar.trpgfate.service.exception.CaptchaExpiredException;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Calendar;
import java.util.Locale;

@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Autowired
    private TokenManager tokenManager;

    private final class CaptchaImpl implements Captcha {
        private Token token;

        public CaptchaImpl(Token token) {
            this.token = token;
        }

        @Override
        public String getId() {
            return token.getId();
        }

        @Override
        public String getImageBase64() {
            return ((String[]) token.getContent())[0];
        }

        @Override
        public boolean isExpired() {
            return token.isExpired();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CaptchaImpl && token.equals( ((CaptchaImpl) obj).token );
        }
    }

    @Override
    public Captcha generateCaptcha() {
        Token token = tokenManager.generateToken();
        token.refresh(Calendar.MINUTE, 5);
        try (ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream()) {
            String[] tokenContent = new String[2];
            SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
            specCaptcha.setCharType(com.wf.captcha.Captcha.TYPE_NUM_AND_UPPER);
            specCaptcha.out(pngOutputStream);
            tokenContent[0] = Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());
            tokenContent[1] = specCaptcha.text().toUpperCase(Locale.CHINA);
            token.setContent(tokenContent);
        } catch (Exception e) {
            e.printStackTrace();
            token.expire();
        }
        return new CaptchaImpl(token);
    }

    @Override
    public Captcha getCaptcha(String id) {
        Token token = tokenManager.getToken(id, false);
        return token != null ? new CaptchaImpl(token) : null;
    }

    @Override
    public boolean validate(String id, String text) throws CaptchaExpiredException {
        Token token = tokenManager.getToken(id, false);
        if (token == null) throw new CaptchaExpiredException();
        String[] content = (String[]) token.getContent();
        text = text.toUpperCase(Locale.CHINA);
        boolean result = text.equals(content[1]);
        token.expire();
        return result;
    }
}
