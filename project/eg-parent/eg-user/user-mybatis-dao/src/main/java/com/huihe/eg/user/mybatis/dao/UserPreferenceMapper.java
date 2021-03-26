package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserPreferenceEntity;
import org.apache.ibatis.annotations.Param;

public interface UserPreferenceMapper extends BaseFrameworkMapper<UserPreferenceEntity, Long> {
    /**
     *
     * @mbggenerated 2019-04-18
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-04-18
     */
    @Override
    int insert(UserPreferenceEntity record);

    /**
     *
     * @mbggenerated 2019-04-18
     */
    @Override
    int insertSelective(UserPreferenceEntity record);

    /**
     *
     * @mbggenerated 2019-04-18
     */
    @Override
    UserPreferenceEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-04-18
     */
    @Override
    int updateByPrimaryKeySelective(UserPreferenceEntity record);

    /**
     *
     * @mbggenerated 2019-04-18
     */
    @Override
    int updateByPrimaryKey(UserPreferenceEntity record);
    int updateStatus(@Param("param")UserPreferenceEntity record);
}