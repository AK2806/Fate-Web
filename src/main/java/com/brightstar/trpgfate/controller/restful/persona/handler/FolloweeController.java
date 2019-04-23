package com.brightstar.trpgfate.controller.restful.persona.handler;

import com.brightstar.trpgfate.controller.helper.RequestUserFetcher;
import com.brightstar.trpgfate.controller.restful.persona.vo.FolloweePutReq;
import com.brightstar.trpgfate.service.CommunityService;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController("personaFollowee")
@RequestMapping("/persona/followee")
public final class FolloweeController {
    @Autowired
    private RequestUserFetcher userFetcher;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private UserService userService;

    @PutMapping
    public void toggleFollowing(@RequestBody @Valid FolloweePutReq req, HttpServletRequest request) {
        User user;
        try {
            user = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        User target;
        try {
            target = userService.getUser(req.getUserId());
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (communityService.isFollowing(user, target) != null) {
            communityService.unfollow(user, target);
        } else {
            communityService.follow(user, target);
        }
    }

    @GetMapping
    @RequestMapping("/{followeeId}")
    public void getFollowingStatus(@PathVariable int followeeId, HttpServletRequest request) {
        User user;
        try {
            user = userFetcher.fetch(request);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        User followee;
        try {
            followee = userService.getUser(followeeId);
        } catch (UserDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (communityService.isFollowing(user, followee) == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
