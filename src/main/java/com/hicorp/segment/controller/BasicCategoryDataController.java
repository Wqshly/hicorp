package com.hicorp.segment.controller;


import com.github.pagehelper.PageInfo;
import com.hicorp.segment.pojo.BasicCategoryData;
import com.hicorp.segment.security.utils.SecurityUtils;
import com.hicorp.segment.service.BasicCategoryDataService;
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
@RequestMapping("/basicCategoryData")
@Api(tags = "基础数据管理")
public class BasicCategoryDataController {

    private final BasicCategoryDataService basicCategoryDataService;
    private final SecurityUtils securityUtils;

    public BasicCategoryDataController(SecurityUtils securityUtils, BasicCategoryDataService basicCategoryDataService) {
        this.securityUtils = securityUtils;
        this.basicCategoryDataService = basicCategoryDataService;
    }

    // 设备类型维护
    @Operation(summary = "获取设备类型维护信息", description = "带参数为分页查询设备类型维护，不带为查询所有")
    @GetMapping("/list")
    public ResultBean<List<BasicCategoryData>> getBasicCategoryList() {
        return basicCategoryDataService.queryInfo();
    }

    @Operation(summary = "查询设备类型维护信息", description = "带参数为分页查询设备类型维护信息，不带为查询所有")
    @GetMapping(value = "/list/{type}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<BasicCategoryData>> getBasicCategoryList(@RequestParam("page") Integer page,
                                                                        @RequestParam("size") Integer size,
                                                                        @RequestParam("sort") String sort,
                                                                        @PathVariable("type") String type) {
        return basicCategoryDataService.queryInfo(page, size, sort, type);
    }

    @Operation(summary = "查询设备类型维护信息", description = "带参数为分页查询设备类型维护信息，不带为查询所有")
    @GetMapping(value = "/list/{type}/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<BasicCategoryData>> getBasicCategoryList(@RequestParam("page") Integer page,
                                                                        @RequestParam("size") Integer size,
                                                                        @RequestParam("sort") String sort,
                                                                        @PathVariable("type") String type,
                                                                        @PathVariable("field") String field,
                                                                        @PathVariable("content") String content) {
        return basicCategoryDataService.selectRecordsByPage(page, size, sort, type, field, content);
    }

    @ApiVersion(value = {ApiVersionConstant.VERSION_1_0_0, ApiVersionConstant.VERSION_1_0_1})
    @Operation(summary = "新增单个设备类型维护信息", description = "需要是设备类型维护信息，才能存上")
    @Parameter(name = "basicCategory", description = "设备类型维护信息实体", required = true)
    @PostMapping("/add")
    public ResultBean<Integer> addBasicCategory(@RequestBody BasicCategoryData basicCategory) {
        Date date = new Date();
        basicCategory.setCreateUser(securityUtils.getUserName());
        basicCategory.setCreateGmt(date);
        try {
            return basicCategoryDataService.createRecord(basicCategory);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增设备类型维护信息", description = "根据设备类型维护信息实体创建设备类型维护信息")
    @Parameter(name = "deviceCategories", description = "设备类型维护信息实体", required = true)
    @PostMapping("/addList")
    public ResultBean<Integer> addBasicCategoryList(@RequestBody List<BasicCategoryData> deviceCategories) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        deviceCategories.forEach(basicCategory -> {
            basicCategory.setCreateUser(userName);
            basicCategory.setCreateGmt(date);
        });
        return basicCategoryDataService.createRecords(deviceCategories);
    }

    @ApiVersion(value = {ApiVersionConstant.VERSION_1_0_0, ApiVersionConstant.VERSION_1_0_1})
    @Operation(summary = "删除设备类型维护信息", description = "根据设备类型维护信息id删除设备类型维护信息")
    @Parameter(name = "id", description = "设备类型维护信息id", required = true)
    @DeleteMapping("/{id}")
    public ResultBean<Integer> deleteBasicCategory(@PathVariable(value = "id") Long id) {
        return basicCategoryDataService.deleteRecord(id);
    }

    @Operation(summary = "批量删除设备类型维护信息", description = "根据设备类型维护信息id批量删除设备类型维护信息")
    @Parameter(name = "id", description = "设备类型维护信息id", required = true)
    @PostMapping("/deleteList")
    public ResultBean<Integer> deleteBasicCategoryList(@RequestBody List<Object> ids) {
        return basicCategoryDataService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新设备类型维护信息", description = "根据设备类型维护信息id更新设备类型维护信息")
    @Parameters({
            @Parameter(name = "id", description = "设备类型维护信息id", required = true),
            @Parameter(name = "basicCategory", description = "设备类型维护信息实体", required = true)})
    @PutMapping("/{id}")
    public ResultBean<Integer> updateBasicCategory(@PathVariable(value = "id") Long id, @RequestBody BasicCategoryData basicCategory) {
        Date date = new Date();
        basicCategory.setId(id);
        basicCategory.setModifiedUser(securityUtils.getUserName());
        basicCategory.setModifiedGmt(date);
        try {
            return basicCategoryDataService.updateNotNull(basicCategory);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

}
