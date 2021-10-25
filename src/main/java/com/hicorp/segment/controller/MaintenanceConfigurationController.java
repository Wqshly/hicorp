package com.hicorp.segment.controller;

import com.github.pagehelper.PageInfo;
import com.hicorp.segment.pojo.MaintenanceClassification;
import com.hicorp.segment.pojo.MaintenanceItemConfig;
import com.hicorp.segment.pojo.MaintenanceRecordDetails;
import com.hicorp.segment.pojo.MaintenanceRecordView;
import com.hicorp.segment.security.utils.SecurityUtils;
import com.hicorp.segment.service.MaintenanceClassificationService;
import com.hicorp.segment.service.MaintenanceItemConfigService;
import com.hicorp.segment.service.MaintenanceRecordDetailsService;
import com.hicorp.segment.service.MaintenanceRecordViewService;
import com.hicorp.segment.swagger.ApiVersion;
import com.hicorp.segment.utils.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.hicorp.segment.swagger.ApiVersionConstant.VERSION_1_0_0;
import static com.hicorp.segment.swagger.ApiVersionConstant.VERSION_1_0_1;

@RestController
@Api(tags = "设备维护配置管理")
public class MaintenanceConfigurationController {

    private final MaintenanceClassificationService maintenanceClassificationService;
    private final MaintenanceItemConfigService maintenanceItemConfigService;
    private final MaintenanceRecordViewService maintenanceRecordViewService;
    private final MaintenanceRecordDetailsService maintenanceRecordDetailsService;
    private final SecurityUtils securityUtils;

    public MaintenanceConfigurationController(MaintenanceClassificationService maintenanceClassificationService, MaintenanceItemConfigService maintenanceItemConfigService, MaintenanceRecordViewService maintenanceRecordViewService, MaintenanceRecordDetailsService maintenanceRecordDetailsService, SecurityUtils securityUtils) {
        this.maintenanceClassificationService = maintenanceClassificationService;
        this.maintenanceItemConfigService = maintenanceItemConfigService;
        this.maintenanceRecordViewService = maintenanceRecordViewService;
        this.maintenanceRecordDetailsService = maintenanceRecordDetailsService;
        this.securityUtils = securityUtils;
    }

    // 维护分类管理
    @Operation(summary = "获取维护分类信息", description = "带参数为分页查询巡检点，不带为查询所有")
    @GetMapping("/maintenanceClassification/list")
    public ResultBean<List<MaintenanceClassification>> getMaintenanceClassificationList() {
        return maintenanceClassificationService.queryInfo();
    }

