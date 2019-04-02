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
import java.util.Date;

@RestController
@RequestMapping("/persona/{id}/account-info")
public final class AccountInfoController {
    @Autowired
    private UserService userService;
    @Autowired
    private RequestUserFetcher userFetcher;
    @Autowired
    private CommunityService communityService;

    @PatchMapping
    public void updateAccountInfo(@PathVariable int id, @RequestBody @Valid AccountInfoPatchReq req) {
        User user = userFetcher.fetch();
        if (user.getRole() <= User.ROLE_ADMIN && user.getId() != id)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "没有权限修改此用户信息");
        User targetUser;
        try {
            targetUser = userService.getUser(id);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "用户不存在", e);
        }
        Account account = userService.getAccountInfo(targetUser);
        account.setName(req.getName());
        account.setGender(req.getGender());
        Date birthday = req.getBirthday();
        if (birthday != null) account.setBirthday(DatetimeConverter.utilDate2Calendar(req.getBirthday()));
        else account.setBirthday(null);
        account.setResidence(req.getResidence());
        account.setPrivacy(req.getPrivacy());
        userService.updateAccountInfo(targetUser, account);
    }

    @GetMapping
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
        if (self.getId() == target.getId()
                || privacy == Account.PRIVACY_PUBLIC
                || self.getRole() > User.ROLE_ADMIN) {
            return generateAccountInfoGetResp(account);
        }
        if (privacy == Account.PRIVACY_FOLLOWEE) {
            if (communityService.isFollowing(target, self)
                && communityService.isFollowing(self, target)) {
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
