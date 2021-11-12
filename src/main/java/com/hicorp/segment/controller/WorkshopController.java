package com.hicorp.segment.controller;


import com.github.pagehelper.PageInfo;
import com.hicorp.segment.pojo.Workshop;
import com.hicorp.segment.security.utils.SecurityUtils;
import com.hicorp.segment.service.WorkshopService;
import com.hicorp.segment.swagger.ApiVersion;
import com.hicorp.segment.swagger.ApiVersionConstant;
import com.hicorp.segment.utils.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/workshop")
@Api(tags = "工厂数据管理")
public class WorkshopController {

    private final WorkshopService workshopService;
    private final SecurityUtils securityUtils;

    public WorkshopController(SecurityUtils securityUtils, WorkshopService workshopService) {
        this.securityUtils = securityUtils;
        this.workshopService = workshopService;
    }

    // 设备类型维护
    @Operation(summary = "获取设备类型维护信息", description = "带参数为分页查询设备类型维护，不带为查询所有")
    @GetMapping("/list")
    public ResultBean<List<Workshop>> getBasicDetailList() {
        return workshopService.queryInfo();
    }

    @Operation(summary = "查询设备类型维护信息", description = "带参数为分页查询设备类型维护信息，不带为查询所有")
    @GetMapping(value = "/list/{type}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<Workshop>> getBasicDetailList(@RequestParam("page") Integer page,
                                                                        @RequestParam("size") Integer size,
                                                                        @RequestParam("sort") String sort,
                                                                        @PathVariable("type") String type) {
        return workshopService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询设备类型维护信息", description = "带参数为分页查询设备类型维护信息，不带为查询所有")
    @GetMapping(value = "/list/{type}/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<Workshop>> getBasicDetailList(@RequestParam("page") Integer page,
                                                                        @RequestParam("size") Integer size,
                                                                        @RequestParam("sort") String sort,
                                                                        @PathVariable("type") String type,
                                                                        @PathVariable("field") String field,
                                                                        @PathVariable("content") String content) {
        return workshopService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {ApiVersionConstant.VERSION_1_0_0, ApiVersionConstant.VERSION_1_0_1})
    @Operation(summary = "新增单个设备类型维护信息", description = "需要是设备类型维护信息，才能存上")
    @Parameter(name = "basicDetail", description = "设备类型维护信息实体", required = true)
    @PostMapping("/add")
    public ResultBean<Integer> addBasicDetail(@RequestBody Workshop basicDetail) {
        Date date = new Date();
        basicDetail.setCreateUser(securityUtils.getUserName());
        basicDetail.setCreateGmt(date);
        try {
            return workshopService.createRecord(basicDetail);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增设备类型维护信息", description = "根据设备类型维护信息实体创建设备类型维护信息")
    @Parameter(name = "deviceCategories", description = "设备类型维护信息实体", required = true)
    @PostMapping("/addList")
    public ResultBean<Integer> addBasicDetailList(@RequestBody List<Workshop> deviceCategories) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        deviceCategories.forEach(basicDetail -> {
            basicDetail.setCreateUser(userName);
            basicDetail.setCreateGmt(date);
        });
        return workshopService.createRecords(deviceCategories);
    }

    @ApiVersion(value = {ApiVersionConstant.VERSION_1_0_0, ApiVersionConstant.VERSION_1_0_1})
    @Operation(summary = "删除设备类型维护信息", description = "根据设备类型维护信息id删除设备类型维护信息")
    @Parameter(name = "id", description = "设备类型维护信息id", required = true)
    @DeleteMapping("/{id}")
    public ResultBean<Integer> deleteBasicDetail(@PathVariable(value = "id") Long id) {
        return workshopService.deleteRecord(id);
    }

    @Operation(summary = "批量删除设备类型维护信息", description = "根据设备类型维护信息id批量删除设备类型维护信息")
    @Parameter(name = "id", description = "设备类型维护信息id", required = true)
    @PostMapping("/deleteList")
    public ResultBean<Integer> deleteBasicDetailList(@RequestBody List<Object> ids) {
        return workshopService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新设备类型维护信息", description = "根据设备类型维护信息id更新设备类型维护信息")
    @Parameters({
            @Parameter(name = "id", description = "设备类型维护信息id", required = true),
            @Parameter(name = "basicDetail", description = "设备类型维护信息实体", required = true)})
    @PutMapping("/{id}")
    public ResultBean<Integer> updateBasicDetail(@PathVariable(value = "id") Long id, @RequestBody Workshop basicDetail) {
        Date date = new Date();
        basicDetail.setId(id);
        basicDetail.setModifiedUser(securityUtils.getUserName());
        basicDetail.setModifiedGmt(date);
        try {
            return workshopService.updateNotNull(basicDetail);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

}
