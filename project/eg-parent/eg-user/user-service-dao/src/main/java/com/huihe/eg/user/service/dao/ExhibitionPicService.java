package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.ExhibitionPicEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ExhibitionPicService extends BaseFrameworkService<ExhibitionPicEntity, Long> {
    ResultParam updateClickCount(ExhibitionPicEntity param, HttpServletRequest request, HttpServletResponse response);
}