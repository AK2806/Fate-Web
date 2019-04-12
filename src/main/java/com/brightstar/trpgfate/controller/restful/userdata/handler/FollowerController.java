package com.brightstar.trpgfate.controller.restful.userdata.handler;

import com.brightstar.trpgfate.controller.helper.UserPrivacyChecker;
import com.brightstar.trpgfate.controller.restful.userdata.vo.FollowerGetListGroupCountResp;
import com.brightstar.trpgfate.controller.restful.userdata.vo.FollowerGetListResp;
import com.brightstar.trpgfate.service.CommunityService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController("userdataFollower")
@RequestMapping("/userdata")
public class FollowerController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommunityService communityService;

    @GetMapping
    @RequestMapping("/follower/{userId}/{page}")
    public FollowerGetListResp getFollower(@PathVariable int userId, @PathVariable int page, HttpServletRequest request) {
        User target;
        try {
            target = userService.getUser(userId);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        FollowerGetListResp ret = new FollowerGetListResp();
        ret.setUsersId(communityService.getFollowerIdOf(target, page));
        return ret;
    }

    @GetMapping
    @RequestMapping("/follower/{userId}")
    public FollowerGetListGroupCountResp getFollowerGroupCount(@PathVariable int userId, HttpServletRequest request) {
        User target;
        try {
            target = userService.getUser(userId);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        FollowerGetListGroupCountResp ret = new FollowerGetListGroupCountResp();
        ret.setCount(communityService.getFollowerBundleCountOf(target));
        return ret;
    }

    @GetMapping
    @RequestMapping("/followee/{userId}/{page}")
    public FollowerGetListResp getFollowee(@PathVariable int userId, @PathVariable int page, HttpServletRequest request) {
        User target;
        try {
            target = userService.getUser(userId);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        FollowerGetListResp ret = new FollowerGetListResp();
        ret.setUsersId(communityService.getFolloweeIdOf(target, page));
        return ret;
    }

    @GetMapping
    @RequestMapping("/followee/{userId}")
    public FollowerGetListGroupCountResp getFolloweeGroupCount(@PathVariable int userId, HttpServletRequest request) {
        User target;
        try {
            target = userService.getUser(userId);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        FollowerGetListGroupCountResp ret = new FollowerGetListGroupCountResp();
        ret.setCount(communityService.getFolloweeBundleCountOf(target));
        return ret;
    }
}
