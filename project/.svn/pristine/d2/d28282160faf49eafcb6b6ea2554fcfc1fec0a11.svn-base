package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.MasterInfoEntity;
import com.huihe.eg.user.model.RechargeRecordEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface MasterInfoService extends BaseFrameworkService<MasterInfoEntity, Long> {
    /**
     * 审核
     * @param param
     * @param request
     * @param response
     * @return
     */
    ResultParam masterAudit(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> queryMasterAuthListPage(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response);

    List<MasterInfoEntity> queryTeachingCenter(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryHomeListPage(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    List<MasterInfoEntity> queryHomeMaster(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);
    List<MasterInfoEntity> queryMasterListPage(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);
    ResultParam masterMechanismInsert(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);
    List<MasterInfoEntity> queryMechanismMasters(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);
    ResultParam masterBindingSwitch(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> countMaster(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> masterAuditCount(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> helperCount(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);
    List<MasterInfoEntity> queryRecommendMasterInfo(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);
    Map<String,Object>  queryByMessage(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryEachType(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);
    List<MasterInfoEntity> queryMechanismPrivateListPage(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMasterLoginInfo(MasterInfoEntity param, HttpServletResponse request, HttpServletResponse response);

    ResultParam upDateMasterStatus(Map<String, String> param, HttpServletResponse request, HttpServletResponse response);

    Map<String,Object> queryMasterTeacherInfo(MasterInfoEntity masterInfoEntity, HttpServletResponse request, HttpServletResponse response);

    Map<String,Object> updateMasterInfo(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response);

    List<MasterInfoEntity> queryPassMaster(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMasterList(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> querEachMasterCount(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response);

    List<MasterInfoEntity> queryPrivateMaster(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertInviteMaster(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateFaceVideo(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> queryByFullName(String full_name, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> querySingleMasterListPage(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response);

    void updateCash(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateMechanismID(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMechanismCount(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMechanismHelperList(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryRecommendMasterInfoPC(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMasterListPagePC(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    List<MasterInfoEntity> queryByLanguage(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response);

    List<MasterInfoEntity> queryMasterListPageNew(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateMechanismMasterInfo(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response);
}