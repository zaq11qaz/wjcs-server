package com.huihe.eg.authorization.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.authorization.model.ManagerCommentEntity;

public interface ManagerCommentMapper extends BaseFrameworkMapper<ManagerCommentEntity, Long> {
    /**
     *
     * @mbggenerated 2020-10-28
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-10-28
     */
    int insert(ManagerCommentEntity record);

    /**
     *
     * @mbggenerated 2020-10-28
     */
    int insertSelective(ManagerCommentEntity record);

    /**
     *
     * @mbggenerated 2020-10-28
     */
    ManagerCommentEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-10-28
     */
    int updateByPrimaryKeySelective(ManagerCommentEntity record);

    /**
     *
     * @mbggenerated 2020-10-28
     */
    int updateByPrimaryKey(ManagerCommentEntity record);
}