package com.hicorp.segment.controller;

import com.github.pagehelper.PageInfo;
import com.hicorp.segment.pojo.*;
import com.hicorp.segment.security.utils.SecurityUtils;
import com.hicorp.segment.service.*;
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
@Api(tags = "设备维护管理")
public class MaintenanceController {
    private final MaintenanceDailyMonitoringSystemRoomService maintenanceDailyMonitoringSystemRoomService;
    private final MaintenanceEquipmentReplacementService maintenanceEquipmentReplacementService;
    private final MaintenanceMajorHardwareService maintenanceMajorHardwareService;
    private final MaintenanceMonthlyMonitoringSystemService maintenanceMonthlyMonitoringSystemService;
    private final MaintenanceQuarterlyMonitoringSystemService maintenanceQuarterlyMonitoringSystemService;
    private final SecurityUtils securityUtils;

    public MaintenanceController(MaintenanceDailyMonitoringSystemRoomService maintenanceDailyMonitoringSystemRoomService, MaintenanceEquipmentReplacementService maintenanceEquipmentReplacementService, MaintenanceMajorHardwareService maintenanceMajorHardwareService, MaintenanceMonthlyMonitoringSystemService maintenanceMonthlyMonitoringSystemService, MaintenanceQuarterlyMonitoringSystemService maintenanceQuarterlyMonitoringSystemService, SecurityUtils securityUtils) {
        this.maintenanceDailyMonitoringSystemRoomService = maintenanceDailyMonitoringSystemRoomService;
        this.maintenanceEquipmentReplacementService = maintenanceEquipmentReplacementService;
        this.maintenanceMajorHardwareService = maintenanceMajorHardwareService;
        this.maintenanceMonthlyMonitoringSystemService = maintenanceMonthlyMonitoringSystemService;
        this.maintenanceQuarterlyMonitoringSystemService = maintenanceQuarterlyMonitoringSystemService;
        this.securityUtils = securityUtils;
    }

    // 条件查询，五种维护信息中未审批的个数
    @Operation(summary = "获取监五种维护信息中未审批的个数", description = "五种维护信息中未审批的个数")
    @GetMapping("/maintenance/notApproveMaintenanceNumber/number/{status}")
    public ResultBean<List<Integer>> getNotApproveMaintenanceNumber(@PathVariable("status") String status) {
        return maintenanceDailyMonitoringSystemRoomService.getNotApproveMaintenanceNumber(status);
    }
    // 条件查询，今日五种维护信息的个数
    @Operation(summary = "获取今日五种维护信息的个数", description = "今日五种维护信息的个数")
    @GetMapping("/maintenance/getTodayMaintenanceNumber/number")
    public ResultBean<List<Integer>> getTodayMaintenanceNumber() {
        Date date = new Date();
        return maintenanceDailyMonitoringSystemRoomService.getTodayMaintenanceNumber(date);
    }

    // 监控系统机房日常维护
    @Operation(summary = "获取监控系统机房日常维护信息", description = "带参数为分页查询监控系统机房日常维护信息，不带为查询所有")
    @GetMapping("/maintenanceDailyMonitoringSystemRoom/list")
    public ResultBean<List<MaintenanceDailyMonitoringSystemRoom>> getMaintenanceDailyMonitoringSystemRoomList() {
        return maintenanceDailyMonitoringSystemRoomService.queryInfo();
    }

