package com.hicorp.segment.mapper;


import com.hicorp.segment.mapper.basic.BasicMapper;
import com.hicorp.segment.pojo.DeviceInfo;

import java.util.List;

public interface DeviceInfoMapper extends BasicMapper<DeviceInfo> {
    int countNumber(String name);
    String selectNameById(Integer id);
    List<DeviceInfo> selectByName(String name);
}
