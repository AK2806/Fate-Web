package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserDisabledException;

import javax.servlet.http.HttpSession;

public interface AuthenticationService {
    String getAuthPrincipalName();
    boolean authenticate(User user, String password) throws UserDisabledException;
    boolean authenticate(User user, String password, HttpSession session, int durationInSeconds) throws UserDisabledException;
}
