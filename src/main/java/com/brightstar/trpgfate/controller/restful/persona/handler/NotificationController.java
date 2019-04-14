package com.brightstar.trpgfate.controller.restful.persona.handler;

import com.brightstar.trpgfate.controller.helper.RequestUserFetcher;
import com.brightstar.trpgfate.controller.restful.persona.vo.NotificationGetResp;
import com.brightstar.trpgfate.service.NotificationService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController("personaNotification")
@RequestMapping("/persona/notification")
public class NotificationController {
    @Autowired
    private RequestUserFetcher userFetcher;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/announcement")
    public NotificationGetResp getUnreadAnnouncementCount(HttpServletRequest request) {
        User user;
        try {
            user = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        NotificationGetResp ret = new NotificationGetResp();
        ret.setCount(notificationService.getAnnouncementsCountAfterLastRead(user));
        return ret;
    }

    @PutMapping("/announcement")
    public void markReadAnnouncements(HttpServletRequest request) {
        User user;
        try {
            user = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        notificationService.markReadAnnouncements(user);
    }

    @GetMapping("/follower")
    public NotificationGetResp getNewFollowerCount(HttpServletRequest request) {
        User user;
        try {
            user = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        NotificationGetResp ret = new NotificationGetResp();
        ret.setCount(notificationService.getFollowersCountAfterLastRead(user));
        return ret;
    }

    @PutMapping("/follower")
    public void markReadFollowers(HttpServletRequest request) {
        User user;
        try {
            user = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        notificationService.markReadFollowers(user);
    }
}
