package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.component.staticly.datetime.DatetimeConverter;
import com.brightstar.trpgfate.dao.StatisticsDAO;
import com.brightstar.trpgfate.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsDAO statisticsDAO;

    @Override
    public int getUsersCount() {
        return statisticsDAO.getUsersCount();
    }

    @Override
    public int getGamingRecordsCount() {
        return statisticsDAO.getRecordsCount();
    }

    @Override
    public int getGamingRecordsCountBetween(Calendar from, Calendar to) {
        return statisticsDAO.getRecordsCountBetween(
                DatetimeConverter.calendar2SqlTimestamp(from),
                DatetimeConverter.calendar2SqlTimestamp(to));
    }
}
