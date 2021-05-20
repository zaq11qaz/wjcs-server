package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserRecommenderGroupInterlinkEntity;

public interface UserRecommenderGroupInterlinkMapper extends BaseFrameworkMapper<UserRecommenderGroupInterlinkEntity, Long> {
    /**
     *
     * @mbggenerated 2021-05-05
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-05-05
     */
    int insert(UserRecommenderGroupInterlinkEntity record);

    /**
     *
     * @mbggenerated 2021-05-05
     */
    int insertSelective(UserRecommenderGroupInterlinkEntity record);

    /**
     *
     * @mbggenerated 2021-05-05
     */
    UserRecommenderGroupInterlinkEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-05-05
     */
    int updateByPrimaryKeySelective(UserRecommenderGroupInterlinkEntity record);

    /**
     *
     * @mbggenerated 2021-05-05
     */
    int updateByPrimaryKey(UserRecommenderGroupInterlinkEntity record);
}