package com.brightstar.trpgfate.controller.restful.persona.vo;

public final class AvatarPostResp {
    private String uuid;

    public AvatarPostResp() {
    }

    public AvatarPostResp(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
