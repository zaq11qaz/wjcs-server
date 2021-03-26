package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserEarnRoleEntity;
import com.huihe.eg.user.model.UserRecommenderEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRecommenderMapper extends BaseFrameworkMapper<UserRecommenderEntity, Long> {
    /**
     *
     * @mbggenerated 2020-08-25
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-08-25
     */
    @Override
    int insert(UserRecommenderEntity record);

    /**
     *
     * @mbggenerated 2020-08-25
     */
    @Override
    int insertSelective(UserRecommenderEntity record);

    /**
     *
     * @mbggenerated 2020-08-25
     */
    @Override
    UserRecommenderEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-08-25
     */
    @Override
    int updateByPrimaryKeySelective(UserRecommenderEntity record);

    /**
     *
     * @mbggenerated 2020-08-25
     */
    @Override
    int updateByPrimaryKey(UserRecommenderEntity record);

    Integer updateRoleId(@Param("param") UserEarnRoleEntity userEarnRoleEntity);

    UserRecommenderEntity queryInvateCode(@Param("invate_code") String invate_code);

    Integer updateCash(@Param("param") UserRecommenderEntity userRecommenderEntity);

    List<UserRecommenderEntity> queryByMessage(@Param("param") UserRecommenderEntity param);

    Integer queryByMessageCount(@Param("param") UserRecommenderEntity param);

    Integer queryDayCount(@Param("param") UserRecommenderEntity param);

    Integer queryWeekCount(@Param("param") UserRecommenderEntity param);

    Integer queryMonthCount(@Param("param") UserRecommenderEntity param);

    Integer queryTotalCount(@Param("param") UserRecommenderEntity param);
}