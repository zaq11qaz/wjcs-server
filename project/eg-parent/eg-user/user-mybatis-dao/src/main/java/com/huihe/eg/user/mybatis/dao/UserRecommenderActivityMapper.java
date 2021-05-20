package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserRecommenderActivityEntity;
import com.huihe.eg.user.model.UserRecommenderEntity;
import org.apache.ibatis.annotations.Param;

public interface UserRecommenderActivityMapper extends BaseFrameworkMapper<UserRecommenderActivityEntity, Long> {
    /**
     *
     * @mbggenerated 2021-05-19
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-05-19
     */
    int insert(UserRecommenderActivityEntity record);

    /**
     *
     * @mbggenerated 2021-05-19
     */
    int insertSelective(UserRecommenderActivityEntity record);

    /**
     *
     * @mbggenerated 2021-05-19
     */
    UserRecommenderActivityEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-05-19
     */
    int updateByPrimaryKeySelective(UserRecommenderActivityEntity record);

    /**
     *
     * @mbggenerated 2021-05-19
     */
    int updateByPrimaryKey(UserRecommenderActivityEntity record);

    int updatePayCount(@Param("param") UserRecommenderEntity userRecommenderEntity);
}