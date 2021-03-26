package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.CouponListEntity;
import com.huihe.eg.user.model.UserCouponEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCouponMapper extends BaseFrameworkMapper<UserCouponEntity, Long> {
    /**
     * @mbggenerated 2019-06-06
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-06-06
     */
    @Override
    int insert(UserCouponEntity record);

    /**
     * @mbggenerated 2019-06-06
     */
    @Override
    int insertSelective(UserCouponEntity record);

    /**
     * @mbggenerated 2019-06-06
     */
    @Override
    UserCouponEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-06-06
     */
    @Override
    int updateByPrimaryKeySelective(UserCouponEntity record);

    /**
     * @mbggenerated 2019-06-06
     */
    @Override
    int updateByPrimaryKey(UserCouponEntity record);

    List<UserCouponEntity> queryByMessage(@Param("param") UserCouponEntity param);

    Integer queryByMessageCount(@Param("param") UserCouponEntity param);

    Integer queryDayCount(@Param("param") UserCouponEntity param);

    Integer queryWeekCount(@Param("param") UserCouponEntity param);

    Integer queryMonthCount(@Param("param") UserCouponEntity param);

    Integer queryTotalCount(@Param("param")UserCouponEntity param);

    List<UserCouponEntity> queryDetailListPage(@Param("param") UserCouponEntity userCouponEntity);

    int updateStatusUsed(@Param("coupon_id") Long coupon_id,@Param("mechanism_id")Long mechanism_id);

    int updateStatusUnUsed(@Param("coupon_id") Long coupon_id);
}