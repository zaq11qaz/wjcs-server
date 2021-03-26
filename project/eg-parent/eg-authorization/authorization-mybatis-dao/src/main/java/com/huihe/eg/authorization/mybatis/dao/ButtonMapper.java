package com.huihe.eg.authorization.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.authorization.model.ButtonEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ButtonMapper extends BaseFrameworkMapper<ButtonEntity, Long> {
    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int insert(ButtonEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int insertSelective(ButtonEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    ButtonEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int updateByPrimaryKeySelective(ButtonEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int updateByPrimaryKey(ButtonEntity record);

    List<ButtonEntity> queryMenuButton(@Param("id") Long id);
}