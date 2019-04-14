package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.dto.User;

import java.util.Calendar;

public interface NotificationService {
    int getFollowersCountAfterLastRead(User user);
    int getAnnouncementsCountAfterLastRead(User user);
    void markReadFollowers(User user);
    void markReadAnnouncements(User user);
}
