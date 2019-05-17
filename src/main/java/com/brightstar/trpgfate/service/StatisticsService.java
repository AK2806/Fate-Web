package com.brightstar.trpgfate.service;

import java.util.Calendar;

public interface StatisticsService {
    int getUsersCount();
    int getGamingRecordsCount();
    int getGamingRecordsCountBetween(Calendar from, Calendar to);
}
