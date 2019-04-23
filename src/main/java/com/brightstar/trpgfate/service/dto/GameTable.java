package com.brightstar.trpgfate.service.dto;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.UUID;

public final class GameTable {
    public static final int TYPE_FAST = 0;
    public static final int TYPE_FULL = 1;

    private UUID gameUuid;
    private Process gameProcess;
    private InetAddress socketAddress;
    private int socketPort;
    private Calendar createTime;

    public UUID getGameUuid() {
        return gameUuid;
    }

    public void setGameUuid(UUID gameUuid) {
        this.gameUuid = gameUuid;
    }

    public Process getGameProcess() {
        return gameProcess;
    }

    public void setGameProcess(Process gameProcess) {
        this.gameProcess = gameProcess;
    }

    public InetAddress getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(InetAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public int getSocketPort() {
        return socketPort;
    }

    public void setSocketPort(int socketPort) {
        this.socketPort = socketPort;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }
}
