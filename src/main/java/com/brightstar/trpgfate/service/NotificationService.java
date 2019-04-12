package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.dto.User;

import java.util.Calendar;

public interface NotificationService {
    int getFollowersCountAfter(User user, Calendar lastTime);
    int getAnnouncementsCountAfter(User user, Calendar lastTime);
    void markRead(User user);
}
