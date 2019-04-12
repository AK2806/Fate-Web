package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.dto.User;

import java.util.Calendar;
import java.util.List;

public interface CommunityService {
    List<Integer> getFollowerIdOf(User user, int bundle);
    int getFollowerBundleCountOf(User user);
    List<Integer> getFolloweeIdOf(User fan, int bundle);
    int getFolloweeBundleCountOf(User fan);
    Calendar isFollowing(User fan, User user);
    void follow(User fan, User user);
    void unfollow(User fan, User user);
}
