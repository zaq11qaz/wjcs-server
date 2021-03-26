package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.ClassCardEntity;
import com.huihe.eg.user.model.UserStudyCardEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassCardMapper extends BaseFrameworkMapper<ClassCardEntity, Long> {
    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int insert(ClassCardEntity record);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int insertSelective(ClassCardEntity record);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    ClassCardEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int updateByPrimaryKeySelective(ClassCardEntity record);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int updateByPrimaryKey(ClassCardEntity record);


    List<ClassCardEntity> queryMyClassCard();

    List<Long> queryIdList(@Param("param")UserStudyCardEntity param);


}