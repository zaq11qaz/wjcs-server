package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.ExhibitionPicEntity;
import org.apache.ibatis.annotations.Param;

public interface ExhibitionPicMapper extends BaseFrameworkMapper<ExhibitionPicEntity, Long> {
    /**
     * @mbggenerated 2019-05-21
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-05-21
     */
    @Override
    int insert(ExhibitionPicEntity record);

    /**
     * @mbggenerated 2019-05-21
     */
    @Override
    int insertSelective(ExhibitionPicEntity record);

    /**
     * @mbggenerated 2019-05-21
     */
    @Override
    ExhibitionPicEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-05-21
     */
    @Override
    int updateByPrimaryKeySelective(ExhibitionPicEntity record);

    /**
     * @mbggenerated 2019-05-21
     */
    @Override
    int updateByPrimaryKey(ExhibitionPicEntity record);

    int updateViewTime(@Param("id") Long id);
}