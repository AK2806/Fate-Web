package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.service.AuthenticationService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserDisabledException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationManager authManager;

    @Override
    public String getAuthPrincipalName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        if (auth == null) return null;
        return auth.isAuthenticated() ? auth.getName() : null;
    }

    @Override
    public boolean authenticate(User user, String password) throws UserDisabledException {
        return authenticateCurrReq(user, password);
    }

    @Override
    public boolean authenticate(User user, String password, HttpSession session, int durationInSeconds) throws UserDisabledException {
        if (authenticateCurrReq(user, password)) {
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());
            session.setMaxInactiveInterval(durationInSeconds);
            return true;
        } else return false;
    }

    private boolean authenticateCurrReq(User user, String password) throws UserDisabledException {
        try {
            UsernamePasswordAuthenticationToken authReqToken
                    = new UsernamePasswordAuthenticationToken(String.valueOf(user.getId()), password);
            Authentication auth = authManager.authenticate(authReqToken);
            if (auth == null || !auth.isAuthenticated()) return false;
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);
            return true;
        } catch (AuthenticationException e) {
            if (e instanceof DisabledException) {
                throw new UserDisabledException(e);
            }
            return false;
        }
    }
}
