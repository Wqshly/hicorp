package com.hicorp.segment.mapper;

import com.hicorp.segment.mapper.basic.BasicMapper;
import com.hicorp.segment.pojo.DeviceCategory;


public interface DeviceCategoryMapper extends BasicMapper<DeviceCategory> {

    int updateNumber(String name, Integer newNumber);
}