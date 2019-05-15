package com.brightstar.trpgfate.controller.restful.persona.vo;

public final class GameTableGetInstanceResp {
    private String ipAddress;
    private int port;
    private String accessKeyHex;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAccessKeyHex() {
        return accessKeyHex;
    }

    public void setAccessKeyHex(String accessKeyHex) {
        this.accessKeyHex = accessKeyHex;
    }
}
