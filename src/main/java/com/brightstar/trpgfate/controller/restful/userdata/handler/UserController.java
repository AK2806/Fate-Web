package com.brightstar.trpgfate.controller.restful.userdata.handler;

import com.brightstar.trpgfate.controller.restful.userdata.vo.UserGetResp;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.AccountInfo;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/userdata/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @RequestMapping("/id/{id}")
    public UserGetResp getById(@PathVariable int id) {
        User user;
        try {
            user = userService.getUser(id);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return generateResponse(user);
    }

    @GetMapping
    @RequestMapping("/email/{email}")
    public UserGetResp getByEmail(@PathVariable String email) {
        User user;
        try {
            user = userService.getUser(email);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return generateResponse(user);
    }

    private UserGetResp generateResponse(User user) {
        AccountInfo accountInfo = userService.getAccountInfo(user);
        UserGetResp ret = new UserGetResp();
        ret.setUserId(user.getId());
        ret.setEmail(user.getEmail());
        ret.setRole(user.getRole());
        ret.setAvatarId(accountInfo.getAvatar().toString());
        ret.setName(accountInfo.getName());
        return ret;
    }
}
