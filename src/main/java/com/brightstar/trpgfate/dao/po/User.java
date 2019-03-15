package com.brightstar.trpgfate.dao.po;

public final class User {
    private String email;
    private byte[] passwdSha256Salted;
    private int role;

    public User() {
    }

    public User(String email, byte[] passwdSha256Salted, int role) {
        this.email = email;
        this.passwdSha256Salted = passwdSha256Salted;
        this.role = role;
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
}
