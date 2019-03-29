package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.service.CommunityService;
import com.brightstar.trpgfate.service.dto.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CommunityServiceImpl implements CommunityService {
    @Override
    public Collection<User> getFollowersOf(User user, int start, int count) {
        return null;
    }

    @Override
    public boolean isFollowing(User fan, User user) {
        return false;
    }

    @Override
    public void follow(User fan, User user) {

    }
}
