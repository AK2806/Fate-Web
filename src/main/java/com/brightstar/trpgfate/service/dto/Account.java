package com.brightstar.trpgfate.service.dto;

import java.util.Calendar;
import java.util.UUID;

public final class Account {
    public static final int GENDER_MALE = 0;
    public static final int GENDER_FEMALE = 1;
    public static final int GENDER_UNKNOWN = 2;

    public static final int PRIVACY_PRIVATE = 0;
    public static final int PRIVACY_FOLLOWEE = 1;
    public static final int PRIVACY_PUBLIC = 2;

    private String name;
    private UUID avatar;
    private int gender;
    private Calendar birthday;
    private String residence;
    private int privacy;

    public Account() {
    }

    public Account(String name, UUID avatar, int gender, Calendar birthday, String residence, int privacy) {
        this.name = name;
        this.avatar = avatar;
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

    public UUID getAvatar() {
        return avatar;
    }

    public void setAvatar(UUID avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
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
