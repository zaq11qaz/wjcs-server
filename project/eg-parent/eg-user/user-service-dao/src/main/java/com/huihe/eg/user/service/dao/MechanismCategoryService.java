package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.user.model.MechanismCategoryEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface MechanismCategoryService extends BaseFrameworkService<MechanismCategoryEntity, Long> {
    List<MechanismCategoryEntity> queryListPageChild(MechanismCategoryEntity param, HttpServletRequest request, HttpServletResponse response);

    List<MechanismCategoryEntity> querySubjects(MechanismCategoryEntity param, HttpServletRequest request, HttpServletResponse response);
}