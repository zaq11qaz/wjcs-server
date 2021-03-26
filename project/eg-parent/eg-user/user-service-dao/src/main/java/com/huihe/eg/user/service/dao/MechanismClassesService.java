package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.MechanismClassesEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MechanismClassesService extends BaseFrameworkService<MechanismClassesEntity, Long> {
    ResultParam insertMechanismCourse(MechanismClassesEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateMergerClass(MechanismClassesEntity param, HttpServletRequest request, HttpServletResponse response);
}