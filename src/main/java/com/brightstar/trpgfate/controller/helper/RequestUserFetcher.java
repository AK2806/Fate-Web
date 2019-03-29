package com.brightstar.trpgfate.controller.helper;

import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class RequestUserFetcher {
    @Autowired
    private UserService userService;

    public User fetch() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        if (auth == null) return null;
        int id = Integer.parseInt(auth.getName());
        try {
            return userService.getUser(id);
        } catch (UserDoesntExistException e) {
            return null;
        }
    }
}
