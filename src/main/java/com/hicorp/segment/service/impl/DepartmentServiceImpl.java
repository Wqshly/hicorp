package com.hicorp.segment.service.impl;

import com.hicorp.segment.pojo.Department;
import com.hicorp.segment.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @Author:
 * @Date: Created in 10:09 2021/5/19
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Service("departmentService")
public class DepartmentServiceImpl extends BasicInterfaceImpl<Department> implements DepartmentService {
    @Override
    public Void dataValid(Department department) {
        Assert.notNull(department.getNumber(), "部门编号不能为空!");
        Assert.notNull(department.getName(), "部门名称不能为空!");
        Assert.notNull(department.getGmtCreate(), "创建时间不能为空!");
        return null;
    }
}
