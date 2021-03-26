package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MasterCommentEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MasterCommentMapper extends BaseFrameworkMapper<MasterCommentEntity, Long> {
    /**
     *
     * @mbggenerated 2019-06-18
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-18
     */
    @Override
    int insert(MasterCommentEntity record);

    /**
     *
     * @mbggenerated 2019-06-18
     */
    @Override
    int insertSelective(MasterCommentEntity record);

    /**
     *
     * @mbggenerated 2019-06-18
     */
    @Override
    MasterCommentEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-18
     */
    @Override
    int updateByPrimaryKeySelective(MasterCommentEntity record);

    /**
     *
     * @mbggenerated 2019-06-18
     */
    @Override
    int updateByPrimaryKey(MasterCommentEntity record);

    void updateCommentLikeCount(@Param("id") Long id);

    void updateCommentLikeCountCancel(@Param("id") Long id);

    void updateCommentShareCount(@Param("id") Long id);

    MasterCommentEntity queryAvgScore(@Param("param") MasterCommentEntity masterCommentEntity);
}