package com.brightstar.trpgfate.controller.restful.console.handler;

import com.brightstar.trpgfate.controller.restful.console.vo.AnnouncementPostReq;
import com.brightstar.trpgfate.service.AnnouncementService;
import com.brightstar.trpgfate.service.dto.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("consoleAnnouncement")
@RequestMapping("/console/announcement")
public final class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    @PostMapping
    public void postAnnouncement(@RequestBody @Valid AnnouncementPostReq req) {
         Announcement announcement = new Announcement();
         announcement.setTitle(req.getTitle());
         announcement.setHtmlContent(req.getHtmlContent());
         announcementService.addAnnouncement(announcement);
    }
}
