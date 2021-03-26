package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.SceneTypeEntity;

public interface SceneTypeMapper extends BaseFrameworkMapper<SceneTypeEntity, Long> {
    /**
     *
     * @mbggenerated 2019-07-01
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-07-01
     */
    @Override
    int insert(SceneTypeEntity record);

    /**
     *
     * @mbggenerated 2019-07-01
     */
    @Override
    int insertSelective(SceneTypeEntity record);

    /**
     *
     * @mbggenerated 2019-07-01
     */
    @Override
    SceneTypeEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-07-01
     */
    @Override
    int updateByPrimaryKeySelective(SceneTypeEntity record);

    /**
     *
     * @mbggenerated 2019-07-01
     */
    @Override
    int updateByPrimaryKey(SceneTypeEntity record);
}