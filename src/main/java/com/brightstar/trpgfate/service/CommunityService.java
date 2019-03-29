package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.dto.User;

import java.util.Collection;

public interface CommunityService {
    Collection<User> getFollowersOf(User user, int start, int count);
    boolean isFollowing(User fan, User user);
    void follow(User fan, User user);
}
