package com.brightstar.trpgfate.dao.po;

import java.sql.Date;

public final class Account {
    private int userId;
    private String name;
    private byte[] avatarId;
    private int gender;
    private Date birthday;
    private String residence;
    private int privacy;

    public Account() {
    }

    public Account(int userId, String name, byte[] avatarId, int gender, Date birthday, String residence, int privacy) {
        this.userId = userId;
        this.name = name;
        this.avatarId = avatarId;
        this.gender = gender;
        this.birthday = birthday;
        this.residence = residence;
        this.privacy = privacy;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(byte[] avatarId) {
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
