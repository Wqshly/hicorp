package com.hicorp.segment.controller;

import com.github.pagehelper.PageInfo;
import com.hicorp.segment.pojo.DeviceCategory;
import com.hicorp.segment.pojo.DeviceInfo;
import com.hicorp.segment.security.utils.SecurityUtils;
import com.hicorp.segment.service.DeviceCategoryService;
import com.hicorp.segment.service.DeviceInfoService;
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
@Api(tags = "设备管理")
public class DeviceController {
    private final DeviceCategoryService deviceCategoryService;
    private final DeviceInfoService deviceInfoService;
    private final SecurityUtils securityUtils;

    public DeviceController(SecurityUtils securityUtils, DeviceCategoryService deviceCategoryService, DeviceInfoService deviceInfoService) {
        this.securityUtils = securityUtils;
        this.deviceCategoryService = deviceCategoryService;
        this.deviceInfoService = deviceInfoService;
    }

    // 设备类型维护
    @Operation(summary = "获取设备类型维护信息", description = "带参数为分页查询设备类型维护，不带为查询所有")
    @GetMapping("/deviceCategory/list")
    public ResultBean<List<DeviceCategory>> getDeviceCategoryList() {
        return deviceCategoryService.queryInfo();
    }


    @Operation(summary = "查询设备类型维护信息", description = "带参数为分页查询设备类型维护信息，不带为查询所有")
    @GetMapping(value = "/deviceCategory/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<DeviceCategory>> getDeviceCategoryList(@RequestParam("page") Integer page,
                                                                      @RequestParam("size") Integer size,
                                                                      @RequestParam("sort") String sort) {
        return deviceCategoryService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询设备类型维护信息", description = "带参数为分页查询设备类型维护信息，不带为查询所有")
    @GetMapping(value = "/deviceCategory/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<DeviceCategory>> getDeviceCategoryList(@RequestParam("page") Integer page,
                                                                      @RequestParam("size") Integer size,
                                                                      @RequestParam("sort") String sort,
                                                                      @PathVariable("field") String field,
                                                                      @PathVariable("content") String content) {
        return deviceCategoryService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {ApiVersionConstant.VERSION_1_0_0, ApiVersionConstant.VERSION_1_0_1})
    @Operation(summary = "新增单个设备类型维护信息", description = "需要是设备类型维护信息，才能存上")
    @Parameter(name = "deviceCategory", description = "设备类型维护信息实体", required = true)
    @PostMapping("/deviceCategory/add")
    public ResultBean<Integer> addDeviceCategory(@RequestBody DeviceCategory deviceCategory) {
        Date date = new Date();
        deviceCategory.setCreateUser(securityUtils.getUserName());
        deviceCategory.setGmtCreate(date);
        try {
            return deviceCategoryService.createRecord(deviceCategory);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增设备类型维护信息", description = "根据设备类型维护信息实体创建设备类型维护信息")
    @Parameter(name = "deviceCategories", description = "设备类型维护信息实体", required = true)
    @PostMapping("/deviceCategory/addList")
    public ResultBean<Integer> addDeviceCategoryList(@RequestBody List<DeviceCategory> deviceCategories) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        deviceCategories.forEach(deviceCategory -> {
            deviceCategory.setCreateUser(userName);
            deviceCategory.setGmtCreate(date);
        });
        return deviceCategoryService.createRecords(deviceCategories);
    }


    @ApiVersion(value = {ApiVersionConstant.VERSION_1_0_0, ApiVersionConstant.VERSION_1_0_1})
    @Operation(summary = "删除设备类型维护信息", description = "根据设备类型维护信息id删除设备类型维护信息")
    @Parameter(name = "id", description = "设备类型维护信息id", required = true)
    @DeleteMapping("/deviceCategory/{id}")
    public ResultBean<Integer> deleteDeviceCategory(@PathVariable(value = "id") Long id) {
        return deviceCategoryService.deleteRecord(id);
    }

    @Operation(summary = "批量删除设备类型维护信息", description = "根据设备类型维护信息id批量删除设备类型维护信息")
    @Parameter(name = "id", description = "设备类型维护信息id", required = true)
    @PostMapping("/deviceCategory/deleteList")
    public ResultBean<Integer> deleteDeviceCategoryList(@RequestBody List<Object> ids) {
        return deviceCategoryService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新设备类型维护信息", description = "根据设备类型维护信息id更新设备类型维护信息")
    @Parameters({
            @Parameter(name = "id", description = "设备类型维护信息id", required = true),
            @Parameter(name = "deviceCategory", description = "设备类型维护信息实体", required = true)})
    @PutMapping("/deviceCategory/{id}")
    public ResultBean<Integer> updateDeviceCategory(@PathVariable(value = "id") Long id, @RequestBody DeviceCategory deviceCategory) {
        Date date = new Date();
        deviceCategory.setId(id);
        deviceCategory.setModifiedUser(securityUtils.getUserName());
        deviceCategory.setGmtModified(date);
        try {
            return deviceCategoryService.updateNotNull(deviceCategory);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

    // 设备信息维护
    @Operation(summary = "获取设备信息维护信息", description = "带参数为分页查询设备信息维护信息，不带为查询所有")
    @GetMapping("/deviceInfo/list")
    public ResultBean<List<DeviceInfo>> getDeviceInfoList() {
        return deviceInfoService.queryInfo();
    }

    // 条件查询，查询某设备名称下的设备信息
    @Operation(summary = "通过设备名称获取设备信息维护信息", description = "带参数为分页查询设备信息维护信息，不带为查询所有")
    @GetMapping(value = "/deviceInfo/listByName/{name}")
    public ResultBean<List<DeviceInfo>> getDeviceInfoList(@PathVariable("name") String name) {
        return deviceInfoService.getDeviceInfoListByName(name);
    }

    @Operation(summary = "查询设备信息维护信息", description = "带参数为分页查询设备信息维护信息，不带为查询所有")
    @GetMapping(value = "/deviceInfo/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<DeviceInfo>> getDeviceInfoList(@RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size,
                                                              @RequestParam("sort") String sort) {
        return deviceInfoService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询设备信息维护信息", description = "带参数为分页查询设备信息维护信息，不带为查询所有")
    @GetMapping(value = "/deviceInfo/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<DeviceInfo>> getDeviceInfoList(@RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size,
                                                              @RequestParam("sort") String sort,
                                                              @PathVariable("field") String field,
                                                              @PathVariable("content") String content) {
        return deviceInfoService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {ApiVersionConstant.VERSION_1_0_0, ApiVersionConstant.VERSION_1_0_1})
    @Operation(summary = "新增单个设备信息维护信息", description = "需要是设备信息维护信息，才能存上")
    @Parameter(name = "deviceInfo", description = "设备信息维护信息实体", required = true)
    @PostMapping("/deviceInfo/add")
    public ResultBean<Integer> addDeviceInfo(@RequestBody DeviceInfo deviceInfo) {
        Date date = new Date();
        deviceInfo.setCreateUser(securityUtils.getUserName());
        deviceInfo.setGmtCreate(date);
        try {
            return deviceInfoService.createRecord(deviceInfo);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增设备信息维护信息", description = "根据巡设备信息维护信息实体创建巡检项目")
    @Parameter(name = "deviceInfos", description = "设备信息维护信息实体", required = true)
    @PostMapping("/deviceInfo/addList")
    public ResultBean<Integer> addDeviceInfoList(@RequestBody List<DeviceInfo> deviceInfos) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        deviceInfos.forEach(deviceInfo -> {
            deviceInfo.setCreateUser(userName);
            deviceInfo.setGmtCreate(date);
        });
        return deviceInfoService.createRecords(deviceInfos);
    }


    @ApiVersion(value = {ApiVersionConstant.VERSION_1_0_0, ApiVersionConstant.VERSION_1_0_1})
    @Operation(summary = "删除设备信息维护信息", description = "根据设备信息维护信息id删除设备信息维护信息")
    @Parameter(name = "id", description = "设备信息维护信息id", required = true)
    @DeleteMapping("/deviceInfo/{id}")
    public ResultBean<Integer> deleteDeviceInfo(@PathVariable(value = "id") Long id) {
        return deviceInfoService.deleteRecord(id);
    }

    @Operation(summary = "批量删除设备信息维护信息", description = "根据设备信息维护信息批量删除设备信息维护信息")
    @Parameter(name = "id", description = "设备信息维护信息id", required = true)
    @PostMapping("/deviceInfo/deleteList")
    public ResultBean<Integer> deleteDeviceInfoList(@RequestBody List<Object> ids) {
        return deviceInfoService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新设备信息维护信息", description = "根据设备信息维护信息id更新设备信息维护信息")
    @Parameters({
            @Parameter(name = "id", description = "设备信息维护信息id", required = true),
            @Parameter(name = "deviceInfo", description = "设备信息维护信息实体", required = true)})
    @PutMapping("/deviceInfo/{id}")
    public ResultBean<Integer> updateDeviceInfo(@PathVariable(value = "id") Long id, @RequestBody DeviceInfo deviceInfo) {
        Date date = new Date();
        deviceInfo.setId(id);
        deviceInfo.setModifiedUser(securityUtils.getUserName());
        deviceInfo.setGmtModified(date);
        try {
            return deviceInfoService.updateNotNull(deviceInfo);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

}
