package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.component.staticly.datetime.DatetimeConverter;
import com.brightstar.trpgfate.config.custom_property.AnnouncementConfig;
import com.brightstar.trpgfate.config.custom_property.FollowerConfig;
import com.brightstar.trpgfate.dao.AnnouncementDAO;
import com.brightstar.trpgfate.dao.FollowerDAO;
import com.brightstar.trpgfate.dao.NotificationDAO;
import com.brightstar.trpgfate.dao.po.Notification;
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
    public int getFollowersCountAfterLastRead(User user) {
        Notification po = notificationDAO.getByUserId(user.getId());
        return followerDAO.getFollowerCountByUserIdAndTime(user.getId(), po.getLastViewFollowerTime());
    }

    @Override
    public int getAnnouncementsCountAfterLastRead(User user) {
        Notification po = notificationDAO.getByUserId(user.getId());
        return announcementDAO.getCount(po.getLastReadAnnouncementId() + 1);
    }

    @Override
    public void markReadFollowers(User user) {
        Notification po = notificationDAO.getByUserId(user.getId());
        po.setLastViewFollowerTime(DatetimeConverter.calendar2SqlTimestamp(Calendar.getInstance()));
        notificationDAO.update(po);
    }

    @Override
    public void markReadAnnouncements(User user) {
        Notification po = notificationDAO.getByUserId(user.getId());
        po.setLastReadAnnouncementId(announcementDAO.getLastAnnouncement().getId());
        notificationDAO.update(po);
    }
}