    @Operation(summary = "查询维护分类信息", description = "带参数为分页查询巡检点，不带为查询所有")
    @GetMapping(value = "/maintenanceClassification/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceClassification>> getMaintenanceClassificationList(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("sort") String sort) {
        return maintenanceClassificationService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询维护分类信息", description = "带参数为分页查询维护分类信息，不带为查询所有")
    @GetMapping(value = "/maintenanceClassification/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceClassification>> getMaintenanceClassificationList(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("sort") String sort,
                                                                @PathVariable("field") String field,
                                                                @PathVariable("content") String content) {
        return maintenanceClassificationService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "新增单个维护分类信息", description = "需要是维护分类信息，才能存上")
    @Parameter(name = "maintenanceClassification", description = "维护分类信息实体", required = true)
    @PostMapping("/maintenanceClassification/add")
    public ResultBean<Integer> addMaintenanceClassification(@RequestBody MaintenanceClassification maintenanceClassification) {
        Date date = new Date();
        maintenanceClassification.setCreateUser(securityUtils.getUserName());
        maintenanceClassification.setGmtCreate(date);
        try {
            return maintenanceClassificationService.createRecord(maintenanceClassification);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增维护分类信息", description = "根据维护分类信息实体创建维护分类信息")
    @Parameter(name = "maintenanceClassifications", description = "维护分类实体", required = true)
    @PostMapping("/maintenanceClassification/addList")
    public ResultBean<Integer> addMaintenanceClassificationList(@RequestBody List<MaintenanceClassification> maintenanceClassifications) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        maintenanceClassifications.forEach(maintenanceClassification -> {
            maintenanceClassification.setCreateUser(userName);
            maintenanceClassification.setGmtCreate(date);
        });
        return maintenanceClassificationService.createRecords(maintenanceClassifications);
    }


    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "删除维护分类信息", description = "根据维护分类id删除巡检点")
    @Parameter(name = "id", description = "维护分类id", required = true)
    @DeleteMapping("/maintenanceClassification/{id}")
    public ResultBean<Integer> deleteMaintenanceClassification(@PathVariable(value = "id") Long id) {
        return maintenanceClassificationService.deleteRecord(id);
    }

    @Operation(summary = "批量删除维护分类", description = "根据维护分类id批量删除维护分类")
    @Parameter(name = "id", description = "维护分类id", required = true)
    @PostMapping("/maintenanceClassification/deleteList")
    public ResultBean<Integer> deleteMaintenanceClassificationList(@RequestBody List<Object> ids) {
        return maintenanceClassificationService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新维护分类", description = "根据维护分类id更新维护分类信息")
    @Parameters({
            @Parameter(name = "id", description = "维护分类id", required = true),
            @Parameter(name = "maintenanceClassification", description = "维护分类实体", required = true)})
    @PutMapping("/maintenanceClassification/{id}")
    public ResultBean<Integer> updateMaintenanceClassification(@PathVariable(value = "id") Long id, @RequestBody MaintenanceClassification maintenanceClassification) {
        Date date = new Date();
        maintenanceClassification.setId(id);
        maintenanceClassification.setModifiedUser(securityUtils.getUserName());
        maintenanceClassification.setGmtModified(date);
        try {
            return maintenanceClassificationService.updateNotNull(maintenanceClassification);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

    // 维护项配置管理
    @Operation(summary = "获取维护项配置信息", description = "带参数为分页查询维护项配置，不带为查询所有")
    @GetMapping("/maintenanceItemConfig/list")
    public ResultBean<List<MaintenanceItemConfig>> getMaintenanceItemConfigList() {
        return maintenanceItemConfigService.queryInfo();
    }

    @Operation(summary = "查询维护项配置信息", description = "带参数为分页查询维护项配置，不带为查询所有")
    @GetMapping(value = "/maintenanceItemConfig/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceItemConfig>> getMaintenanceItemConfigList(@RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size,
                                                              @RequestParam("sort") String sort) {
        return maintenanceItemConfigService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询维护项配置信息", description = "带参数为分页查询维护项配置信息，不带为查询所有")
    @GetMapping(value = "/maintenanceItemConfig/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceItemConfig>> getMaintenanceItemConfigList(@RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size,
                                                              @RequestParam("sort") String sort,
                                                              @PathVariable("field") String field,
                                                              @PathVariable("content") String content) {
        return maintenanceItemConfigService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "新增单个维护项配置", description = "需要是维护项配置，才能存上")
    @Parameter(name = "maintenanceItemConfig", description = "维护项配置实体", required = true)
    @PostMapping("/maintenanceItemConfig/add")
    public ResultBean<Integer> addMaintenanceItemConfig(@RequestBody MaintenanceItemConfig maintenanceItemConfig) {
        Date date = new Date();
        maintenanceItemConfig.setCreateUser(securityUtils.getUserName());
        maintenanceItemConfig.setGmtCreate(date);
        try {
            return maintenanceItemConfigService.createRecord(maintenanceItemConfig);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增维护项配置", description = "根据维护项配置实体创建巡检项目")
    @Parameter(name = "maintenanceItemConfigs", description = "维护项配置实体", required = true)
    @PostMapping("/maintenanceItemConfig/addList")
    public ResultBean<Integer> addMaintenanceItemConfigList(@RequestBody List<MaintenanceItemConfig> maintenanceItemConfigs) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        maintenanceItemConfigs.forEach(maintenanceItemConfig -> {
            maintenanceItemConfig.setCreateUser(userName);
            maintenanceItemConfig.setGmtCreate(date);
        });
        return maintenanceItemConfigService.createRecords(maintenanceItemConfigs);
    }


    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "删除维护项配置", description = "根据维护项配置id删除巡检项目")
    @Parameter(name = "id", description = "维护项配置id", required = true)
    @DeleteMapping("/maintenanceItemConfig/{id}")
    public ResultBean<Integer> deleteMaintenanceItemConfig(@PathVariable(value = "id") Long id) {
        return maintenanceItemConfigService.deleteRecord(id);
    }

    @Operation(summary = "批量删除维护项配置", description = "根据维护项配置批量删除巡检项目")
    @Parameter(name = "id", description = "维护项配置id", required = true)
    @PostMapping("/maintenanceItemConfig/deleteList")
    public ResultBean<Integer> deleteMaintenanceItemConfigList(@RequestBody List<Object> ids) {
        return maintenanceItemConfigService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新维护项配置", description = "根据维护项配置id更新维护项配置信息")
    @Parameters({
            @Parameter(name = "id", description = "维护项配置id", required = true),
            @Parameter(name = "maintenanceItemConfig", description = "维护项配置实体", required = true)})
    @PutMapping("/maintenanceItemConfig/{id}")
    public ResultBean<Integer> updateMaintenanceItemConfig(@PathVariable(value = "id") Long id, @RequestBody MaintenanceItemConfig maintenanceItemConfig) {
        Date date = new Date();
        maintenanceItemConfig.setId(id);
        maintenanceItemConfig.setModifiedUser(securityUtils.getUserName());
        maintenanceItemConfig.setGmtModified(date);
        try {
            return maintenanceItemConfigService.updateNotNull(maintenanceItemConfig);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

    // 维护记录查看
    @Operation(summary = "获取维护记录查看信息", description = "带参数为分页查询维护记录查看，不带为查询所有")
    @GetMapping("/maintenanceRecordView/list")
    public ResultBean<List<MaintenanceRecordView>> getMaintenanceRecordViewList() {
        return maintenanceRecordViewService.queryInfo();
    }

    @Operation(summary = "查询维护记录查看信息", description = "带参数为分页查询维护记录查看，不带为查询所有")
    @GetMapping(value = "/maintenanceRecordView/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceRecordView>> getMaintenanceRecordViewList(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("sort") String sort) {
        return maintenanceRecordViewService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询维护记录查看信息", description = "带参数为分页查询维护记录查看信息，不带为查询所有")
    @GetMapping(value = "/maintenanceRecordView/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceRecordView>> getMaintenanceRecordViewList(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("sort") String sort,
                                                                @PathVariable("field") String field,
                                                                @PathVariable("content") String content) {
        return maintenanceRecordViewService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "新增单个维护记录查看", description = "需要是维护记录查看，才能存上")
    @Parameter(name = "maintenanceRecordView", description = "维护记录查看实体", required = true)
    @PostMapping("/maintenanceRecordView/add")
    public ResultBean<Integer> addMaintenanceRecordView(@RequestBody MaintenanceRecordView maintenanceRecordView) {
        Date date = new Date();
        maintenanceRecordView.setCreateUser(securityUtils.getUserName());
        maintenanceRecordView.setGmtCreate(date);
        try {
            return maintenanceRecordViewService.createRecord(maintenanceRecordView);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增维护记录查看", description = "根据维护记录查看实体创建维护记录查看")
    @Parameter(name = "maintenanceRecordViews", description = "维护记录查看实体", required = true)
    @PostMapping("/maintenanceRecordView/addList")
    public ResultBean<Integer> addMaintenanceRecordViewList(@RequestBody List<MaintenanceRecordView> maintenanceRecordViews) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        maintenanceRecordViews.forEach(maintenanceRecordView -> {
            maintenanceRecordView.setCreateUser(userName);
            maintenanceRecordView.setGmtCreate(date);
        });
        return maintenanceRecordViewService.createRecords(maintenanceRecordViews);
    }


    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "删除维护记录查看", description = "根据维护记录查看id删除维护记录查看")
    @Parameter(name = "id", description = "维护记录查看id", required = true)
    @DeleteMapping("/maintenanceRecordView/{id}")
    public ResultBean<Integer> deleteMaintenanceRecordView(@PathVariable(value = "id") Long id) {
        return maintenanceRecordViewService.deleteRecord(id);
    }

    @Operation(summary = "批量删除维护记录查看", description = "根据维护记录查看id批量删除维护记录查看")
    @Parameter(name = "id", description = "维护记录查看id", required = true)
    @PostMapping("/maintenanceRecordView/deleteList")
    public ResultBean<Integer> deleteMaintenanceRecordViewList(@RequestBody List<Object> ids) {
        return maintenanceRecordViewService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新维护记录查看", description = "根据维护记录查看id更新维护记录查看")
    @Parameters({
            @Parameter(name = "id", description = "维护记录查看id", required = true),
            @Parameter(name = "maintenanceRecordView", description = "维护记录查看实体", required = true)})
    @PutMapping("/maintenanceRecordView/{id}")
    public ResultBean<Integer> updateMaintenanceRecordView(@PathVariable(value = "id") Long id, @RequestBody MaintenanceRecordView maintenanceRecordView) {
        Date date = new Date();
        maintenanceRecordView.setId(id);
        maintenanceRecordView.setModifiedUser(securityUtils.getUserName());
        maintenanceRecordView.setGmtModified(date);
        try {
            return maintenanceRecordViewService.updateNotNull(maintenanceRecordView);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

    // 维护记录详情
    @Operation(summary = "获取维护记录详情信息", description = "带参数为分页查询维护记录详情，不带为查询所有")
    @GetMapping("/maintenanceRecordDetails/list")
    public ResultBean<List<MaintenanceRecordDetails>> getMaintenanceRecordDetailsList() {
        return maintenanceRecordDetailsService.queryInfo();
    }

    @Operation(summary = "查询维护记录详情信息", description = "带参数为分页查询维护记录详情，不带为查询所有")
    @GetMapping(value = "/maintenanceRecordDetails/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceRecordDetails>> getMaintenanceRecordDetailsList(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("sort") String sort) {
        return maintenanceRecordDetailsService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询维护记录详情信息", description = "带参数为分页查询维护记录详情信息，不带为查询所有")
    @GetMapping(value = "/maintenanceRecordDetails/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceRecordDetails>> getMaintenanceRecordDetailsList(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("sort") String sort,
                                                                @PathVariable("field") String field,
                                                                @PathVariable("content") String content) {
        return maintenanceRecordDetailsService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "新增单个维护记录详情", description = "需要是维护记录详情，才能存上")
    @Parameter(name = "maintenanceRecordDetails", description = "维护记录详情实体", required = true)
    @PostMapping("/maintenanceRecordDetails/add")
    public ResultBean<Integer> addMaintenanceRecordDetails(@RequestBody MaintenanceRecordDetails maintenanceRecordDetails) {
        Date date = new Date();
        maintenanceRecordDetails.setCreateUser(securityUtils.getUserName());
        maintenanceRecordDetails.setGmtCreate(date);
        try {
            return maintenanceRecordDetailsService.createRecord(maintenanceRecordDetails);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增维护记录详情", description = "根据维护记录详情实体创建维护记录详情")
    @Parameter(name = "maintenanceRecordDetailss", description = "维护记录详情实体", required = true)
    @PostMapping("/maintenanceRecordDetails/addList")
    public ResultBean<Integer> addMaintenanceRecordDetailsList(@RequestBody List<MaintenanceRecordDetails> maintenanceRecordDetailss) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        maintenanceRecordDetailss.forEach(maintenanceRecordDetails -> {
            maintenanceRecordDetails.setCreateUser(userName);
            maintenanceRecordDetails.setGmtCreate(date);
        });
        return maintenanceRecordDetailsService.createRecords(maintenanceRecordDetailss);
    }


    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "删除维护记录详情", description = "根据维护记录详情id删除维护记录详情")
    @Parameter(name = "id", description = "维护记录详情id", required = true)
    @DeleteMapping("/maintenanceRecordDetails/{id}")
    public ResultBean<Integer> deleteMaintenanceRecordDetails(@PathVariable(value = "id") Long id) {
        return maintenanceRecordDetailsService.deleteRecord(id);
    }

    @Operation(summary = "批量删除维护记录详情", description = "根据维护记录详情id批量删除维护记录详情")
    @Parameter(name = "id", description = "维护记录详情id", required = true)
    @PostMapping("/maintenanceRecordDetails/deleteList")
    public ResultBean<Integer> deleteMaintenanceRecordDetailsList(@RequestBody List<Object> ids) {
        return maintenanceRecordDetailsService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新维护记录详情", description = "根据维护记录详情id更新维护记录详情信息")
    @Parameters({
            @Parameter(name = "id", description = "维护记录详情id", required = true),
            @Parameter(name = "maintenanceRecordDetails", description = "维护记录详情实体", required = true)})
    @PutMapping("/maintenanceRecordDetails/{id}")
    public ResultBean<Integer> updateMaintenanceRecordDetails(@PathVariable(value = "id") Long id, @RequestBody MaintenanceRecordDetails maintenanceRecordDetails) {
        Date date = new Date();
        maintenanceRecordDetails.setId(id);
        maintenanceRecordDetails.setModifiedUser(securityUtils.getUserName());
        maintenanceRecordDetails.setGmtModified(date);
        try {
            return maintenanceRecordDetailsService.updateNotNull(maintenanceRecordDetails);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

}
