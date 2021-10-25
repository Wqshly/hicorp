package com.hicorp.segment.service.impl;

import com.hicorp.segment.pojo.BomMain;
import com.hicorp.segment.service.BomMainService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @Author:
 * @Date: Created in 10:09 2021/5/19
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Service("bomMainService")
public class BomMainServiceImpl extends BasicInterfaceImpl<BomMain> implements BomMainService {
    @Override
    public Void dataValid(BomMain department) {
        Assert.notNull(department.getName(), "部门名称不能为空!");
        return null;
    }
}
