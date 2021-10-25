package com.hicorp.segment.service;

import com.hicorp.segment.pojo.MaintenanceMonthlyMonitoringSystem;
import com.hicorp.segment.utils.ResultBean;

import java.util.List;

public interface MaintenanceMonthlyMonitoringSystemService extends BasicInterface<MaintenanceMonthlyMonitoringSystem>{
    ResultBean<List<Integer>> getIsHaveMonthlyAndQuarterlyMonitoringSystem();
}
