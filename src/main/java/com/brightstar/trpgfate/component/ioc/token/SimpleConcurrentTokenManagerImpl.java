package com.brightstar.trpgfate.component.ioc.token;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class SimpleConcurrentTokenManagerImpl implements TokenManager {
    private final class TokenImpl implements Token {
        private final String id;
        private Calendar expireTime;
        private Object content;

        public TokenImpl(String id) {
            this.id = id;
            this.content = null;
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 30);
            this.expireTime = calendar;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public boolean isExpired() {
            return Calendar.getInstance().after(expireTime);
        }

        @Override
        public void expire() {
            expireTime = Calendar.getInstance();
            content = null;
        }

        @Override
        public void refresh(int calendarField, int duration) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(calendarField, duration);
            refresh(calendar);
        }

        @Override
        public void refresh(Calendar expireTime) {
            if (isExpired()) content = null;
            this.expireTime = expireTime;
        }

        @Override
        public Calendar getExpireTime() {
            return expireTime;
        }

        @Override
        public void setContent(Object content) {
            this.content = content;
        }

        @Override
        public Object getContent() {
            return !isExpired() ? content : null;
        }
    }

    private ConcurrentMap<String, Token> tokenMap = new ConcurrentHashMap<>();

    @Override
    public Token getToken(String tokenId, boolean create) {
        Token token = tokenMap.get(tokenId);
        if (token == null) {
            if (create) {
                token = new TokenImpl(tokenId);
                tokenMap.put(tokenId, token);
            }
            return token;
        }
        if (token.isExpired()) {
            if (create) {
                token.refresh(Calendar.MINUTE, 30);
            } else {
                tokenMap.remove(tokenId);
                return null;
            }
        }
        return token;
    }

    @Scheduled(fixedDelay = 180000L)
    private void cleanupMap() {
        tokenMap.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
}
