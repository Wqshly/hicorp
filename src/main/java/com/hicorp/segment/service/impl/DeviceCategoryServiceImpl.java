package com.hicorp.segment.service.impl;

import com.hicorp.segment.pojo.DeviceCategory;
import com.hicorp.segment.service.DeviceCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("DeviceCategoryService")
public class DeviceCategoryServiceImpl extends BasicInterfaceImpl<DeviceCategory> implements DeviceCategoryService {

    @Override
    public Void dataValid(DeviceCategory deviceCategory) {
        Assert.notNull(deviceCategory.getPurpose(), "产品用途不能为空!");
        Assert.notNull(deviceCategory.getName(), "产品名称不能为空!");
        Assert.notNull(deviceCategory.getBrand(), "品牌不能为空!");
        Assert.notNull(deviceCategory.getSpecificationModel(), "规格型号不能为空!");
        Assert.notNull(deviceCategory.getUnit(), "单位不能为空!");
        Assert.notNull(deviceCategory.getRemarks(), "备注不能为空!");
        Assert.notNull(deviceCategory.getUseBegin(), "开始使用时间不能为空!");
        Assert.notNull(deviceCategory.getGmtCreate(), "创建时间不能为空!");
        return null;
    }
}
