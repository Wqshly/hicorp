package com.hicorp.segment.service.impl;

import com.hicorp.segment.mapper.MaintenanceDailyMonitoringSystemRoomMapper;
import com.hicorp.segment.mapper.MaintenanceMajorHardwareMapper;
import com.hicorp.segment.mapper.MaintenanceMonthlyMonitoringSystemMapper;
import com.hicorp.segment.mapper.MaintenanceQuarterlyMonitoringSystemMapper;
import com.hicorp.segment.pojo.MaintenanceDailyMonitoringSystemRoom;
import com.hicorp.segment.service.MaintenanceDailyMonitoringSystemRoomService;
import com.hicorp.segment.utils.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("MaintenanceDailyMonitoringSystemRoomService")
public class MaintenanceDailyMonitoringSystemRoomServiceImpl extends BasicInterfaceImpl<MaintenanceDailyMonitoringSystemRoom> implements MaintenanceDailyMonitoringSystemRoomService {
    @Resource
    private MaintenanceDailyMonitoringSystemRoomMapper maintenanceDailyMonitoringSystemRoomMapper;
    @Resource
    private MaintenanceMonthlyMonitoringSystemMapper maintenanceMonthlyMonitoringSystemMapper;
    @Resource
    private MaintenanceQuarterlyMonitoringSystemMapper maintenanceQuarterlyMonitoringSystemMapper;
    @Resource
    private MaintenanceMajorHardwareMapper maintenanceMajorHardwareMapper;
    @Override
    public Void dataValid(MaintenanceDailyMonitoringSystemRoom maintenanceDailyMonitoringSystemRoom) {
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getName(), "设备名称不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getNumber(), "编号不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getMaintenanceUnit(), "维护单位不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getMaintenanceLocation(), "维护地点不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getElectricResult(), "电力结果不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getElectricConclusion(), "电力结论不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getTemperatureResult(), "温度结果不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getTemperatureConclusion(), "温度结论不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getHumidityResult(), "湿度结果不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getHumidityConclusion(), "湿度结论不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getDirtResult(), "防尘结果不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getDustProofConclusion(), "防尘结论不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getStorageResult(), "存储结果不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getStorageConclusion(), "存储结论不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getImageResult(), "图像结果不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getImageConclusion(), "图像结论不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getControlResult(), "控制结果不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getControlConclusion(), "控制结论不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getDeviceAttachmentResult(), "设备连接结果不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getDeviceAttachmentConclusion(), "设备连接结论不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getEngineer(), "维护工程师不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getDateDayEngineer(), "日期（维护工程师）不能为空!");
        Assert.notNull(maintenanceDailyMonitoringSystemRoom.getGmtCreate(), "创建时间不能为空!");
        return null;
    }

    @Override
    public ResultBean<List<Integer>> getNotApproveMaintenanceNumber(String status) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(maintenanceDailyMonitoringSystemRoomMapper.countNumber(status));
        list.add(maintenanceMonthlyMonitoringSystemMapper.countNumber(status));
        list.add(maintenanceQuarterlyMonitoringSystemMapper.countNumber(status));
        list.add(maintenanceMajorHardwareMapper.countNumber(status));
        list.add(maintenanceMajorHardwareMapper.countEquipmentReplacementNumber(status));
        log.info(String.valueOf(list));
        return new ResultBean<>(list);
    }

    @Override
    public ResultBean<List<Integer>> getTodayMaintenanceNumber(Date today) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(maintenanceDailyMonitoringSystemRoomMapper.countTodayNumber(today));
        list.add(maintenanceMonthlyMonitoringSystemMapper.countTodayNumber(today));
        list.add(maintenanceQuarterlyMonitoringSystemMapper.countTodayNumber(today));
        list.add(maintenanceMajorHardwareMapper.countTodayMajorHardwareNumber(today));
        list.add(maintenanceMajorHardwareMapper.countTodayEquipmentReplacementNumber(today));
        log.info(String.valueOf(list));
        return new ResultBean<>(list);
    }
}
