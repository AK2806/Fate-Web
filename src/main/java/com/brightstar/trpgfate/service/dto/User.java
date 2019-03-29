package com.brightstar.trpgfate.service.dto;

import java.util.Calendar;

public interface User {
    int ROLE_USER = 0;
    int ROLE_ADMIN = 1;
    int ROLE_SUPERADMIN = 2;

    int getId();
    String getEmail();
    int getRole();
    Calendar getCreateTime();
    boolean isActive();
}
