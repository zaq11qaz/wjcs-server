package com.huihe.eg.grab.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.grab.model.GrabTaskEntity;

public interface GrabTaskMapper extends BaseFrameworkMapper<GrabTaskEntity, Long> {
    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    int insert(GrabTaskEntity record);

    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    int insertSelective(GrabTaskEntity record);

    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    GrabTaskEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    int updateByPrimaryKeySelective(GrabTaskEntity record);

    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    int updateByPrimaryKey(GrabTaskEntity record);
}