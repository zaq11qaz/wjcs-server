package com.huihe.eg.news.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.news.model.BrowesHistoryEntity;
import org.apache.ibatis.annotations.Param;

public interface BrowesHistoryMapper extends BaseFrameworkMapper<BrowesHistoryEntity, Long> {
    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    int insert(BrowesHistoryEntity record);

    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    int insertSelective(BrowesHistoryEntity record);

    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    BrowesHistoryEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    int updateByPrimaryKeySelective(BrowesHistoryEntity record);

    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    int updateByPrimaryKey(BrowesHistoryEntity record);
    /**
     * 2019年2月15日15:48:04
     * zwx
     * 查询浏览时长总和
     */
    Integer getAllDurationTime(@Param(value = "param")BrowesHistoryEntity param);
    /**
     * 2019年2月15日15:48:04
     * zwx
     * 查询浏览次数总和
     */
    int getAllBrowseCount(@Param(value = "param")BrowesHistoryEntity param);
    /**
     * 2019年3月9日17:34:50
     * zwx
     * 修改时长和次数
     */
    ResultParam addRecord(@Param(value = "param")BrowesHistoryEntity param);

}