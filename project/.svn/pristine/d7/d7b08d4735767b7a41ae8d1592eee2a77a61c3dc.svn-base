package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.ClassRecordEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ClassRecordService extends BaseFrameworkService<ClassRecordEntity, Long> {
    ResultParam queryProgressiveGroup(ClassRecordEntity param, HttpServletRequest request, HttpServletResponse response);
    List<ClassRecordEntity> queryStayListPage(ClassRecordEntity param, HttpServletRequest request, HttpServletResponse response);
    List<ClassRecordEntity> queryHistoryListPage(ClassRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryByMessage(ClassRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertViewRecordings(ClassRecordEntity param, HttpServletRequest request, HttpServletResponse response);
}