package com.brightstar.trpgfate.controller.restful.persona.handler;

import com.brightstar.trpgfate.component.staticly.datetime.DatetimeConverter;
import com.brightstar.trpgfate.controller.helper.RequestUserFetcher;
import com.brightstar.trpgfate.controller.restful.persona.vo.AccountInfoPatchReq;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.AccountInfo;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@RestController("personaAccountInfoController")
@RequestMapping("/persona/account-info")
public final class AccountInfoController {
    @Autowired
    private UserService userService;
    @Autowired
    private RequestUserFetcher userFetcher;

    @PatchMapping
    public void updateAccountInfo(@RequestBody @Valid AccountInfoPatchReq req, HttpServletRequest request) {
        User user;
        try {
            user = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid session", e);
        }
        AccountInfo accountInfo = userService.getAccountInfo(user);
        accountInfo.setName(req.getName());
        accountInfo.setGender(req.getGender());
        Date birthday = req.getBirthday();
        if (birthday != null) accountInfo.setBirthday(DatetimeConverter.utilDate2Calendar(req.getBirthday()));
        else accountInfo.setBirthday(null);
        accountInfo.setResidence(req.getResidence());
        accountInfo.setPrivacy(req.getPrivacy());
        userService.updateAccountInfo(user, accountInfo);
    }

}
