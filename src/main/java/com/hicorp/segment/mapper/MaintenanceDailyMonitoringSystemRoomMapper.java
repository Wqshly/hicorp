package com.hicorp.segment.mapper;

import com.hicorp.segment.mapper.basic.BasicMapper;
import com.hicorp.segment.pojo.MaintenanceDailyMonitoringSystemRoom;

import java.util.Date;


public interface MaintenanceDailyMonitoringSystemRoomMapper extends BasicMapper<MaintenanceDailyMonitoringSystemRoom> {
    int countNumber(String status);
    int countTodayNumber(Date date);
}