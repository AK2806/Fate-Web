package com.brightstar.trpgfate.controller.restful.persona.vo;

import javax.validation.constraints.*;
import java.util.Date;

public final class AccountInfoPatchReq {
    @Size(max = 50)
    private String name;
    @Min(0) @Max(2)
    private int gender;
    @Past
    private Date birthday;
    @Size(max = 100)
    private String residence;
    @Min(0) @Max(2)
    private int privacy;

    public AccountInfoPatchReq() {
    }

    public AccountInfoPatchReq(@Size(max = 50) String name, @Min(0) @Max(2) int gender, @Past Date birthday, @Size(max = 100) String residence, @Min(0) @Max(2) int privacy) {
        this.name = name;
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
