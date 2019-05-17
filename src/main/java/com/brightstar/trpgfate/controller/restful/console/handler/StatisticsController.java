package com.brightstar.trpgfate.controller.restful.console.handler;

import com.brightstar.trpgfate.controller.restful.console.vo.StatisticsGetCountResp;
import com.brightstar.trpgfate.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/console/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/users-count")
    public StatisticsGetCountResp getUsersCount() {
        StatisticsGetCountResp ret = new StatisticsGetCountResp();
        ret.setCount(statisticsService.getUsersCount());
        return ret;
    }

    @GetMapping("/gaming-records-count")
    public StatisticsGetCountResp getGamingRecordsCount() {
        StatisticsGetCountResp ret = new StatisticsGetCountResp();
        ret.setCount(statisticsService.getGamingRecordsCount());
        return ret;
    }

    @GetMapping("/gaming-records-count/last-{hours}-hours")
    public List<StatisticsGetCountResp> getGamingRecordsCountInLastHours(@PathVariable int hours) {
        ArrayList<StatisticsGetCountResp> ret = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        for (int i = 0; i < hours; ++i) {
            StatisticsGetCountResp countResp = new StatisticsGetCountResp();
            Calendar to = (Calendar) now.clone();
            now.add(Calendar.HOUR_OF_DAY, -1);
            countResp.setCount(statisticsService.getGamingRecordsCountBetween(now, to));
            ret.add(countResp);
        }
        return ret;
    }

}
