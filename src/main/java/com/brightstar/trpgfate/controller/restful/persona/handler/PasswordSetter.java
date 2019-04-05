package com.brightstar.trpgfate.controller.restful.persona.handler;

import com.brightstar.trpgfate.controller.helper.RequestUserFetcher;
import com.brightstar.trpgfate.controller.restful.persona.vo.PasswordSetterPutReq;
import com.brightstar.trpgfate.service.AuthenticationService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserDisabledException;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/persona/password")
public final class PasswordSetter {
    @Autowired
    private UserService userService;
    @Autowired
    private RequestUserFetcher userFetcher;
    @Autowired
    private AuthenticationService authenticationService;

    @PutMapping
    public void updatePassword(@RequestBody @Valid PasswordSetterPutReq req, HttpServletRequest request) {
        User user;
        try {
            user = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid session", e);
        }
        boolean auth;
        try {
            auth = authenticationService.authenticate(user, req.getOldPasswd());
        } catch (UserDisabledException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "账户已冻结", e);
        }
        if (!auth) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "密码错误");
        userService.modifyPassword(user, req.getNewPasswd());
    }
}
