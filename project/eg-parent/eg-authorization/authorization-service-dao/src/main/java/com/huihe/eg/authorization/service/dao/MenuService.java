package com.huihe.eg.authorization.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.authorization.model.MenuEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface MenuService extends BaseFrameworkService<MenuEntity, Long> {
    List<MenuEntity> queryMenuList(MenuEntity entity, HttpServletRequest request, HttpServletResponse response);

    List<MenuEntity> queryMenuChildList(Long id, HttpServletRequest request, HttpServletResponse response);

    List<MenuEntity> queryRoleMenu(Long id, HttpServletRequest request, HttpServletResponse response);
}