package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.EmailExistsException;
import com.brightstar.trpgfate.service.exception.EmailDoesntExistException;

import javax.servlet.http.HttpSession;

public interface UserService {
    int ROLE_USER = 0;
    int ROLE_ADMIN = 1;

    boolean authenticate(User user);
    boolean authenticate(User user, HttpSession session, int durationInSeconds);
    void register(User user, int role) throws EmailExistsException;
    void modifyPassword(User user) throws EmailDoesntExistException;
    boolean userExists(String email);
}