    @Operation(summary = "查询监控系统机房日常维护信息", description = "带参数为分页查询监控系统机房日常维护信息，不带为查询所有")
    @GetMapping(value = "/maintenanceDailyMonitoringSystemRoom/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceDailyMonitoringSystemRoom>> getMaintenanceDailyMonitoringSystemRoomList(@RequestParam("page") Integer page,
                                                                                                                  @RequestParam("size") Integer size,
                                                                                                                  @RequestParam("sort") String sort) {
        return maintenanceDailyMonitoringSystemRoomService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询监控系统机房日常维护信息", description = "带参数为分页查询监控系统机房日常维护信息，不带为查询所有")
    @GetMapping(value = "/maintenanceDailyMonitoringSystemRoom/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceDailyMonitoringSystemRoom>> getMaintenanceDailyMonitoringSystemRoomList(@RequestParam("page") Integer page,
                                                                                                                  @RequestParam("size") Integer size,
                                                                                                                  @RequestParam("sort") String sort,
                                                                                                                  @PathVariable("field") String field,
                                                                                                                  @PathVariable("content") String content) {
        return maintenanceDailyMonitoringSystemRoomService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "新增单个监控系统机房日常维护信息", description = "需要是监控系统机房日常维护信息，才能存上")
    @Parameter(name = "maintenanceDailyMonitoringSystemRoom", description = "监控系统机房日常维护实体", required = true)
    @PostMapping("/maintenanceDailyMonitoringSystemRoom/add")
    public ResultBean<Integer> addMaintenanceDailyMonitoringSystemRoom(@RequestBody MaintenanceDailyMonitoringSystemRoom maintenanceDailyMonitoringSystemRoom) {
        Date date = new Date();
        maintenanceDailyMonitoringSystemRoom.setCreateUser(securityUtils.getUserName());
        maintenanceDailyMonitoringSystemRoom.setGmtCreate(date);
        try {
            return maintenanceDailyMonitoringSystemRoomService.createRecord(maintenanceDailyMonitoringSystemRoom);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增监控系统机房日常维护信息", description = "根据监控系统机房日常维护实体创建监控系统机房日常维护信息")
    @Parameter(name = "maintenanceDailyMonitoringSystemRooms", description = "监控系统机房日常维护实体", required = true)
    @PostMapping("/maintenanceDailyMonitoringSystemRoom/addList")
    public ResultBean<Integer> addMaintenanceDailyMonitoringSystemRoomList(@RequestBody List<MaintenanceDailyMonitoringSystemRoom> maintenanceDailyMonitoringSystemRooms) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        maintenanceDailyMonitoringSystemRooms.forEach(maintenanceDailyMonitoringSystemRoom -> {
            maintenanceDailyMonitoringSystemRoom.setCreateUser(userName);
            maintenanceDailyMonitoringSystemRoom.setGmtCreate(date);
        });
        return maintenanceDailyMonitoringSystemRoomService.createRecords(maintenanceDailyMonitoringSystemRooms);
    }


    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "删除监控系统机房日常维护信息", description = "根据监控系统机房日常维护id删除监控系统机房日常维护信息")
    @Parameter(name = "id", description = "监控系统机房日常维护id", required = true)
    @DeleteMapping("/maintenanceDailyMonitoringSystemRoom/{id}")
    public ResultBean<Integer> deleteMaintenanceDailyMonitoringSystemRoom(@PathVariable(value = "id") Long id) {
        return maintenanceDailyMonitoringSystemRoomService.deleteRecord(id);
    }

    @Operation(summary = "批量删除监控系统机房日常维护信息", description = "根据监控系统机房日常维护id批量删除监控系统机房日常维护信息")
    @Parameter(name = "id", description = "监控系统机房日常维护id", required = true)
    @PostMapping("/maintenanceDailyMonitoringSystemRoom/deleteList")
    public ResultBean<Integer> deleteMaintenanceDailyMonitoringSystemRoomList(@RequestBody List<Object> ids) {
        return maintenanceDailyMonitoringSystemRoomService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新监控系统机房日常维护信息", description = "根据监控系统机房日常维护id更新监控系统机房日常维护信息")
    @Parameters({
            @Parameter(name = "id", description = "监控系统机房日常维护id", required = true),
            @Parameter(name = "maintenanceDailyMonitoringSystemRoom", description = "监控系统机房日常维护实体", required = true)})
    @PutMapping("/maintenanceDailyMonitoringSystemRoom/{id}")
    public ResultBean<Integer> updateMaintenanceDailyMonitoringSystemRoom(@PathVariable(value = "id") Long id, @RequestBody MaintenanceDailyMonitoringSystemRoom maintenanceDailyMonitoringSystemRoom) {
        Date date = new Date();
        maintenanceDailyMonitoringSystemRoom.setId(id);
        maintenanceDailyMonitoringSystemRoom.setModifiedUser(securityUtils.getUserName());
        maintenanceDailyMonitoringSystemRoom.setGmtModified(date);
        try {
            return maintenanceDailyMonitoringSystemRoomService.updateNotNull(maintenanceDailyMonitoringSystemRoom);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

    // 设备更换
    @Operation(summary = "获取设备更换信息", description = "带参数为分页查询设备更换信息，不带为查询所有")
    @GetMapping("/maintenanceEquipmentReplacement/list")
    public ResultBean<List<MaintenanceEquipmentReplacement>> getMaintenanceEquipmentReplacementList() {
        return maintenanceEquipmentReplacementService.queryInfo();
    }

    @Operation(summary = "查询设备更换信息", description = "带参数为分页查询设备更换信息，不带为查询所有")
    @GetMapping(value = "/maintenanceEquipmentReplacement/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceEquipmentReplacement>> getMaintenanceEquipmentReplacementList(@RequestParam("page") Integer page,
                                                                                                        @RequestParam("size") Integer size,
                                                                                                        @RequestParam("sort") String sort) {
        return maintenanceEquipmentReplacementService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询设备更换信息", description = "带参数为分页查询设备更换信息，不带为查询所有")
    @GetMapping(value = "/maintenanceEquipmentReplacement/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceEquipmentReplacement>> getMaintenanceEquipmentReplacementList(@RequestParam("page") Integer page,
                                                                                                        @RequestParam("size") Integer size,
                                                                                                        @RequestParam("sort") String sort,
                                                                                                        @PathVariable("field") String field,
                                                                                                        @PathVariable("content") String content) {
        return maintenanceEquipmentReplacementService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "新增单个设备更换信息", description = "需要是设备更换信息，才能存上")
    @Parameter(name = "maintenanceEquipmentReplacement", description = "设备更换实体", required = true)
    @PostMapping("/maintenanceEquipmentReplacement/add")
    public ResultBean<Integer> addMaintenanceEquipmentReplacement(@RequestBody MaintenanceEquipmentReplacement maintenanceEquipmentReplacement) {
        Date date = new Date();
        maintenanceEquipmentReplacement.setCreateUser(securityUtils.getUserName());
        maintenanceEquipmentReplacement.setGmtCreate(date);
        try {
            return maintenanceEquipmentReplacementService.createRecord(maintenanceEquipmentReplacement);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增设备更换信息", description = "根据设备更换实体创建设备更换信息")
    @Parameter(name = "maintenanceEquipmentReplacements", description = "设备更换实体", required = true)
    @PostMapping("/maintenanceEquipmentReplacement/addList")
    public ResultBean<Integer> addMaintenanceEquipmentReplacementList(@RequestBody List<MaintenanceEquipmentReplacement> maintenanceEquipmentReplacements) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        maintenanceEquipmentReplacements.forEach(maintenanceEquipmentReplacement -> {
            maintenanceEquipmentReplacement.setCreateUser(userName);
            maintenanceEquipmentReplacement.setGmtCreate(date);
        });
        return maintenanceEquipmentReplacementService.createRecords(maintenanceEquipmentReplacements);
    }


    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "删除设备更换信息", description = "根据设备更换id删除设备更换信息")
    @Parameter(name = "id", description = "设备更换id", required = true)
    @DeleteMapping("/maintenanceEquipmentReplacement/{id}")
    public ResultBean<Integer> deleteMaintenanceEquipmentReplacement(@PathVariable(value = "id") Long id) {
        return maintenanceEquipmentReplacementService.deleteRecord(id);
    }

    @Operation(summary = "批量删除设备更换信息", description = "根据设备更换id批量删除设备更换信息")
    @Parameter(name = "id", description = "设备更换id", required = true)
    @PostMapping("/maintenanceEquipmentReplacement/deleteList")
    public ResultBean<Integer> deleteMaintenanceEquipmentReplacementList(@RequestBody List<Object> ids) {
        return maintenanceEquipmentReplacementService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新设备更换信息", description = "根据设备更换id更新设备更换信息")
    @Parameters({
            @Parameter(name = "id", description = "设备更换id", required = true),
            @Parameter(name = "maintenanceEquipmentReplacement", description = "设备更换实体", required = true)})
    @PutMapping("/maintenanceEquipmentReplacement/{id}")
    public ResultBean<Integer> updateMaintenanceEquipmentReplacement(@PathVariable(value = "id") Long id, @RequestBody MaintenanceEquipmentReplacement maintenanceEquipmentReplacement) {
        Date date = new Date();
        maintenanceEquipmentReplacement.setId(id);
        maintenanceEquipmentReplacement.setModifiedUser(securityUtils.getUserName());
        maintenanceEquipmentReplacement.setGmtModified(date);
        try {
            return maintenanceEquipmentReplacementService.updateNotNull(maintenanceEquipmentReplacement);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

    // 重大硬件维护
    @Operation(summary = "获取重大硬件维护信息", description = "带参数为分页查询重大硬件维护信息，不带为查询所有")
    @GetMapping("/maintenanceMajorHardware/list")
    public ResultBean<List<MaintenanceMajorHardware>> getMaintenanceMajorHardwareList() {
        return maintenanceMajorHardwareService.queryInfo();
    }

    @Operation(summary = "查询重大硬件维护信息", description = "带参数为分页查询重大硬件维护信息，不带为查询所有")
    @GetMapping(value = "/maintenanceMajorHardware/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceMajorHardware>> getMaintenanceMajorHardwareList(@RequestParam("page") Integer page,
                                                                                          @RequestParam("size") Integer size,
                                                                                          @RequestParam("sort") String sort) {
        return maintenanceMajorHardwareService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询重大硬件维护信息", description = "带参数为分页查询重大硬件维护信息，不带为查询所有")
    @GetMapping(value = "/maintenanceMajorHardware/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceMajorHardware>> getMaintenanceMajorHardwareList(@RequestParam("page") Integer page,
                                                                                          @RequestParam("size") Integer size,
                                                                                          @RequestParam("sort") String sort,
                                                                                          @PathVariable("field") String field,
                                                                                          @PathVariable("content") String content) {
        return maintenanceMajorHardwareService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "新增单个重大硬件维护信息", description = "需要是重大硬件维护信息，才能存上")
    @Parameter(name = "maintenanceMajorHardware", description = "重大硬件维护实体", required = true)
    @PostMapping("/maintenanceMajorHardware/add")
    public ResultBean<Integer> addMaintenanceMajorHardware(@RequestBody MaintenanceMajorHardware maintenanceMajorHardware) {
        Date date = new Date();
        maintenanceMajorHardware.setCreateUser(securityUtils.getUserName());
        maintenanceMajorHardware.setGmtCreate(date);
        try {
            return maintenanceMajorHardwareService.createRecord(maintenanceMajorHardware);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增重大硬件维护信息", description = "根据重大硬件维护实体创建重大硬件维护信息")
    @Parameter(name = "maintenanceMajorHardwares", description = "重大硬件维护实体", required = true)
    @PostMapping("/maintenanceMajorHardware/addList")
    public ResultBean<Integer> addMaintenanceMajorHardwareList(@RequestBody List<MaintenanceMajorHardware> maintenanceMajorHardwares) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        maintenanceMajorHardwares.forEach(maintenanceMajorHardware -> {
            maintenanceMajorHardware.setCreateUser(userName);
            maintenanceMajorHardware.setGmtCreate(date);
        });
        return maintenanceMajorHardwareService.createRecords(maintenanceMajorHardwares);
    }


    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "删除重大硬件维护信息", description = "根据重大硬件维护id删除重大硬件维护信息")
    @Parameter(name = "id", description = "重大硬件维护id", required = true)
    @DeleteMapping("/maintenanceMajorHardware/{id}")
    public ResultBean<Integer> deleteMaintenanceMajorHardware(@PathVariable(value = "id") Long id) {
        return maintenanceMajorHardwareService.deleteRecord(id);
    }

    @Operation(summary = "批量删除重大硬件维护信息", description = "根据重大硬件维护id批量删除重大硬件维护信息")
    @Parameter(name = "id", description = "重大硬件维护id", required = true)
    @PostMapping("/maintenanceMajorHardware/deleteList")
    public ResultBean<Integer> deleteMaintenanceMajorHardwareList(@RequestBody List<Object> ids) {
        return maintenanceMajorHardwareService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新重大硬件维护信息", description = "根据重大硬件维护id更新重大硬件维护信息")
    @Parameters({
            @Parameter(name = "id", description = "重大硬件维护id", required = true),
            @Parameter(name = "maintenanceMajorHardware", description = "重大硬件维护实体", required = true)})
    @PutMapping("/maintenanceMajorHardware/{id}")
    public ResultBean<Integer> updateMaintenanceMajorHardware(@PathVariable(value = "id") Long id, @RequestBody MaintenanceMajorHardware maintenanceMajorHardware) {
        Date date = new Date();
        maintenanceMajorHardware.setId(id);
        maintenanceMajorHardware.setModifiedUser(securityUtils.getUserName());
        maintenanceMajorHardware.setGmtModified(date);
        try {
            return maintenanceMajorHardwareService.updateNotNull(maintenanceMajorHardware);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

    // 月度监控系统维护
    // 条件查询，本月月度和季度监控系统维护信息中的个数
    @Operation(summary = "获取本月月度和季度监控系统维护信息中的个数", description = "本月月度和季度监控系统维护信息中的个数")
    @GetMapping("/maintenance/getIsHaveMonthlyAndQuarterlyMonitoringSystem")
    public ResultBean<List<Integer>> getIsHaveMonthlyAndQuarterlyMonitoringSystem() {
        return maintenanceMonthlyMonitoringSystemService.getIsHaveMonthlyAndQuarterlyMonitoringSystem();
    }
    @Operation(summary = "获取月度监控系统维护信息", description = "带参数为分页查询月度监控系统维护信息，不带为查询所有")
    @GetMapping("/maintenanceMonthlyMonitoringSystem/list")
    public ResultBean<List<MaintenanceMonthlyMonitoringSystem>> getMaintenanceMonthlyMonitoringSystemList() {
        return maintenanceMonthlyMonitoringSystemService.queryInfo();
    }

    @Operation(summary = "查询月度监控系统维护信息", description = "带参数为分页查询月度监控系统维护信息，不带为查询所有")
    @GetMapping(value = "/maintenanceMonthlyMonitoringSystem/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceMonthlyMonitoringSystem>> getMaintenanceMonthlyMonitoringSystemList(@RequestParam("page") Integer page,
                                                                                                              @RequestParam("size") Integer size,
                                                                                                              @RequestParam("sort") String sort) {
        return maintenanceMonthlyMonitoringSystemService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询月度监控系统维护信息", description = "带参数为分页查询月度监控系统维护信息，不带为查询所有")
    @GetMapping(value = "/maintenanceMonthlyMonitoringSystem/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceMonthlyMonitoringSystem>> getMaintenanceMonthlyMonitoringSystemList(@RequestParam("page") Integer page,
                                                                                                              @RequestParam("size") Integer size,
                                                                                                              @RequestParam("sort") String sort,
                                                                                                              @PathVariable("field") String field,
                                                                                                              @PathVariable("content") String content) {
        return maintenanceMonthlyMonitoringSystemService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "新增单个月度监控系统维护信息", description = "需要是月度监控系统维护信息，才能存上")
    @Parameter(name = "maintenanceMonthlyMonitoringSystem", description = "月度监控系统维护实体", required = true)
    @PostMapping("/maintenanceMonthlyMonitoringSystem/add")
    public ResultBean<Integer> addMaintenanceMonthlyMonitoringSystem(@RequestBody MaintenanceMonthlyMonitoringSystem maintenanceMonthlyMonitoringSystem) {
        Date date = new Date();
        maintenanceMonthlyMonitoringSystem.setCreateUser(securityUtils.getUserName());
        maintenanceMonthlyMonitoringSystem.setGmtCreate(date);
        try {
            return maintenanceMonthlyMonitoringSystemService.createRecord(maintenanceMonthlyMonitoringSystem);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增月度监控系统维护信息", description = "根据月度监控系统维护实体创建月度监控系统维护信息")
    @Parameter(name = "maintenanceMonthlyMonitoringSystems", description = "月度监控系统维护实体", required = true)
    @PostMapping("/maintenanceMonthlyMonitoringSystem/addList")
    public ResultBean<Integer> addMaintenanceMonthlyMonitoringSystemList(@RequestBody List<MaintenanceMonthlyMonitoringSystem> maintenanceMonthlyMonitoringSystems) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        maintenanceMonthlyMonitoringSystems.forEach(maintenanceMonthlyMonitoringSystem -> {
            maintenanceMonthlyMonitoringSystem.setCreateUser(userName);
            maintenanceMonthlyMonitoringSystem.setGmtCreate(date);
        });
        return maintenanceMonthlyMonitoringSystemService.createRecords(maintenanceMonthlyMonitoringSystems);
    }


    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "删除月度监控系统维护信息", description = "根据月度监控系统维护id删除月度监控系统维护信息")
    @Parameter(name = "id", description = "月度监控系统维护id", required = true)
    @DeleteMapping("/maintenanceMonthlyMonitoringSystem/{id}")
    public ResultBean<Integer> deleteMaintenanceMonthlyMonitoringSystem(@PathVariable(value = "id") Long id) {
        return maintenanceMonthlyMonitoringSystemService.deleteRecord(id);
    }

    @Operation(summary = "批量删除月度监控系统维护信息", description = "根据月度监控系统维护id批量删除月度监控系统维护信息")
    @Parameter(name = "id", description = "月度监控系统维护id", required = true)
    @PostMapping("/maintenanceMonthlyMonitoringSystem/deleteList")
    public ResultBean<Integer> deleteMaintenanceMonthlyMonitoringSystemList(@RequestBody List<Object> ids) {
        return maintenanceMonthlyMonitoringSystemService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新月度监控系统维护信息", description = "根据月度监控系统维护id更新月度监控系统维护信息")
    @Parameters({
            @Parameter(name = "id", description = "月度监控系统维护id", required = true),
            @Parameter(name = "maintenanceMonthlyMonitoringSystem", description = "月度监控系统维护实体", required = true)})
    @PutMapping("/maintenanceMonthlyMonitoringSystem/{id}")
    public ResultBean<Integer> updateMaintenanceMonthlyMonitoringSystem(@PathVariable(value = "id") Long id, @RequestBody MaintenanceMonthlyMonitoringSystem maintenanceMonthlyMonitoringSystem) {
        Date date = new Date();
        maintenanceMonthlyMonitoringSystem.setId(id);
        maintenanceMonthlyMonitoringSystem.setModifiedUser(securityUtils.getUserName());
        maintenanceMonthlyMonitoringSystem.setGmtModified(date);
        try {
            return maintenanceMonthlyMonitoringSystemService.updateNotNull(maintenanceMonthlyMonitoringSystem);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

    // 季度监控系统维护
    @Operation(summary = "获取季度监控系统维护信息", description = "带参数为分页查询季度监控系统维护信息，不带为查询所有")
    @GetMapping("/maintenanceQuarterlyMonitoringSystem/list")
    public ResultBean<List<MaintenanceQuarterlyMonitoringSystem>> getMaintenanceQuarterlyMonitoringSystemList() {
        return maintenanceQuarterlyMonitoringSystemService.queryInfo();
    }

    @Operation(summary = "查询季度监控系统维护信息", description = "带参数为分页查询季度监控系统维护信息，不带为查询所有")
    @GetMapping(value = "/maintenanceQuarterlyMonitoringSystem/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceQuarterlyMonitoringSystem>> getMaintenanceQuarterlyMonitoringSystemList(@RequestParam("page") Integer page,
                                                                                                                  @RequestParam("size") Integer size,
                                                                                                                  @RequestParam("sort") String sort) {
        return maintenanceQuarterlyMonitoringSystemService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询季度监控系统维护信息", description = "带参数为分页查询季度监控系统维护信息，不带为查询所有")
    @GetMapping(value = "/maintenanceQuarterlyMonitoringSystem/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<MaintenanceQuarterlyMonitoringSystem>> getMaintenanceQuarterlyMonitoringSystemList(@RequestParam("page") Integer page,
                                                                                                                  @RequestParam("size") Integer size,
                                                                                                                  @RequestParam("sort") String sort,
                                                                                                                  @PathVariable("field") String field,
                                                                                                                  @PathVariable("content") String content) {
        return maintenanceQuarterlyMonitoringSystemService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "新增单个季度监控系统维护信息", description = "需要是季度监控系统维护信息，才能存上")
    @Parameter(name = "maintenanceQuarterlyMonitoringSystem", description = "季度监控系统维护实体", required = true)
    @PostMapping("/maintenanceQuarterlyMonitoringSystem/add")
    public ResultBean<Integer> addMaintenanceQuarterlyMonitoringSystem(@RequestBody MaintenanceQuarterlyMonitoringSystem maintenanceQuarterlyMonitoringSystem) {
        Date date = new Date();
        maintenanceQuarterlyMonitoringSystem.setCreateUser(securityUtils.getUserName());
        maintenanceQuarterlyMonitoringSystem.setGmtCreate(date);
        try {
            return maintenanceQuarterlyMonitoringSystemService.createRecord(maintenanceQuarterlyMonitoringSystem);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增季度监控系统维护信息", description = "根据季度监控系统维护实体创建季度监控系统维护信息")
    @Parameter(name = "maintenanceQuarterlyMonitoringSystems", description = "季度监控系统维护实体", required = true)
    @PostMapping("/maintenanceQuarterlyMonitoringSystem/addList")
    public ResultBean<Integer> addMaintenanceQuarterlyMonitoringSystemList(@RequestBody List<MaintenanceQuarterlyMonitoringSystem> maintenanceQuarterlyMonitoringSystems) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        maintenanceQuarterlyMonitoringSystems.forEach(maintenanceQuarterlyMonitoringSystem -> {
            maintenanceQuarterlyMonitoringSystem.setCreateUser(userName);
            maintenanceQuarterlyMonitoringSystem.setGmtCreate(date);
        });
        return maintenanceQuarterlyMonitoringSystemService.createRecords(maintenanceQuarterlyMonitoringSystems);
    }


    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "删除季度监控系统维护信息", description = "根据季度监控系统维护id删除季度监控系统维护信息")
    @Parameter(name = "id", description = "季度监控系统维护id", required = true)
    @DeleteMapping("/maintenanceQuarterlyMonitoringSystem/{id}")
    public ResultBean<Integer> deleteMaintenanceQuarterlyMonitoringSystem(@PathVariable(value = "id") Long id) {
        return maintenanceQuarterlyMonitoringSystemService.deleteRecord(id);
    }

    @Operation(summary = "批量删除季度监控系统维护信息", description = "根据季度监控系统维护id批量删除季度监控系统维护信息")
    @Parameter(name = "id", description = "季度监控系统维护id", required = true)
    @PostMapping("/maintenanceQuarterlyMonitoringSystem/deleteList")
    public ResultBean<Integer> deleteMaintenanceQuarterlyMonitoringSystemList(@RequestBody List<Object> ids) {
        return maintenanceQuarterlyMonitoringSystemService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新季度监控系统维护信息", description = "根据季度监控系统维护id更新季度监控系统维护信息")
    @Parameters({
            @Parameter(name = "id", description = "季度监控系统维护id", required = true),
            @Parameter(name = "maintenanceQuarterlyMonitoringSystem", description = "季度监控系统维护实体", required = true)})
    @PutMapping("/maintenanceQuarterlyMonitoringSystem/{id}")
    public ResultBean<Integer> updateMaintenanceQuarterlyMonitoringSystem(@PathVariable(value = "id") Long id, @RequestBody MaintenanceQuarterlyMonitoringSystem maintenanceQuarterlyMonitoringSystem) {
        Date date = new Date();
        maintenanceQuarterlyMonitoringSystem.setId(id);
        maintenanceQuarterlyMonitoringSystem.setModifiedUser(securityUtils.getUserName());
        maintenanceQuarterlyMonitoringSystem.setGmtModified(date);
        try {
            return maintenanceQuarterlyMonitoringSystemService.updateNotNull(maintenanceQuarterlyMonitoringSystem);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }
}
