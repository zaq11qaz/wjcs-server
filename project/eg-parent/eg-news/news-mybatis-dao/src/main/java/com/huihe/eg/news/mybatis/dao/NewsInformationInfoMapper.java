package com.huihe.eg.news.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.news.model.NewsInformationInfoEntity;

public interface NewsInformationInfoMapper extends BaseFrameworkMapper<NewsInformationInfoEntity, Long> {
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
    int insert(NewsInformationInfoEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int insertSelective(NewsInformationInfoEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    NewsInformationInfoEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int updateByPrimaryKeySelective(NewsInformationInfoEntity record);

    /**
     *
     * @mbggenerated 2019-01-07
     */
    @Override
    int updateByPrimaryKey(NewsInformationInfoEntity record);
}