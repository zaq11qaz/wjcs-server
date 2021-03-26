package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MasterAppointmentEntity;
import com.huihe.eg.user.model.UserOrderEntity;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserOrderMapper extends BaseFrameworkMapper<UserOrderEntity, Long> {
    /**
     * @mbggenerated 2019-03-21
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-03-21
     */
    @Override
    int insert(UserOrderEntity record);

    /**
     * @mbggenerated 2019-03-21
     */
    @Override
    int insertSelective(UserOrderEntity record);

    /**
     * @mbggenerated 2019-03-21
     */
    @Override
    UserOrderEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-03-21
     */
    @Override
    int updateByPrimaryKeySelective(UserOrderEntity record);

    /**
     * @mbggenerated 2019-03-21
     */
    @Override
    int updateByPrimaryKey(UserOrderEntity record);

    /**
     * 查询我送的排行榜
     *
     * @param var1
     * @return
     */
    List<UserOrderEntity> queryRankingListPage(@Param("param") UserOrderEntity var1);

    /**
     * 查询友圈的排行榜
     *
     * @param var1
     * @return
     */
    List<UserOrderEntity> queryPayRankingListPage(@Param("param") UserOrderEntity var1);

    /**
     * 查询送我的排行榜
     *
     * @param var1
     * @return
     */
    List<UserOrderEntity> queryUserRankingListPage(@Param("param") UserOrderEntity var1);

    /**
     * 查询明细
     *
     * @param var1
     * @return
     */
    List<UserOrderEntity> queryDetailListPage(@Param("param") UserOrderEntity var1);

    List<UserOrderEntity> queryDayListPage(@Param("param") UserOrderEntity var1);
    /**
     * @mbggenerated 2019-03-21
     */
    Integer queryListPageSum(@Param("param")UserOrderEntity record);


    List<UserOrderEntity> queryByTime(@Param("param") UserOrderEntity param);

    List<UserOrderEntity> queryByMessage(@Param("param") UserOrderEntity param);

    Integer queryByMessageCount(@Param("param") UserOrderEntity param);

    Integer query7DayInfo(@Param("param") UserOrderEntity param);

    int queryDayListPageCount(@Param("param") UserOrderEntity userOrderEntity);
}