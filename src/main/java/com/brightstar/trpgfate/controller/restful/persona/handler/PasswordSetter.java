package com.brightstar.trpgfate.controller.restful.persona.handler;

import com.brightstar.trpgfate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class PasswordSetter {
    @Autowired
    private UserService userService;

    @PutMapping
    public void updatePassword() {

    }
}
