package com.brightstar.trpgfate.controller.restful.general.handler;

import com.brightstar.trpgfate.component.staticly.datetime.DatetimeConverter;
import com.brightstar.trpgfate.controller.restful.general.vo.AnnouncementGetPageCountResp;
import com.brightstar.trpgfate.controller.restful.general.vo.AnnouncementGetResp;
import com.brightstar.trpgfate.service.dto.Announcement;
import com.brightstar.trpgfate.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("generalAnnouncement")
@RequestMapping("/announcement")
public final class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    @GetMapping
    @RequestMapping("/{page}")
    public List<AnnouncementGetResp> getAnnouncements(@PathVariable int page) {
        List<Announcement> announcements = announcementService.getAnnouncements(page);
        List<AnnouncementGetResp> ret = new ArrayList<>();
        for (Announcement announcement : announcements) {
            AnnouncementGetResp resp = new AnnouncementGetResp();
            resp.setTitle(announcement.getTitle());
            resp.setHtmlContent(announcement.getHtmlContent());
            resp.setCreateTime(DatetimeConverter.calendar2UtilDate(announcement.getCreateTime()));
            ret.add(resp);
        }
        return ret;
    }

    @GetMapping
    public AnnouncementGetPageCountResp getAnnouncementPagesCount() {
        AnnouncementGetPageCountResp ret = new AnnouncementGetPageCountResp();
        ret.setCount(announcementService.getAnnouncementBundleCount());
        return ret;
    }
}
