package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.MasterAppointmentEntity;
import com.huihe.eg.user.model.UserOrderEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface UserOrderService extends BaseFrameworkService<UserOrderEntity, Long> {

    List<UserOrderEntity> queryPaymentRankingListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) ;
    List<UserOrderEntity> queryPayRankingListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response);
    /**
     * 查询送我的排行榜
     * zwx
     * 2019年5月14日15:40:02
     * @param param
     * @param request
     * @param response
     * @return
     */
    List<UserOrderEntity> queryUserRankingListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) ;

    ResultParam queryDayListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryDetailListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response);
    ResultParam updateFreeFriends(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response);
    ResultParam queryFreeFriendsCount(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response);
    ResultParam queryListPageSum(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response);
    Map<String,Object> queryGiftListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryNeedPayment(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryByMessage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertPoint(UserOrderEntity userOrderEntity, HttpServletRequest request, HttpServletResponse response);
}