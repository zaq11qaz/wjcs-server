package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.MasterMechanismEntity;
import com.huihe.eg.user.model.RechargeRecordEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface MasterMechanismService extends BaseFrameworkService<MasterMechanismEntity, Long> {
    ResultParam mechanismAudit(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response);
    List<MasterMechanismEntity> queryMechanismListPage(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response);
    List<MasterMechanismEntity> queryMechanismStatistics(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response);
    List<MasterMechanismEntity> queryNearbyListPage(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMasterMechanismCount(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> masterMechanismCount(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryByMessage(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMechanismList(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryAppointmentMechanismList(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertAppointmentMechanismInfo(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertMechanismInfo(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response);

    void updateCash(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response);

    List<MasterMechanismEntity> queryMechanismListPageNew(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response);

    void updateOfflineCash(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryTeacherRoom(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response);

    List<MasterMechanismEntity> queryMechanismActivityList(MasterMechanismEntity param, HttpServletRequest request, HttpServletResponse response);
}