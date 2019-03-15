package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.component.token.Token;
import com.brightstar.trpgfate.component.token.TokenManager;
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
        String[] tokenContent = new String[2];
        token.setContent(tokenContent);
        tokenContent[0] = "";
        tokenContent[1] = null;
        try (ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream()) {
            SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
            specCaptcha.setCharType(com.wf.captcha.Captcha.TYPE_NUM_AND_UPPER);
            specCaptcha.out(pngOutputStream);
            tokenContent[0] = Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());
            tokenContent[1] = specCaptcha.text().toUpperCase(Locale.ROOT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CaptchaImpl(token);
    }

    @Override
    public Captcha getCaptcha(String id) {
        Token token = tokenManager.getToken(id, false);
        return token != null ? new CaptchaImpl(token) : null;
    }

    @Override
    public boolean validate(String captchaId, String text) throws CaptchaExpiredException {
        Token token = tokenManager.getToken(captchaId, false);
        if (token == null) throw new CaptchaExpiredException();
        String[] content = (String[]) token.getContent();
        text = text.toUpperCase(Locale.ROOT);
        boolean result = text.equals(content[1]);
        token.expire();
        return result;
    }
}
