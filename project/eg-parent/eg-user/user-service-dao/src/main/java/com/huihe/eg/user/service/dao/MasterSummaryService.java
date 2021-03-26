package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.MasterSummaryEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface MasterSummaryService extends BaseFrameworkService<MasterSummaryEntity, Long> {
    ResultParam insertSummary(MasterSummaryEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertSummaryOffline(MasterSummaryEntity param, HttpServletRequest request, HttpServletResponse response);

    List<MasterSummaryEntity> querySummaryList(MasterSummaryEntity param, HttpServletRequest request, HttpServletResponse response);
}