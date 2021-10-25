package com.hicorp.segment.service.impl;

import com.hicorp.segment.pojo.MaintenanceQuarterlyMonitoringSystem;
import com.hicorp.segment.service.MaintenanceQuarterlyMonitoringSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service("MaintenanceQuarterlyMonitoringSystemService")
public class MaintenanceQuarterlyMonitoringSystemServiceImpl extends BasicInterfaceImpl<MaintenanceQuarterlyMonitoringSystem> implements MaintenanceQuarterlyMonitoringSystemService {

    @Override
    public Void dataValid(MaintenanceQuarterlyMonitoringSystem maintenanceQuarterlyMonitoringSystem) {
        Assert.notNull(maintenanceQuarterlyMonitoringSystem.getMaintenanceUnit(), "保养单位不能为空!");
        Assert.notNull(maintenanceQuarterlyMonitoringSystem.getResettlementSite(), "设备安置点不能为空!");
        Assert.notNull(maintenanceQuarterlyMonitoringSystem.getWork1Result(), "保养工作内容1不能为空!");
        Assert.notNull(maintenanceQuarterlyMonitoringSystem.getWork2Result(), "保养工作内容2不能为空!!");
        Assert.notNull(maintenanceQuarterlyMonitoringSystem.getWork3Result(), "保养工作内容3不能为空!!");
        Assert.notNull(maintenanceQuarterlyMonitoringSystem.getWork4Result(), "保养工作内容4不能为空!!");
        Assert.notNull(maintenanceQuarterlyMonitoringSystem.getWork5Result(), "保养工作内容5不能为空!!");
        Assert.notNull(maintenanceQuarterlyMonitoringSystem.getWork6Result(), "保养工作内容6不能为空!!");
        Assert.notNull(maintenanceQuarterlyMonitoringSystem.getWork7Result(), "保养工作内容7不能为空!!");
        Assert.notNull(maintenanceQuarterlyMonitoringSystem.getWork8Result(), "保养工作内容8不能为空!!");
        Assert.notNull(maintenanceQuarterlyMonitoringSystem.getDateDay(), "保养日期不能为空!");
        Assert.notNull(maintenanceQuarterlyMonitoringSystem.getArriveTime(), "到达时间不能为空!");
        Assert.notNull(maintenanceQuarterlyMonitoringSystem.getLeaveTime(), "离开时间不能为空!");
        Assert.notNull(maintenanceQuarterlyMonitoringSystem.getInformationNote(), "保养情况说明不能为空!");
        Assert.notNull(maintenanceQuarterlyMonitoringSystem.getEngineer(), "保养维修人员不能为空!");
        Assert.notNull(maintenanceQuarterlyMonitoringSystem.getGmtCreate(), "创建时间不能为空!");
        return null;
    }

}
