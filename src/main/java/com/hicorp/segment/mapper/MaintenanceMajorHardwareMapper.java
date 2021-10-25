package com.hicorp.segment.mapper;

import com.hicorp.segment.mapper.basic.BasicMapper;
import com.hicorp.segment.pojo.MaintenanceMajorHardware;

import java.util.Date;


public interface MaintenanceMajorHardwareMapper extends BasicMapper<MaintenanceMajorHardware> {
    int countNumber(String status);
    int countEquipmentReplacementNumber(String status);
    int countTodayMajorHardwareNumber(Date date);
    int countTodayEquipmentReplacementNumber(Date date);

}