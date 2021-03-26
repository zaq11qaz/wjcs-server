package com.huihe.eg.news.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.news.model.CuriosityEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CuriosityMapper extends BaseFrameworkMapper<CuriosityEntity, Long> {
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
    int insert(CuriosityEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int insertSelective(CuriosityEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    CuriosityEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int updateByPrimaryKeySelective(CuriosityEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int updateByPrimaryKey(CuriosityEntity record);

    /**
     * 查询好奇内容
     * @param curiosityEntity oper_id
     * @return List<CuriosityEntity>
     *     2019年1月15日
     *     zwx
     */
    List<CuriosityEntity> queryCuriosityListPage(@Param(value = "param")CuriosityEntity curiosityEntity);

    /**
     * 查询推荐
     * zwx
     * 2019年1月16日
     * @param curiosityEntity oper_id
     */
    List<CuriosityEntity> queryRecommendCuriosityListPage(@Param(value = "param") CuriosityEntity curiosityEntity);
    /**
     * 热门
     * zwx
     * 2019年1月16日
     * @param curiosityEntity
     */
    List<CuriosityEntity> queryHotRankingListPage(@Param(value = "param") CuriosityEntity curiosityEntity);
    /**
     * 月榜
     * zwx
     * 2019年1月16日
     * @param curiosityEntity
     */
    List<CuriosityEntity> queryMonthRankingListPage(@Param(value = "param") CuriosityEntity curiosityEntity);
    /**
     * 周榜
     * zwx
     * 2019年1月16日
     * @param curiosityEntity
     */
    List<CuriosityEntity> queryWeekRankingListPage(@Param(value = "param") CuriosityEntity curiosityEntity);
    /**
     *围观数量
     * zwx
     * @mbggenerated 2019年2月21日15:58:10
     */
    Integer queryGatherCount(@Param(value = "param") CuriosityEntity curiosityEntity);

    //查询今日发布数量
    Integer queryAddCount(@Param(value = "param") CuriosityEntity curiosityEntity);

}