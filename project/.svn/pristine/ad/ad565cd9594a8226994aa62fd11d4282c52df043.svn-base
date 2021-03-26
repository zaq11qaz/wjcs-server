package com.huihe.eg.news.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.news.model.CuriosityCommentEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CuriosityCommentMapper extends BaseFrameworkMapper<CuriosityCommentEntity, Long> {
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
    int insert(CuriosityCommentEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int insertSelective(CuriosityCommentEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    CuriosityCommentEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int updateByPrimaryKeySelective(CuriosityCommentEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int updateByPrimaryKey(CuriosityCommentEntity record);


    /**
     * zwx
     * 查询最热评论
     * @date 2019年1月8日
     */

    List<CuriosityCommentEntity> queryHotCommentListPage(@Param(value = "param")CuriosityCommentEntity record);
}