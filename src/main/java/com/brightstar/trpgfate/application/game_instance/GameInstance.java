package com.brightstar.trpgfate.application.game_instance;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

public interface GameInstance {
    UUID getGameUuid();
    Calendar getCreateTime();
    String getLog();
    InetAddress getSocketAddress();
    int getSocketPort();
    Map<Integer, byte[]> getAccessKeys();
}
