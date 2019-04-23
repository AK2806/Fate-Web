package com.brightstar.trpgfate.application.game_instance;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.UUID;

public interface GameInstance {
    UUID getGameUuid();
    Calendar getCreateTime();
    Process getProcess();
    InetAddress getSocketAddress();
    int getSocketPort();
}
