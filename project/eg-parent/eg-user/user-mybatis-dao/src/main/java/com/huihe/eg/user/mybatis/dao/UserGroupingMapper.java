package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserGroupingEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserGroupingMapper extends BaseFrameworkMapper<UserGroupingEntity, Long> {
    /**
     *
     * @mbggenerated 2021-01-04
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-01-04
     */
    int insert(UserGroupingEntity record);

    /**
     *
     * @mbggenerated 2021-01-04
     */
    int insertSelective(UserGroupingEntity record);

    /**
     *
     * @mbggenerated 2021-01-04
     */
    UserGroupingEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-01-04
     */
    int updateByPrimaryKeySelective(UserGroupingEntity record);

    /**
     *
     * @mbggenerated 2021-01-04
     */
    int updateByPrimaryKey(UserGroupingEntity record);

    List<UserGroupingEntity> querylistPageByIdAsc(@Param("param") UserGroupingEntity param);

    List<UserGroupingEntity> querylistPageByIdAscInTime(@Param("param") UserGroupingEntity userGroupingEntity);

    List<Long> queryUserIds(@Param("param") UserGroupingEntity userGroupingEntity);

    Object queryEndTime(@Param("param") UserGroupingEntity userGroupingEntity);

    int updatePresentLesson(@Param("param") UserGroupingEntity userGroupingEntity);

    Integer queryMoreThanListPageCount(@Param("param") UserGroupingEntity userGroupingEntity);

    List<UserGroupingEntity> queryListPageNotIn(@Param("param") UserGroupingEntity userGroupingEntity);

    List<UserGroupingEntity> queryGroupIdList(@Param("param") UserGroupingEntity userGroupingEntity);
}