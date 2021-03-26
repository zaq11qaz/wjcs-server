package com.huihe.eg.push.mybatis;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.push.model.PushDeviceEntity;

public interface PushDeviceMapper extends BaseFrameworkMapper<PushDeviceEntity, Long> {
    /**
     *
     * @mbggenerated 2019-06-17
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-17
     */
    @Override
    int insert(PushDeviceEntity record);

    /**
     *
     * @mbggenerated 2019-06-17
     */
    @Override
    int insertSelective(PushDeviceEntity record);

    /**
     *
     * @mbggenerated 2019-06-17
     */
    @Override
    PushDeviceEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-17
     */
    @Override
    int updateByPrimaryKeySelective(PushDeviceEntity record);

    /**
     *
     * @mbggenerated 2019-06-17
     */
    @Override
    int updateByPrimaryKey(PushDeviceEntity record);
}