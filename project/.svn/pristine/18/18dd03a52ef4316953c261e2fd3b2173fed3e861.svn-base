package com.huihe.eg.message.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.message.model.NoteCommentEntity;
import com.huihe.eg.message.model.NoteUserEntity;
import org.apache.ibatis.annotations.Param;


public interface NoteCommentMapper extends BaseFrameworkMapper<NoteCommentEntity, Long> {
    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int insert(NoteCommentEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int insertSelective(NoteCommentEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    NoteCommentEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int updateByPrimaryKeySelective(NoteCommentEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int updateByPrimaryKey(NoteCommentEntity record);
    /**
     *查询发布数量
     * 2019年1月12日
     * zwx
     */
    int queryCommentAddCount(@Param("param") NoteCommentEntity record);
}