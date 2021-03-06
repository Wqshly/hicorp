package com.hicorp.segment.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.hicorp.segment.mapper.MenuMapper;
import com.hicorp.segment.mapper.PermissionMapper;
import com.hicorp.segment.pojo.Menu;
import com.hicorp.segment.pojo.Permission;
import com.hicorp.segment.service.MenuService;
import com.hicorp.segment.service.RoleMenuRelationService;
import com.hicorp.segment.service.RolePermissionRelationService;
import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: wqs
 * @Date: Created in 8:44 2021/5/31
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Component
@Order(1)
@Slf4j
public class InitMethod implements CommandLineRunner {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private MenuMapper menuMapper;

    private final RolePermissionRelationService rolePermissionRelationService;
    private final RoleMenuRelationService roleMenuRelationService;

    private final MenuService menuService;
    private final DocumentationCache documentationCache;
    private final ServiceModelToSwagger2Mapper mapper;


    public InitMethod(DocumentationCache documentationCache, ServiceModelToSwagger2Mapper mapper, MenuService menuService, RolePermissionRelationService rolePermissionRelationService, RoleMenuRelationService roleMenuRelationService) {
        this.documentationCache = documentationCache;
        this.mapper = mapper;
        this.menuService = menuService;
        this.rolePermissionRelationService = rolePermissionRelationService;
        this.roleMenuRelationService = roleMenuRelationService;
    }

    // ????????? permission ?????????
    public void initPermission() {
        // ???????????????api
        Map<String, Boolean> apiMap = Maps.newHashMap();
        List<Permission> apis = permissionMapper.selectAll();
        List<String> compareApis = new ArrayList<>();
        Date date = new Date();
        apis.forEach(api -> {
            apiMap.put(api.getApiPath() + api.getMethod(), true);
        });
        // ??????swagger
        String groupName = Optional.of("all_api").orElse(Docket.DEFAULT_GROUP_NAME);
        Documentation documentation = documentationCache.documentationByGroup(groupName);
        if (documentation == null) {
            return;
        }
        Swagger swagger = mapper.mapDocumentation(documentation);
        // ??????????????????
        for (Map.Entry<String, Path> item : swagger.getPaths().entrySet()) {
            String path = item.getKey();
            String realPath = getRealPath(path);
            compareApis.add(realPath);
            Path pathInfo = item.getValue();
            createApiIfNeeded(apiMap, realPath, pathInfo.getGet(), HttpMethod.GET.name(), date);
            createApiIfNeeded(apiMap, realPath, pathInfo.getPost(), HttpMethod.POST.name(), date);
            createApiIfNeeded(apiMap, realPath, pathInfo.getDelete(), HttpMethod.DELETE.name(), date);
            createApiIfNeeded(apiMap, realPath, pathInfo.getPut(), HttpMethod.PUT.name(), date);
        }
        deleteApiIfDeprecated(apis, compareApis);
    }

    // ????????? menu ?????????, ???????????????????????????????????????????????????
    public void initMenu(String fileName) {
        String json = FileOption.readJsonFile(fileName);
        List<Menu> menus = JsonUtil.decode(json, new TypeReference<>() {
        });
        assert menus != null;
        menuService.dataSave(menus);
    }

    // ??????????????????????????????api?????????????????????????????????.
    public void initRootManager() {
        List<Long> permissions = permissionMapper.selectAllId();
        List<Long> menus = menuMapper.selectAllId();
        rolePermissionRelationService.setRootPermission(1L, permissions);
        roleMenuRelationService.setRootMenu(1L, menus);
    }

    private String getRealPath(String path) {
        String realApiPath = path;
        if (path.startsWith("/hicorp_segment")) {
            realApiPath = path.substring(15);
        }
//        if (path.startsWith("/department")) {
//            realApiPath = path.substring(11);
//        }
        return realApiPath;
    }

    // ?????? API ????????????
    private void createApiIfNeeded(Map<String, Boolean> apiMap, String apiPath, Operation operation, String method, Date currentTime) {
        if (operation == null) {
            return;
        }
        if (!apiMap.containsKey(apiPath + method)) {
            Permission permission = new Permission(operation.getTags().get(0), method, apiPath, operation.getOperationId(), operation.getSummary(), operation.getDescription());
            // ?????????api??????????????????????????????
            permission.setCreateGmt(currentTime);
            permissionMapper.insert(permission);
            apiMap.put(apiPath + method, true);
        } else if (apiMap.containsKey(apiPath + method)) {
            Permission permission = new Permission(operation.getTags().get(0), method, apiPath, operation.getOperationId(), operation.getSummary(), operation.getDescription());
            permission.setModifiedGmt(currentTime);
            permissionMapper.updateByPrimaryKey(permission);
        }
    }

    // ???????????????????????? API
    private void deleteApiIfDeprecated(List<Permission> permissions, List<String> compareApis) {
        List<Long> ids = new ArrayList<>();
        permissions.forEach(permission -> {
            if (!compareApis.contains(permission.getApiPath())) {
                ids.add(permission.getId());
            }
        });
        // ?????????????????????
        while (!ids.isEmpty()) {
            // ??????Example
            Example example = new Example(Permission.class);
            // ??????????????????
            Example.Criteria criteria = example.createCriteria();
            // ????????????,????????????pojo????????????,????????????????????????
            criteria.andIn("id", ids);
            permissionMapper.deleteByExample(example);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        this.initPermission();
        this.initMenu("menu.json");
        this.initRootManager();
        System.out.println("### INIT_TIPS: init resources by implements ApplicationRunner");
    }
}
