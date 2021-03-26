package com.huihe.eg.message.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.message.model.HistoryEntity;
import com.huihe.eg.message.model.NoteCommentEntity;
import org.apache.ibatis.annotations.Param;


public interface HistoryMapper extends BaseFrameworkMapper<HistoryEntity, Long> {
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
    int insert(HistoryEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int insertSelective(HistoryEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    HistoryEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int updateByPrimaryKeySelective(HistoryEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int updateByPrimaryKey(HistoryEntity record);
    /**
     *查询互动
     * 2019年1月12日
     * zwx
     */
    int queryNoteInteraction(@Param("param") HistoryEntity record);
}