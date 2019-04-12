package com.brightstar.trpgfate.service;

import com.brightstar.trpgfate.service.dto.Announcement;

import java.util.List;

public interface AnnouncementService {
    int getAnnouncementBundleCount();
    List<Announcement> getAnnouncements(int bundle);
    void addAnnouncement(Announcement announcement);
}
