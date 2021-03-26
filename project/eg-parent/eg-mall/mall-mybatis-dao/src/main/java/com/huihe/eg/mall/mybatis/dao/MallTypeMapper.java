package com.huihe.eg.mall.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.mall.model.MallTypeEntity;

public interface MallTypeMapper extends BaseFrameworkMapper<MallTypeEntity, Long> {
    /**
     *
     * @mbggenerated 2019-09-02
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int insert(MallTypeEntity record);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int insertSelective(MallTypeEntity record);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    MallTypeEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int updateByPrimaryKeySelective(MallTypeEntity record);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int updateByPrimaryKey(MallTypeEntity record);
}