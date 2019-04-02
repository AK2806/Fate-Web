package com.brightstar.trpgfate.controller.restful.persona.handler;

import com.brightstar.trpgfate.controller.helper.RequestUserFetcher;
import com.brightstar.trpgfate.controller.restful.persona.vo.PasswordSetterPutReq;
import com.brightstar.trpgfate.service.AuthenticationService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserDisabledException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    public void updatePassword(@RequestBody @Valid PasswordSetterPutReq req) {
        User user = userFetcher.fetch();
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
