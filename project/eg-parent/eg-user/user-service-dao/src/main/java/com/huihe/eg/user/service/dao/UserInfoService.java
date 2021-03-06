package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.RechargeRecordEntity;
import com.huihe.eg.user.model.UserInfoEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserInfoService extends BaseFrameworkService<UserInfoEntity, Long> {
    Map<String, Object> userCount(UserInfoEntity param,HttpServletRequest request, HttpServletResponse response);

    /**
     * 队列异步修改注册统计数量
     */
    void queueUpdateUser(UserInfoEntity userInfoEntity);

    List<UserInfoEntity> queryUserListPage(UserInfoEntity userInfoEntity, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    /**
     * 用户详情
     *
     * @param param
     * @param request
     * @param response
     * @return zwx
     * 2019年4月3日19:47:08
     */
    ResultParam queryUserInfoById(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    void queryUserinfoInsertRedis(UserInfoEntity param);

    List<UserInfoEntity> queryFriendInfo(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response);
    int TIMregister(UserInfoEntity base);
    void batchImportUserid();
    void updatetimUserInfo(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response);
    ResultParam updateUserBatch(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) ;
    List<UserInfoEntity> queryNewListPage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) ;
    List<UserInfoEntity> queryBackstage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response);
    List<UserInfoEntity> queryUserOnline(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response);
    Map<String,Object> queryMemberListPage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object>  queryByMessage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    List<UserInfoEntity> queryEachMaster(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response);
    //void deletetUserid(Long user_id);

    void AddInviteCash(Long user_id,Long invite_id, HttpServletRequest request, HttpServletResponse response);

    void updateInviteCode(UserInfoEntity param);


    Map<String,Object> queryHelperInfoListPage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    Object deleteUserSign(Long id, HttpServletRequest request, HttpServletResponse response);

    List<UserInfoEntity> queryMechanismMasterList(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    void updateUserPoint(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryTeachPayUserStatistics(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response);

    void deleteRedisById(UserInfoEntity param);

    ResultParam checkIsCanPay(RechargeRecordEntity entity);

    BigDecimal checkPayDeposit(Long user_id , BigDecimal finalAmount);

}