package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserEarnRoleEntity;
import com.huihe.eg.user.model.UserRecommenderEntity;
import com.huihe.eg.user.model.UserRecommenderGroupEntity;
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


    int updateGroupId(@Param("param") UserRecommenderGroupEntity param);

    int updateShareCount(@Param("param") UserRecommenderEntity param);

    int updatePayCount(@Param("param") UserRecommenderEntity userRecommenderEntity);

    List<UserRecommenderEntity> queryGroupListByUserId(@Param("user_id") Long user_id);

    Integer queryGroupListByUserIdCount(@Param("user_id") Long user_id);

    List<UserRecommenderEntity> queryGroupUserDetail(@Param("param") UserRecommenderEntity param);

    UserRecommenderEntity queryByInviteCode(@Param("invite_code") String invite_code);

    int updateInviteCount(@Param("id") Long id);

    int updateInviteCountByCode(@Param("invite_code") String invite_code);

    UserRecommenderEntity findByInviteCode(@Param("invite_code") String invite_code);
}