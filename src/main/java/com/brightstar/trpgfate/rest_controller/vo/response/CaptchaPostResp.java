package com.brightstar.trpgfate.rest_controller.vo.response;

public final class CaptchaPostResp {
    private String token;
    private String imgBs64;

    public CaptchaPostResp() {
    }

    public CaptchaPostResp(String token, String imgBs64) {
        this.token = token;
        this.imgBs64 = imgBs64;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImgBs64() {
        return imgBs64;
    }

    public void setImgBs64(String imgBs64) {
        this.imgBs64 = imgBs64;
    }
}
