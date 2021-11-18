package com.hicorp.segment.controller;

import com.github.pagehelper.PageInfo;
import com.hicorp.segment.pojo.ApproveProcess;
import com.hicorp.segment.security.utils.SecurityUtils;
import com.hicorp.segment.service.ApproveProcessService;
import com.hicorp.segment.swagger.ApiVersion;
import com.hicorp.segment.swagger.ApiVersionConstant;
import com.hicorp.segment.utils.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/approval")
@Api(tags = "审批流定义")
public class ApproveProcessController {

    private final ApproveProcessService approveProcessService;

    private final SecurityUtils securityUtils;

    public ApproveProcessController(SecurityUtils securityUtils, ApproveProcessService approveProcessService) {
        this.securityUtils = securityUtils;
        this.approveProcessService = approveProcessService;
    }

    // 设备类型维护
    @Operation(summary = "获取设备类型维护信息", description = "带参数为分页查询设备类型维护，不带为查询所有")
    @GetMapping("/list")
    public ResultBean<List<ApproveProcess>> getApproveProcessList() {
        return approveProcessService.queryInfo();
    }

    @Operation(summary = "查询审批流", description = "带参数为分页查询设备类型维护信息，不带为查询所有")
    @GetMapping(value = "/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<ApproveProcess>> getApproveProcessList(@RequestParam("page") Integer page,
                                                                      @RequestParam("size") Integer size,
                                                                      @RequestParam("sort") String sort) {
        return approveProcessService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询设备类型维护信息", description = "带参数为分页查询设备类型维护信息，不带为查询所有")
    @GetMapping(value = "/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<ApproveProcess>> getApproveProcessList(@RequestParam("page") Integer page,
                                                                      @RequestParam("size") Integer size,
                                                                      @RequestParam("sort") String sort,
                                                                      @PathVariable("field") String field,
                                                                      @PathVariable("content") String content) {
        return approveProcessService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {ApiVersionConstant.VERSION_1_0_0, ApiVersionConstant.VERSION_1_0_1})
    @Operation(summary = "新增审批流定义", description = "新增审批流")
    @Parameter(name = "approveProcess", description = "审批流实体", required = true)
    @PostMapping("/add")
    public ResultBean<Integer> addApproveProcess(@RequestBody ApproveProcess approveProcess) {
        approveProcess.setCreateUser(securityUtils.getUserName());
        try {
            return approveProcessService.createRecord(approveProcess);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }

    @Operation(summary = "更新审批流", description = "根据id更新审批流")
    @Parameters({
            @Parameter(name = "id", description = "审批流的ID", required = true),
            @Parameter(name = "approveProcess", description = "设备类型维护信息实体", required = true)})
    @PutMapping("/{id}")
    public ResultBean<Integer> updateApproveProcess(@PathVariable(value = "id") Long id, @RequestBody ApproveProcess approveProcess) {
        approveProcess.setId(id);
        approveProcess.setModifiedUser(securityUtils.getUserName());
        try {
            return approveProcessService.updateNotNull(approveProcess);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

    @Operation(summary = "删除审批流定义", description = "根据主键完成批量删除")
    @Parameter(name = "id", description = "待删除的数据的主键(id)", required = true)
    @PostMapping("/deleteList")
    public ResultBean<Integer> deleteApproveProcessList(@RequestBody List<Object> ids) {
        return approveProcessService.deleteRecords("id", ids);
    }

}
