package com.hicorp.segment.mapper;

import com.hicorp.segment.mapper.basic.BasicMapper;
import com.hicorp.segment.pojo.MaintenanceQuarterlyMonitoringSystem;

import java.util.Date;


public interface MaintenanceQuarterlyMonitoringSystemMapper extends BasicMapper<MaintenanceQuarterlyMonitoringSystem> {
    int countNumber(String status);
    int countTodayNumber(Date date);
    int countIsHaveNumber();
}