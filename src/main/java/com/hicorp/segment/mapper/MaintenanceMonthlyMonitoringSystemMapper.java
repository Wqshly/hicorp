package com.hicorp.segment.mapper;

import com.hicorp.segment.mapper.basic.BasicMapper;
import com.hicorp.segment.pojo.MaintenanceMonthlyMonitoringSystem;

import java.util.Date;


public interface MaintenanceMonthlyMonitoringSystemMapper extends BasicMapper<MaintenanceMonthlyMonitoringSystem> {
    int countNumber(String status);
    int countTodayNumber(Date date);
    int countIsHaveNumber();
}