package com.brightstar.trpgfate.controller.restful.persona.vo;

import java.util.Date;

public final class AccountInfoGetResp {
    private String name;
    private String avatarId;
    private int gender;
    private Date birthday;
    private String residence;
    private int privacy;

    public AccountInfoGetResp() {
    }

    public AccountInfoGetResp(String name, String avatarId, int gender, Date birthday, String residence, int privacy) {
        this.name = name;
        this.avatarId = avatarId;
        this.gender = gender;
        this.birthday = birthday;
        this.residence = residence;
        this.privacy = privacy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public int getPrivacy() {
        return privacy;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }
}
