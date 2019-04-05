package com.brightstar.trpgfate.controller.restful.userdata.handler;

import com.brightstar.trpgfate.component.staticly.datetime.DatetimeConverter;
import com.brightstar.trpgfate.controller.helper.UserPrivacyChecker;
import com.brightstar.trpgfate.controller.restful.userdata.vo.AccountInfoGetResp;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@RestController("userdataAccountInfoController")
@RequestMapping("/userdata/account-info/{userId}")
public final class AccountInfoController {
    @Autowired
    private UserPrivacyChecker privacyChecker;
    @Autowired
    private UserService userService;

    @GetMapping
    public AccountInfoGetResp fetchAccountInfo(@PathVariable int userId, HttpServletRequest request) {
        User target;
        try {
            target = userService.getUser(userId);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!privacyChecker.isAccessible(target, request)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "由于ta的隐私设置，你无法查看ta的资料");
        AccountInfo accountInfo = userService.getAccountInfo(target);
        AccountInfoGetResp ret = new AccountInfoGetResp();
        ret.setAvatarId(accountInfo.getAvatar().toString());
        ret.setName(accountInfo.getName());
        ret.setGender(accountInfo.getGender());
        Calendar birthday = accountInfo.getBirthday();
        if (birthday != null) ret.setBirthday(DatetimeConverter.calendar2UtilDate(accountInfo.getBirthday()));
        else ret.setBirthday(null);
        ret.setResidence(accountInfo.getResidence());
        ret.setResidence(accountInfo.getResidence());
        ret.setPrivacy(accountInfo.getPrivacy());
        return ret;
    }
}
