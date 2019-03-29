package com.brightstar.trpgfate.dao.po;

import com.brightstar.trpgfate.service.UserService;

import java.sql.Timestamp;

public final class User {
    private int id;
    private String email;
    private byte[] passwdSha256Salted;
    private int role;
    private boolean active;
    private Timestamp createTime;

    public User() {
    }

    public User(int id, String email, byte[] passwdSha256Salted, int role, boolean active, Timestamp createTime) {
        this.id = id;
        this.email = email;
        this.passwdSha256Salted = passwdSha256Salted;
        this.role = role;
        this.active = active;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPasswdSha256Salted() {
        return passwdSha256Salted;
    }

    public void setPasswdSha256Salted(byte[] passwdSha256Salted) {
        this.passwdSha256Salted = passwdSha256Salted;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
