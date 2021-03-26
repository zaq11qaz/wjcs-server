package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.CouponListEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponListMapper extends BaseFrameworkMapper<CouponListEntity, Long> {
    /**
     *
     * @mbggenerated 2019-06-10
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-10
     */
    @Override
    int insert(CouponListEntity record);

    /**
     *
     * @mbggenerated 2019-06-10
     */
    @Override
    int insertSelective(CouponListEntity record);

    /**
     *
     * @mbggenerated 2019-06-10
     */
    @Override
    CouponListEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-10
     */
    @Override
    int updateByPrimaryKeySelective(CouponListEntity record);

    /**
     *
     * @mbggenerated 2019-06-10
     */
    @Override
    int updateByPrimaryKey(CouponListEntity record);

    CouponListEntity queryByCouponCode(@Param("param") CouponListEntity couponListEntity);

    int updateStatus(@Param("param") CouponListEntity param);

    List<CouponListEntity> queryByMessage(@Param("param") CouponListEntity param);

    Integer queryByMessageCount(@Param("param") CouponListEntity param);

    Integer queryDayCount(@Param("param") CouponListEntity param);

    Integer queryWeekCount(@Param("param") CouponListEntity param);

    Integer queryMonthCount(@Param("param") CouponListEntity param);

    Integer queryTotalCount(CouponListEntity param);
}