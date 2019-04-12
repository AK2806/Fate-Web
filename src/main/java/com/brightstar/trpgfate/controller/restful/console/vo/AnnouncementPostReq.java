package com.brightstar.trpgfate.controller.restful.console.vo;

import javax.validation.constraints.NotBlank;

public final class AnnouncementPostReq {
    @NotBlank
    private String title;
    @NotBlank
    private String htmlContent;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
