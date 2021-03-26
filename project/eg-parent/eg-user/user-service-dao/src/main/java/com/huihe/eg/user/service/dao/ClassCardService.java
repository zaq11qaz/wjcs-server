package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.user.model.ClassCardEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ClassCardService extends BaseFrameworkService<ClassCardEntity, Long> {
    List<ClassCardEntity> queryMyClassCard(ClassCardEntity param, HttpServletRequest request, HttpServletResponse response);

    List<ClassCardEntity> queryClassCardList(ClassCardEntity param, HttpServletRequest request, HttpServletResponse response);
}