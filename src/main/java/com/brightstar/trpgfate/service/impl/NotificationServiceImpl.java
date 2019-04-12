package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.dao.AnnouncementDAO;
import com.brightstar.trpgfate.dao.FollowerDAO;
import com.brightstar.trpgfate.dao.NotificationDAO;
import com.brightstar.trpgfate.service.NotificationService;
import com.brightstar.trpgfate.service.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationDAO notificationDAO;
    @Autowired
    private AnnouncementDAO announcementDAO;
    @Autowired
    private FollowerDAO followerDAO;

    @Override
    public int getFollowersCountAfter(User user, Calendar lastTime) {

        return 0;
    }

    @Override
    public int getAnnouncementsCountAfter(User user, Calendar lastTime) {

        return 0;
    }

    @Override
    public void markRead(User user) {

    }
}
