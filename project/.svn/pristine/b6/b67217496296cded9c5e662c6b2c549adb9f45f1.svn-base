package com.huihe.eg.news.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.news.model.CuriosityEntity;
import com.huihe.eg.news.model.HistoryEntity;
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
    //查询今日发布数量
    Integer queryCuriosityInteraction(@Param(value = "param") HistoryEntity historyEntity);
}