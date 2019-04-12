package com.brightstar.trpgfate.controller.helper;

import com.brightstar.trpgfate.service.CommunityService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.AccountInfo;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserPrivacyChecker {
    @Autowired
    private RequestUserFetcher userFetcher;
    @Autowired
    private UserService userService;
    @Autowired
    private CommunityService communityService;

    @GetMapping
    public boolean isAccessible(User user, HttpServletRequest request) {
        AccountInfo accountInfo = userService.getAccountInfo(user);
        int privacy = accountInfo.getPrivacy();
        if (privacy == AccountInfo.PRIVACY_PUBLIC) {
            return true;
        } else {
            User self;
            try {
                self = userFetcher.fetch(request);
            } catch (UserDoesntExistException e) {
                return false;
            }
            if (self.getId() == user.getId() || self.getRole() > User.ROLE_USER) {
                return true;
            } else if (privacy == AccountInfo.PRIVACY_FOLLOWER) {
                if (communityService.isFollowing(user, self) != null
                        && communityService.isFollowing(self, user) != null) {
                    return true;
                }
            }
            return false;
        }
    }
}
