package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.component.staticly.datetime.DatetimeConverter;
import com.brightstar.trpgfate.config.custom_property.AnnouncementConfig;
import com.brightstar.trpgfate.dao.AnnouncementDAO;
import com.brightstar.trpgfate.service.AnnouncementService;
import com.brightstar.trpgfate.service.dto.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    private AnnouncementDAO announcementDAO;
    @Autowired
    private AnnouncementConfig announcementConfig;

    @Override
    public int getAnnouncementBundleCount() {
        int count = announcementDAO.getCount(0);
        if (count % announcementConfig.getBundleSize() != 0) {
            count /= announcementConfig.getBundleSize();
            ++count;
        } else {
            count /= announcementConfig.getBundleSize();
        }
        return count > 0 ? count : 1;
    }

    @Override
    public List<Announcement> getAnnouncements(int bundle) {
        List<com.brightstar.trpgfate.dao.po.Announcement> pos =
                announcementDAO.getAnnouncements(bundle * announcementConfig.getBundleSize(), announcementConfig.getBundleSize());
        List<Announcement> ret = new ArrayList<>();
        for (com.brightstar.trpgfate.dao.po.Announcement po : pos) {
            Announcement announcement = new Announcement();
            announcement.setCreateTime(DatetimeConverter.sqlTimestamp2Calendar(po.getCreateTime()));
            announcement.setTitle(po.getTitle());
            announcement.setHtmlContent(po.getContent());
            ret.add(announcement);
        }
        return ret;
    }

    @Override
    public void addAnnouncement(Announcement announcement) {
        com.brightstar.trpgfate.dao.po.Announcement po = new com.brightstar.trpgfate.dao.po.Announcement();
        Calendar now = Calendar.getInstance();
        announcement.setCreateTime(now);
        po.setCreateTime(DatetimeConverter.calendar2SqlTimestamp(now));
        po.setTitle(announcement.getTitle());
        po.setContent(announcement.getHtmlContent());
        announcementDAO.insert(po);
    }
}
