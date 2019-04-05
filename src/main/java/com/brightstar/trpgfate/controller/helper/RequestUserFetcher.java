package com.brightstar.trpgfate.controller.helper;

import com.brightstar.trpgfate.service.AuthenticationService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestUserFetcher {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authService;

    public User fetch(HttpServletRequest request) throws UserDoesntExistException {
        String idStr = authService.getAuthPrincipalName(request.getSession());
        if (idStr == null) throw new UserDoesntExistException();
        try {
            int id = Integer.parseInt(idStr);
            return userService.getUser(id);
        } catch (Exception e) {
            throw new UserDoesntExistException();
        }
    }
}
