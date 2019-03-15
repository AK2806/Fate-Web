package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.EmailExistsException;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpSession;

public interface UserService {
    int ROLE_USER = 0;
    int ROLE_ADMIN = 1;

    boolean authenticate(User user, HttpSession session, int duration);
    void register(User user, int role) throws EmailExistsException;
}
