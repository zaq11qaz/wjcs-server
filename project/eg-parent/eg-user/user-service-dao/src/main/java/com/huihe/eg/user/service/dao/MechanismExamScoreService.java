package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.MechanismExamScoreEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MechanismExamScoreService extends BaseFrameworkService<MechanismExamScoreEntity, Long> {
    ResultParam insertExamScore(MechanismExamScoreEntity param, HttpServletRequest request, HttpServletResponse response);
}