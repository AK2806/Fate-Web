package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.dto.AccountInfo;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserConflictException;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;

public interface UserService {
    User getUser(int id) throws UserDoesntExistException;
    User getUser(String email) throws UserDoesntExistException;

    void registerWithEmail(String email, String password, int role) throws UserConflictException;
    void modifyPassword(User user, String newPassword);

    AccountInfo getAccountInfo(User user);
    void updateAccountInfo(User user, AccountInfo accountInfo);
}
