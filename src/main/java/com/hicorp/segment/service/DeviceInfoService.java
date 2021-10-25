package com.hicorp.segment.service;

import com.hicorp.segment.pojo.DeviceInfo;
import com.hicorp.segment.utils.ResultBean;

import java.util.List;

public interface DeviceInfoService extends BasicInterface<DeviceInfo>{
    ResultBean<List<DeviceInfo>> getDeviceInfoListByName(String name);
}
