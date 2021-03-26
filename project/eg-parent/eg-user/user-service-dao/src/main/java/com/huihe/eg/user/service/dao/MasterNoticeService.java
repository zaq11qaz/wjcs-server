package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.MasterNoticeEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MasterNoticeService extends BaseFrameworkService<MasterNoticeEntity, Long> {

    ResultParam queryIsRead(MasterNoticeEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateIsRead(MasterNoticeEntity param, HttpServletRequest request, HttpServletResponse response) ;
}