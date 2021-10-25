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
@Api(tags = "巡检管理")
public class PatrolController {
    private final PatrolCycleService patrolCycleService;
    private final PatrolRouteService patrolRouteService;
    private final PatrolTaskService patrolTaskService;
    private final PatrolItemService patrolItemService;
    private final SecurityUtils securityUtils;
    private final PatrolPointService patrolPointService;

    public PatrolController(PatrolPointService patrolPointService, SecurityUtils securityUtils, PatrolItemService patrolItemService, PatrolCycleService patrolCycleService, PatrolRouteService patrolRouteService, PatrolTaskService patrolTaskService) {
        this.patrolPointService = patrolPointService;
        this.securityUtils = securityUtils;
        this.patrolItemService = patrolItemService;
        this.patrolCycleService = patrolCycleService;
        this.patrolRouteService = patrolRouteService;
        this.patrolTaskService = patrolTaskService;
    }

    // 巡检点管理
    @Operation(summary = "获取巡检点信息", description = "带参数为分页查询巡检点，不带为查询所有")
    @GetMapping("/patrolPoint/list")
    public ResultBean<List<PatrolPoint>> getPatrolPointList() {
        return patrolPointService.queryInfo();
    }

    @Operation(summary = "查询巡检点信息", description = "带参数为分页查询巡检点，不带为查询所有")
    @GetMapping(value = "/patrolPoint/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<PatrolPoint>> getPatrolPointList(@RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size,
                                                              @RequestParam("sort") String sort) {
        return patrolPointService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询巡检点信息", description = "带参数为分页查询巡检点信息，不带为查询所有")
    @GetMapping(value = "/patrolPoint/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<PatrolPoint>> getPatrolPointList(@RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size,
                                                              @RequestParam("sort") String sort,
                                                              @PathVariable("field") String field,
                                                              @PathVariable("content") String content) {
        return patrolPointService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "新增单个巡检点", description = "需要是巡检点，才能存上")
    @Parameter(name = "patrolPoint", description = "巡检点实体", required = true)
    @PostMapping("/patrolPoint/add")
    public ResultBean<Integer> addPatrolPoint(@RequestBody PatrolPoint patrolPoint) {
        Date date = new Date();
        patrolPoint.setCreateUser(securityUtils.getUserName());
        patrolPoint.setGmtCreate(date);
        try {
            return patrolPointService.createRecord(patrolPoint);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增巡检点", description = "根据巡检点实体创建巡检点")
    @Parameter(name = "patrolPoints", description = "巡检点实体", required = true)
    @PostMapping("/patrolPoint/addList")
    public ResultBean<Integer> addPatrolPointList(@RequestBody List<PatrolPoint> patrolPoints) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        patrolPoints.forEach(patrolPoint -> {
            patrolPoint.setCreateUser(userName);
            patrolPoint.setGmtCreate(date);
        });
        return patrolPointService.createRecords(patrolPoints);
    }


    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "删除巡检点", description = "根据巡检点id删除巡检点")
    @Parameter(name = "id", description = "巡检点id", required = true)
    @DeleteMapping("/patrolPoint/{id}")
    public ResultBean<Integer> deletePatrolPoint(@PathVariable(value = "id") Long id) {
        return patrolPointService.deleteRecord(id);
    }

    @Operation(summary = "批量删除巡检点", description = "根据巡检点id批量删除巡检点")
    @Parameter(name = "id", description = "巡检点id", required = true)
    @PostMapping("/patrolPoint/deleteList")
    public ResultBean<Integer> deletePatrolPointList(@RequestBody List<Object> ids) {
        return patrolPointService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新巡检点", description = "根据巡检点id更新巡检点信息")
    @Parameters({
            @Parameter(name = "id", description = "巡检点id", required = true),
            @Parameter(name = "patrolPoint", description = "巡检点实体", required = true)})
    @PutMapping("/patrolPoint/{id}")
    public ResultBean<Integer> updatePatrolPoint(@PathVariable(value = "id") Long id, @RequestBody PatrolPoint patrolPoint) {
        Date date = new Date();
        patrolPoint.setId(id);
        patrolPoint.setModifiedUser(securityUtils.getUserName());
        patrolPoint.setGmtModified(date);
        try {
            return patrolPointService.updateNotNull(patrolPoint);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }
    // 巡检项目管理
    @Operation(summary = "获取巡检项目信息", description = "带参数为分页查询巡检项目，不带为查询所有")
    @GetMapping("/patrolItem/list")
    public ResultBean<List<PatrolItem>> getPatrolItemList() {
        return patrolItemService.queryInfo();
    }

    @Operation(summary = "查询巡检项目信息", description = "带参数为分页查询巡检项目，不带为查询所有")
    @GetMapping(value = "/patrolItem/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<PatrolItem>> getPatrolItemList(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("sort") String sort) {
        return patrolItemService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询巡检项目信息", description = "带参数为分页查询巡检项目信息，不带为查询所有")
    @GetMapping(value = "/patrolItem/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<PatrolItem>> getPatrolItemList(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("sort") String sort,
                                                                @PathVariable("field") String field,
                                                                @PathVariable("content") String content) {
        return patrolItemService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "新增单个巡检项目", description = "需要是巡检项目，才能存上")
    @Parameter(name = "patrolItem", description = "巡检项目实体", required = true)
    @PostMapping("/patrolItem/add")
    public ResultBean<Integer> addPatrolItem(@RequestBody PatrolItem patrolItem) {
        Date date = new Date();
        patrolItem.setCreateUser(securityUtils.getUserName());
        patrolItem.setGmtCreate(date);
        try {
            return patrolItemService.createRecord(patrolItem);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增巡检项目", description = "根据巡检项目实体创建巡检项目")
    @Parameter(name = "patrolItems", description = "巡检项目实体", required = true)
    @PostMapping("/patrolItem/addList")
    public ResultBean<Integer> addPatrolItemList(@RequestBody List<PatrolItem> patrolItems) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        patrolItems.forEach(patrolItem -> {
            patrolItem.setCreateUser(userName);
            patrolItem.setGmtCreate(date);
        });
        return patrolItemService.createRecords(patrolItems);
    }


    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "删除巡检项目", description = "根据巡检项目id删除巡检项目")
    @Parameter(name = "id", description = "巡检项目id", required = true)
    @DeleteMapping("/patrolItem/{id}")
    public ResultBean<Integer> deletePatrolItem(@PathVariable(value = "id") Long id) {
        return patrolItemService.deleteRecord(id);
    }

    @Operation(summary = "批量删除巡检项目", description = "根据巡检项目批量删除巡检项目")
    @Parameter(name = "id", description = "巡检项目id", required = true)
    @PostMapping("/patrolItem/deleteList")
    public ResultBean<Integer> deletePatrolItemList(@RequestBody List<Object> ids) {
        return patrolItemService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新巡检项目", description = "根据巡检项目id更新巡检项目信息")
    @Parameters({
            @Parameter(name = "id", description = "巡检项目id", required = true),
            @Parameter(name = "patrolItem", description = "巡检项目实体", required = true)})
    @PutMapping("/patrolItem/{id}")
    public ResultBean<Integer> updatePatrolItem(@PathVariable(value = "id") Long id, @RequestBody PatrolItem patrolItem) {
        Date date = new Date();
        patrolItem.setId(id);
        patrolItem.setModifiedUser(securityUtils.getUserName());
        patrolItem.setGmtModified(date);
        try {
            return patrolItemService.updateNotNull(patrolItem);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

    // 巡检周期
    @Operation(summary = "获取巡检周期信息", description = "带参数为分页查询巡检周期，不带为查询所有")
    @GetMapping("/patrolCycle/list")
    public ResultBean<List<PatrolCycle>> getPatrolCycleList() {
        return patrolCycleService.queryInfo();
    }

    @Operation(summary = "查询巡检周期信息", description = "带参数为分页查询巡检周期，不带为查询所有")
    @GetMapping(value = "/patrolCycle/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<PatrolCycle>> getPatrolCycleList(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("sort") String sort) {
        return patrolCycleService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询巡检周期信息", description = "带参数为分页查询巡检周期信息，不带为查询所有")
    @GetMapping(value = "/patrolCycle/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<PatrolCycle>> getPatrolCycleList(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("sort") String sort,
                                                                @PathVariable("field") String field,
                                                                @PathVariable("content") String content) {
        return patrolCycleService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "新增单个巡检周期", description = "需要是巡检周期，才能存上")
    @Parameter(name = "patrolCycle", description = "巡检周期实体", required = true)
    @PostMapping("/patrolCycle/add")
    public ResultBean<Integer> addPatrolCycle(@RequestBody PatrolCycle patrolCycle) {
        Date date = new Date();
        patrolCycle.setCreateUser(securityUtils.getUserName());
        patrolCycle.setGmtCreate(date);
        try {
            return patrolCycleService.createRecord(patrolCycle);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增巡检周期", description = "根据巡检周期实体创建巡检点")
    @Parameter(name = "patrolCycles", description = "巡检周期实体", required = true)
    @PostMapping("/patrolCycle/addList")
    public ResultBean<Integer> addPatrolCycleList(@RequestBody List<PatrolCycle> patrolCycles) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        patrolCycles.forEach(patrolCycle -> {
            patrolCycle.setCreateUser(userName);
            patrolCycle.setGmtCreate(date);
        });
        return patrolCycleService.createRecords(patrolCycles);
    }


    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "删除巡检周期", description = "根据巡检周期id删除巡检点")
    @Parameter(name = "id", description = "巡检周期id", required = true)
    @DeleteMapping("/patrolCycle/{id}")
    public ResultBean<Integer> deletePatrolCycle(@PathVariable(value = "id") Long id) {
        return patrolCycleService.deleteRecord(id);
    }

    @Operation(summary = "批量删除巡检周期", description = "根据巡检周期id批量删除巡检点")
    @Parameter(name = "id", description = "巡检周期id", required = true)
    @PostMapping("/patrolCycle/deleteList")
    public ResultBean<Integer> deletePatrolCycleList(@RequestBody List<Object> ids) {
        return patrolCycleService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新巡检周期", description = "根据巡检周期id更新巡检点信息")
    @Parameters({
            @Parameter(name = "id", description = "巡检周期id", required = true),
            @Parameter(name = "patrolCycle", description = "巡检周期实体", required = true)})
    @PutMapping("/patrolCycle/{id}")
    public ResultBean<Integer> updatePatrolCycle(@PathVariable(value = "id") Long id, @RequestBody PatrolCycle patrolCycle) {
        Date date = new Date();
        patrolCycle.setId(id);
        patrolCycle.setModifiedUser(securityUtils.getUserName());
        patrolCycle.setGmtModified(date);
        try {
            return patrolCycleService.updateNotNull(patrolCycle);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

    // 巡检路线
    @Operation(summary = "获取巡检路线信息", description = "带参数为分页查询巡检路线，不带为查询所有")
    @GetMapping("/patrolRoute/list")
    public ResultBean<List<PatrolRoute>> getPatrolRouteList() {
        return patrolRouteService.queryInfo();
    }

    @Operation(summary = "查询巡检路线信息", description = "带参数为分页查询巡检路线，不带为查询所有")
    @GetMapping(value = "/patrolRoute/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<PatrolRoute>> getPatrolRouteList(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("sort") String sort) {
        return patrolRouteService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询巡检路线信息", description = "带参数为分页查询巡检路线信息，不带为查询所有")
    @GetMapping(value = "/patrolRoute/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<PatrolRoute>> getPatrolRouteList(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("sort") String sort,
                                                                @PathVariable("field") String field,
                                                                @PathVariable("content") String content) {
        return patrolRouteService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "新增单个巡检路线", description = "需要是巡检路线，才能存上")
    @Parameter(name = "patrolRoute", description = "巡检路线实体", required = true)
    @PostMapping("/patrolRoute/add")
    public ResultBean<Integer> addPatrolRoute(@RequestBody PatrolRoute patrolRoute) {
        Date date = new Date();
        patrolRoute.setCreateUser(securityUtils.getUserName());
        patrolRoute.setGmtCreate(date);
        try {
            return patrolRouteService.createRecord(patrolRoute);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增巡检路线", description = "根据巡检路线实体创建巡检路线")
    @Parameter(name = "patrolRoutes", description = "巡检路线实体", required = true)
    @PostMapping("/patrolRoute/addList")
    public ResultBean<Integer> addPatrolRouteList(@RequestBody List<PatrolRoute> patrolRoutes) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        patrolRoutes.forEach(patrolRoute -> {
            patrolRoute.setCreateUser(userName);
            patrolRoute.setGmtCreate(date);
        });
        return patrolRouteService.createRecords(patrolRoutes);
    }


    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "删除巡检路线", description = "根据巡检路线id删除巡检点")
    @Parameter(name = "id", description = "巡检路线id", required = true)
    @DeleteMapping("/patrolRoute/{id}")
    public ResultBean<Integer> deletePatrolRoute(@PathVariable(value = "id") Long id) {
        return patrolRouteService.deleteRecord(id);
    }

    @Operation(summary = "批量删除巡检路线", description = "根据巡检路线id批量删除巡检点")
    @Parameter(name = "id", description = "巡检路线id", required = true)
    @PostMapping("/patrolRoute/deleteList")
    public ResultBean<Integer> deletePatrolRouteList(@RequestBody List<Object> ids) {
        return patrolRouteService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新巡检路线", description = "根据巡检路线id更新巡检点信息")
    @Parameters({
            @Parameter(name = "id", description = "巡检路线id", required = true),
            @Parameter(name = "patrolRoute", description = "巡检路线实体", required = true)})
    @PutMapping("/patrolRoute/{id}")
    public ResultBean<Integer> updatePatrolRoute(@PathVariable(value = "id") Long id, @RequestBody PatrolRoute patrolRoute) {
        Date date = new Date();
        patrolRoute.setId(id);
        patrolRoute.setModifiedUser(securityUtils.getUserName());
        patrolRoute.setGmtModified(date);
        try {
            return patrolRouteService.updateNotNull(patrolRoute);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

    // 巡检任务
    @Operation(summary = "获取巡检任务信息", description = "带参数为分页查询巡检任务，不带为查询所有")
    @GetMapping("/patrolTask/list")
    public ResultBean<List<PatrolTask>> getPatrolTaskList() {
        return patrolTaskService.queryInfo();
    }

    @Operation(summary = "查询巡检任务信息", description = "带参数为分页查询巡检任务，不带为查询所有")
    @GetMapping(value = "/patrolTask/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<PatrolTask>> getPatrolTaskList(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("sort") String sort) {
        return patrolTaskService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询巡检任务信息", description = "带参数为分页查询巡检任务信息，不带为查询所有")
    @GetMapping(value = "/patrolTask/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<PatrolTask>> getPatrolTaskList(@RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size,
                                                              @RequestParam("sort") String sort,
                                                              @PathVariable("field") String field,
                                                              @PathVariable("content") String content) {
        return patrolTaskService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "新增单个巡检任务", description = "需要是巡检任务，才能存上")
    @Parameter(name = "patrolTask", description = "巡检任务实体", required = true)
    @PostMapping("/patrolTask/add")
    public ResultBean<Integer> addPatrolTask(@RequestBody PatrolTask patrolTask) {
        Date date = new Date();
        patrolTask.setCreateUser(securityUtils.getUserName());
        patrolTask.setGmtCreate(date);
        try {
            return patrolTaskService.createRecord(patrolTask);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增巡检任务", description = "根据巡检任务实体创建巡检任务")
    @Parameter(name = "patrolTasks", description = "巡检任务实体", required = true)
    @PostMapping("/patrolTask/addList")
    public ResultBean<Integer> addPatrolTaskList(@RequestBody List<PatrolTask> patrolTasks) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        patrolTasks.forEach(patrolTask -> {
            patrolTask.setCreateUser(userName);
            patrolTask.setGmtCreate(date);
        });
        return patrolTaskService.createRecords(patrolTasks);
    }


    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "删除巡检任务", description = "根据巡检任务id删除巡检点")
    @Parameter(name = "id", description = "巡检任务id", required = true)
    @DeleteMapping("/patrolTask/{id}")
    public ResultBean<Integer> deletePatrolTask(@PathVariable(value = "id") Long id) {
        return patrolTaskService.deleteRecord(id);
    }

    @Operation(summary = "批量删除巡检任务", description = "根据巡检任务id批量删除巡检点")
    @Parameter(name = "id", description = "巡检任务id", required = true)
    @PostMapping("/patrolTask/deleteList")
    public ResultBean<Integer> deletePatrolTaskList(@RequestBody List<Object> ids) {
        return patrolTaskService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新巡检任务", description = "根据巡检任务id更新巡检点信息")
    @Parameters({
            @Parameter(name = "id", description = "巡检任务id", required = true),
            @Parameter(name = "patrolTask", description = "巡检任务实体", required = true)})
    @PutMapping("/patrolTask/{id}")
    public ResultBean<Integer> updatePatrolTask(@PathVariable(value = "id") Long id, @RequestBody PatrolTask patrolTask) {
        Date date = new Date();
        patrolTask.setId(id);
        patrolTask.setModifiedUser(securityUtils.getUserName());
        patrolTask.setGmtModified(date);
        try {
            return patrolTaskService.updateNotNull(patrolTask);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

}
