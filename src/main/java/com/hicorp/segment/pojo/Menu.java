package com.hicorp.segment.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * @Author: wqs
 * @Date: Created in 16:53 2021/5/30
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Data
public class Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id;

    @Column(name = "parent_id", columnDefinition = "bigint", nullable = false)
    @Schema(name = "父菜单id", example = "1")
    private Long parentId;

    @Column(name = "menu_orders", columnDefinition = "int", length = 45, nullable = false)
    @Schema(name = "菜单顺序", example = "1")
    private Integer menuOrders;

    @Column(name = "path", columnDefinition = "varchar(255)", length = 45, nullable = false)
    @Schema(name = "前端route路径", example = "/basicData/department")
    private String path;

    @Column(name = "title", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "菜单名", example = "部门管理")
    private String title;

    @Column(name = "icon", columnDefinition = "varchar(45)", length = 45)
    @Schema(name = "菜单图标", example = "users-cog")
    private String icon;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_gmt", columnDefinition = "datetime")
    @Schema(name = "创建时间", example = "2021-11-01 08:00")
    private Date createGmt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_gmt", columnDefinition = "datetime")
    @Schema(name = "修改时间", example = "2021-11-01 08:00")
    private Date modifiedGmt;

    private List<Menu> children;

    /**
     * @Author: 91074
     * @Date: Created in 12:20 2021/6/1
     * @Params: [menus] [java.util.List<com.wqs.haier.pojo.Menu>]
     * @Return: java.util.List<com.wqs.haier.pojo.Menu>
     * @Description:
     * @ChineseDescription: 已作废，因为生成的树为乱序，但该思路不错，所以算法保留
     */
    @Deprecated
    public List<Menu> generateTreeUseSet(List<Menu> menus) {
        try {
            // 返还的树
            List<Menu> rootMenus = new ArrayList<>();
            // 建立HashMap
            Map<Long, Menu> menuMap = new HashMap<>(menus.size());
            // 写入 Map
            for (Menu menu :
                    menus) {
                if (null != menu.getParentId()) {
                    menuMap.put(menu.getId(), menu);
                }
            }
            Set<? extends Map.Entry<Long, ? extends Menu>> entries = menuMap.entrySet();
            // 使用 forEachOrdered 以保持原有的排序。
            entries.parallelStream().forEachOrdered(entry -> {
                Menu value = entry.getValue();
                if (value != null) {
                    Menu menu = menuMap.get(value.getParentId());
                    if (menu != null) {
                        List<Menu> children = menu.getChildren();
                        if (children == null) {
                            children = new ArrayList<>();
                            menu.setChildren(children);
                        }
                        children.add(value);
//                        menu.setChildrenCount(children.size());
                    } else {
                        rootMenus.add(value);
                    }
                }
            });
            return rootMenus;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<Menu> generateTree(List<Menu> menus) {
        try {
            List<Menu> rootMenus = new ArrayList<>();
            for (Menu menu : menus) {
                if (null == menu.getParentId()) {
                    continue;
                }
                if (menu.getParentId() == 1) {
                    // 父节点是1的，为根节点。
                    rootMenus.add(menu);
                }
                for (Menu rootMenu : rootMenus) {
                    // 获取根节点下的所有子节点，使用getChild方法。
                    List<Menu> childList = getChild(rootMenu.getId(), menus);
                    rootMenu.setChildren(childList);//给根节点设置子节点
                }
            }
            return rootMenus;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<Menu> getChild(Long id, List<Menu> allMenu) {
        //子菜单
        List<Menu> childList = new ArrayList<>();
        for (Menu menu : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if (null == menu.getParentId()) {
                continue;
            }
            if (menu.getParentId().equals(id)) {
                childList.add(menu);
            }
        }
        //递归
        for (Menu menu : childList) {
            menu.setChildren(getChild(menu.getId(), allMenu));
        }
        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<>();
        }
        return childList;
    }
}
