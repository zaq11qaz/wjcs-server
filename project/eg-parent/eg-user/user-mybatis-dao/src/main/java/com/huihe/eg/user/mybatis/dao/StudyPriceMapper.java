package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.StudyPriceEntity;

public interface StudyPriceMapper extends BaseFrameworkMapper<StudyPriceEntity, Long> {
    /**
     * @mbggenerated 2019-06-20
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-06-20
     */
    @Override
    int insert(StudyPriceEntity record);

    /**
     * @mbggenerated 2019-06-20
     */
    @Override
    int insertSelective(StudyPriceEntity record);

    /**
     * @mbggenerated 2019-06-20
     */
    @Override
    StudyPriceEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-06-20
     */
    @Override
    int updateByPrimaryKeySelective(StudyPriceEntity record);

    /**
     * @mbggenerated 2019-06-20
     */
    @Override
    int updateByPrimaryKey(StudyPriceEntity record);
}