package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.BaseMap;
import com.huihe.eg.user.model.UserRecommenderEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface UserRecommenderService extends BaseFrameworkService<UserRecommenderEntity, Long> {
    ResultParam recommenderAudit(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryEarningsThisMonth(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertMechismRecommender(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response);

    List<UserRecommenderEntity> queryMyMechanismRecommenderInfo(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryRecommenderCount(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryByMessage(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryRecommenderListCount(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response);

    Object updateShareCount(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response);

    BaseMap queryGroupListByUserId(UserRecommenderEntity map, HttpServletRequest request, HttpServletResponse response);

    List<UserRecommenderEntity> queryGroupUserDetail(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response);
}