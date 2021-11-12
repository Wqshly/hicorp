package com.hicorp.segment.service;

import com.hicorp.segment.pojo.Menu;
import com.hicorp.segment.utils.ResultBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: wqs
 * @Date: Created in 17:51 2021/5/31
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Service
public interface MenuService extends BasicInterface<Menu> {

    void dataSave(List<Menu> menus);

    ResultBean<List<Menu>> getAllMenuTree();
}
