package com.hicorp.segment.service;

import com.hicorp.segment.pojo.MaintenanceDailyMonitoringSystemRoom;
import com.hicorp.segment.utils.ResultBean;

import java.util.Date;
import java.util.List;

public interface MaintenanceDailyMonitoringSystemRoomService extends BasicInterface<MaintenanceDailyMonitoringSystemRoom>{
    ResultBean<List<Integer>> getNotApproveMaintenanceNumber(String status);
    ResultBean<List<Integer>> getTodayMaintenanceNumber(Date today);
}
