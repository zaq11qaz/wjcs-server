package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.user.model.UserGoldTypeEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserGoldTypeService extends BaseFrameworkService<UserGoldTypeEntity, Long> {
    List<UserGoldTypeEntity> queryTeachPaypal(UserGoldTypeEntity param, HttpServletRequest request, HttpServletResponse response);
}