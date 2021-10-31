package com.hicorp.segment.service.impl;

import com.hicorp.segment.mapper.MenuMapper;
import com.hicorp.segment.pojo.Menu;
import com.hicorp.segment.service.MenuService;
import com.hicorp.segment.utils.ResultBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:
 * @Date: Created in 17:52 2021/5/31
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Service("MenuService")
public class MenuServiceImpl extends BasicInterfaceImpl<Menu> implements MenuService {
    @Resource
    private MenuMapper menuMapper;

    // final表示地址不能修改，但是地址对应的内存区域的值是可以修改的。
    // 利用该变量，记录更新菜单后，需作废删除的菜单项。
    private static final Map<Long, Boolean> deleteMenuIdsMap = new HashMap<>();

    // 记录菜单的顺序。
    private static Integer menuOrders = 0;

    @Override
    public void dataSave(List<Menu> newMenuTree) {

        // 查询已有的菜单数据
        Date date = new Date();
        List<Menu> oldMenuList = menuMapper.selectAll();
        Map<String, Long> oldMenuMap = new HashMap<>();

        try {
            oldMenuList.forEach(oldMenu -> {
                oldMenuMap.put(oldMenu.getPath(), oldMenu.getId());
                deleteMenuIdsMap.put(oldMenu.getId(), false);
            });
            // 存储并计算旧菜单中作废的项。
            treeDataSave(newMenuTree, oldMenuMap, date);
            // 删除旧菜单中作废的项
            deleteMenuIdsMap.forEach((id, flag) -> {
                menuMapper.deleteByPrimaryKey(id);
            });
            // 置空hashmap
            deleteMenuIdsMap.clear();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void treeDataSave(List<Menu> menuTree, Map<String, Long> oldMenuMap, Date date) {
        for (Menu newMenu :
                menuTree) {
            menuOrders += 1;
            newMenu.setMenuOrders(menuOrders);
            if (oldMenuMap.containsKey(newMenu.getPath())) { // 更新
                newMenu.setId(oldMenuMap.get(newMenu.getPath()));
                newMenu.setModifiedGmt(date);
                menuMapper.updateByPrimaryKeySelective(newMenu);
                deleteMenuIdsMap.remove(newMenu.getId());
            } else { // 新增
                newMenu.setCreateGmt(date);
                menuMapper.insert(newMenu);
            }
            Long id = newMenu.getId();
            if (null != newMenu.getChildren()) {
                if (!newMenu.getChildren().isEmpty()) {
                    newMenu.getChildren().forEach(childMenu -> {
                        childMenu.setParentId(id);
                    });
                    // 递归
                    this.treeDataSave(newMenu.getChildren(), oldMenuMap, date);
                }
            }
        }
    }

    @Override
    public ResultBean<List<Menu>> getAllMenuTree() {
        try {
            List<Menu> menus = menuMapper.selectAll();
            List<Menu> menuTree = new Menu().generateTree(menus);
            return new ResultBean<>(menuTree);
        } catch (Exception e) {
            return new ResultBean<>(400, "查询失败!");
        }
    }

}
