package com.hicorp.segment.controller;

import com.github.pagehelper.PageInfo;
import com.hicorp.segment.pojo.BomMain;
import com.hicorp.segment.security.utils.SecurityUtils;
import com.hicorp.segment.service.BomMainService;
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
public class BomMainController {
    private final BomMainService bomMainService;
    private final SecurityUtils securityUtils;

    public BomMainController(SecurityUtils securityUtils, BomMainService bomMainService) {
        this.securityUtils = securityUtils;
        this.bomMainService = bomMainService;
    }

    // 设备类型维护
    @Operation(summary = "获取设备类型维护信息", description = "带参数为分页查询设备类型维护，不带为查询所有")
    @GetMapping("/bomMain/list")
    public ResultBean<List<BomMain>> getBomMainList() {
        return bomMainService.queryInfo();
    }


    @Operation(summary = "查询设备类型维护信息", description = "带参数为分页查询设备类型维护信息，不带为查询所有")
    @GetMapping(value = "/bomMain/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<BomMain>> getBomMainList(@RequestParam("page") Integer page,
                                                                      @RequestParam("size") Integer size,
                                                                      @RequestParam("sort") String sort) {
        return bomMainService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询设备类型维护信息", description = "带参数为分页查询设备类型维护信息，不带为查询所有")
    @GetMapping(value = "/bomMain/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<BomMain>> getBomMainList(@RequestParam("page") Integer page,
                                                                      @RequestParam("size") Integer size,
                                                                      @RequestParam("sort") String sort,
                                                                      @PathVariable("field") String field,
                                                                      @PathVariable("content") String content) {
        return bomMainService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {ApiVersionConstant.VERSION_1_0_0, ApiVersionConstant.VERSION_1_0_1})
    @Operation(summary = "新增单个设备类型维护信息", description = "需要是设备类型维护信息，才能存上")
    @Parameter(name = "bomMain", description = "设备类型维护信息实体", required = true)
    @PostMapping("/bomMain/add")
    public ResultBean<Integer> addBomMain(@RequestBody BomMain bomMain) {
        Date date = new Date();
        bomMain.setCreateUser(securityUtils.getUserName());
        bomMain.setCreateGmt(date);
        try {
            return bomMainService.createRecord(bomMain);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增设备类型维护信息", description = "根据设备类型维护信息实体创建设备类型维护信息")
    @Parameter(name = "deviceCategories", description = "设备类型维护信息实体", required = true)
    @PostMapping("/bomMain/addList")
    public ResultBean<Integer> addBomMainList(@RequestBody List<BomMain> deviceCategories) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        deviceCategories.forEach(bomMain -> {
            bomMain.setCreateUser(userName);
            bomMain.setCreateGmt(date);
        });
        return bomMainService.createRecords(deviceCategories);
    }


    @ApiVersion(value = {ApiVersionConstant.VERSION_1_0_0, ApiVersionConstant.VERSION_1_0_1})
    @Operation(summary = "删除设备类型维护信息", description = "根据设备类型维护信息id删除设备类型维护信息")
    @Parameter(name = "id", description = "设备类型维护信息id", required = true)
    @DeleteMapping("/bomMain/{id}")
    public ResultBean<Integer> deleteBomMain(@PathVariable(value = "id") Long id) {
        return bomMainService.deleteRecord(id);
    }

    @Operation(summary = "批量删除设备类型维护信息", description = "根据设备类型维护信息id批量删除设备类型维护信息")
    @Parameter(name = "id", description = "设备类型维护信息id", required = true)
    @PostMapping("/bomMain/deleteList")
    public ResultBean<Integer> deleteBomMainList(@RequestBody List<Object> ids) {
        return bomMainService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新设备类型维护信息", description = "根据设备类型维护信息id更新设备类型维护信息")
    @Parameters({
            @Parameter(name = "id", description = "设备类型维护信息id", required = true),
            @Parameter(name = "bomMain", description = "设备类型维护信息实体", required = true)})
    @PutMapping("/bomMain/{id}")
    public ResultBean<Integer> updateBomMain(@PathVariable(value = "id") Long id, @RequestBody BomMain bomMain) {
        Date date = new Date();
        bomMain.setId(id);
        bomMain.setModifyUser(securityUtils.getUserName());
        bomMain.setModifyGmt(date);
        try {
            return bomMainService.updateNotNull(bomMain);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

}
