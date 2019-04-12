package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.component.staticly.datetime.DatetimeConverter;
import com.brightstar.trpgfate.config.custom_property.FollowerConfig;
import com.brightstar.trpgfate.dao.FollowerDAO;
import com.brightstar.trpgfate.dao.po.Follower;
import com.brightstar.trpgfate.service.CommunityService;
import com.brightstar.trpgfate.service.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private FollowerDAO followerDAO;
    @Autowired
    private FollowerConfig followerConfig;

    @Override
    public List<Integer> getFollowerIdOf(User user, int bundle) {
        ArrayList<Integer> ret = new ArrayList<>();
        List<Follower> pos = followerDAO.findFollowerByUserId(user.getId(), bundle * followerConfig.getBundleSize(), followerConfig.getBundleSize());
        for (Follower follower : pos) {
            ret.add(follower.getFollowerId());
        }
        return ret;
    }

    @Override
    public int getFollowerBundleCountOf(User user) {
        int count = followerDAO.getFollowerCountByUserId(user.getId());
        if (count % followerConfig.getBundleSize() != 0) {
            count /= followerConfig.getBundleSize();
            ++count;
        } else {
            count /= followerConfig.getBundleSize();
        }
        return count > 0 ? count : 1;
    }

    @Override
    public List<Integer> getFolloweeIdOf(User fan, int bundle) {
        ArrayList<Integer> ret = new ArrayList<>();
        List<Follower> pos = followerDAO.findFolloweeByFollowerId(fan.getId(), bundle * followerConfig.getBundleSize(), followerConfig.getBundleSize());
        for (Follower follower : pos) {
            ret.add(follower.getUserId());
        }
        return ret;
    }

    @Override
    public int getFolloweeBundleCountOf(User fan) {
        int count = followerDAO.getFolloweeCountByFollowerId(fan.getId());
        if (count % followerConfig.getBundleSize() != 0) {
            count /= followerConfig.getBundleSize();
            ++count;
        } else {
            count /= followerConfig.getBundleSize();
        }
        return count > 0 ? count : 1;
    }

    @Override
    public Calendar isFollowing(User fan, User user) {
        Follower po = followerDAO.get(user.getId(), fan.getId());
        return po != null ? DatetimeConverter.sqlTimestamp2Calendar(po.getTime()) : null;
    }

    @Override
    public void follow(User fan, User user) {
        Follower po = followerDAO.get(user.getId(), fan.getId());
        if (po != null) return;
        Follower follower = new Follower();
        follower.setUserId(user.getId());
        follower.setFollowerId(fan.getId());
        follower.setTime(DatetimeConverter.calendar2SqlTimestamp(Calendar.getInstance()));
        followerDAO.insert(follower);
    }

    @Override
    public void unfollow(User fan, User user) {
        Follower po = followerDAO.get(user.getId(), fan.getId());
        if (po == null) return;
        followerDAO.remove(po);
    }
}
