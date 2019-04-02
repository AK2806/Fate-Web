package com.brightstar.trpgfate.controller.helper;

import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class RequestUserFetcher {
    @Autowired
    private UserService userService;

    public User fetch() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        if (auth == null) throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid session");
        int id = Integer.parseInt(auth.getName());
        try {
            return userService.getUser(id);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid session");
        }
    }
}
