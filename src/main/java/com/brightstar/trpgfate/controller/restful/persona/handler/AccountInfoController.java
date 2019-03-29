package com.brightstar.trpgfate.controller.restful.persona.handler;

import com.brightstar.trpgfate.component.staticly.datetime.DatetimeConverter;
import com.brightstar.trpgfate.controller.helper.RequestUserFetcher;
import com.brightstar.trpgfate.controller.restful.persona.vo.AccountInfoPatchReq;
import com.brightstar.trpgfate.controller.restful.persona.vo.AccountInfoGetResp;
import com.brightstar.trpgfate.service.CommunityService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.Account;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Calendar;

@RestController
@RequestMapping("/persona/account-info")
public final class AccountInfoController {
    @Autowired
    private UserService userService;
    @Autowired
    private RequestUserFetcher userFetcher;
    @Autowired
    private CommunityService communityService;

    @PatchMapping
    public void updateAccountInfo(@RequestBody @Valid AccountInfoPatchReq req) {
        User user = userFetcher.fetch();
        Account account = userService.getAccountInfo(user);
        account.setName(req.getName());
        account.setGender(req.getGender());
        account.setBirthday(DatetimeConverter.utilDate2Calendar(req.getBirthday()));
        account.setResidence(req.getResidence());
        account.setPrivacy(req.getPrivacy());
        userService.updateAccountInfo(user, account);
    }

    @GetMapping
    @RequestMapping("/{id}")
    public AccountInfoGetResp fetchAccountInfo(@PathVariable int id) {
        User target;
        try {
            target = userService.getUser(id);
        } catch (UserDoesntExistException ignore) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "没有权限查看该用户信息");
        }
        User self = userFetcher.fetch();
        Account account = userService.getAccountInfo(target);
        int privacy = account.getPrivacy();
        if (self.getId() == target.getId() || privacy == Account.PRIVACY_PUBLIC) {
            return generateAccountInfoGetResp(account);
        }
        if (privacy == Account.PRIVACY_FOLLOWEE) {
            if (communityService.isFollowing(target, self)) {
                return generateAccountInfoGetResp(account);
            }
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "没有权限查看该用户信息");
    }

    private AccountInfoGetResp generateAccountInfoGetResp(Account account) {
        AccountInfoGetResp ret = new AccountInfoGetResp();
        ret.setAvatarId(account.getAvatar().toString());
        ret.setName(account.getName());
        ret.setGender(account.getGender());
        Calendar birthday = account.getBirthday();
        if (birthday != null) ret.setBirthday(DatetimeConverter.calendar2UtilDate(account.getBirthday()));
        else ret.setBirthday(null);
        ret.setResidence(account.getResidence());
        ret.setResidence(account.getResidence());
        ret.setPrivacy(account.getPrivacy());
        return ret;
    }
}
