package com.hicorp.segment.service.impl;

import com.hicorp.segment.mapper.MaintenanceMonthlyMonitoringSystemMapper;
import com.hicorp.segment.mapper.MaintenanceQuarterlyMonitoringSystemMapper;
import com.hicorp.segment.pojo.MaintenanceMonthlyMonitoringSystem;
import com.hicorp.segment.service.MaintenanceMonthlyMonitoringSystemService;
import com.hicorp.segment.utils.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("MaintenanceMonthlyMonitoringSystemService")
public class MaintenanceMonthlyMonitoringSystemServiceImpl extends BasicInterfaceImpl<MaintenanceMonthlyMonitoringSystem> implements MaintenanceMonthlyMonitoringSystemService {
    @Resource
    private MaintenanceMonthlyMonitoringSystemMapper maintenanceMonthlyMonitoringSystemMapper;
    @Resource
    private MaintenanceQuarterlyMonitoringSystemMapper maintenanceQuarterlyMonitoringSystemMapper;
    @Override
    public Void dataValid(MaintenanceMonthlyMonitoringSystem maintenanceMonthlyMonitoringSystem) {
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getMaintenanceUnit(), "保养单位不能为空!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getResettlementSite(), "设备安置点不能为空!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getWork1Result(), "保养工作内容1不能为空!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getWork2Result(), "保养工作内容2不能为空!!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getWork3Result(), "保养工作内容3不能为空!!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getWork4Result(), "保养工作内容4不能为空!!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getWork5Result(), "保养工作内容5不能为空!!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getWork6Result(), "保养工作内容6不能为空!!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getWork7Result(), "保养工作内容7不能为空!!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getWork8Result(), "保养工作内容8不能为空!!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getWork9Result(), "保养工作内容9不能为空!!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getWork10Result(), "保养工作内容10不能为空!!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getWork11Result(), "保养工作内容11不能为空!!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getWork12Result(), "保养工作内容12不能为空!!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getWork13Result(), "保养工作内容13不能为空!!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getWork14Result(), "保养工作内容14不能为空!!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getWork15Result(), "保养工作内容15不能为空!!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getDateDay(), "保养日期不能为空!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getArriveTime(), "到达时间不能为空!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getLeaveTime(), "离开时间不能为空!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getInformationNote(), "保养情况说明不能为空!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getEngineer(), "保养维修人员不能为空!");
        Assert.notNull(maintenanceMonthlyMonitoringSystem.getGmtCreate(), "创建时间不能为空!");
        return null;
    }

    @Override
    public ResultBean<List<Integer>> getIsHaveMonthlyAndQuarterlyMonitoringSystem() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(maintenanceMonthlyMonitoringSystemMapper.countIsHaveNumber());
        list.add(maintenanceQuarterlyMonitoringSystemMapper.countIsHaveNumber());
        log.info(String.valueOf(list));
        return new ResultBean<>(list);
    }
}
