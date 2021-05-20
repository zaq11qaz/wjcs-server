package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserRecommenderEntity;
import com.huihe.eg.user.model.UserRecommenderGroupEntity;
import org.apache.ibatis.annotations.Param;

public interface UserRecommenderGroupMapper extends BaseFrameworkMapper<UserRecommenderGroupEntity, Long> {
    /**
     *
     * @mbggenerated 2021-05-05
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-05-05
     */
    int insert(UserRecommenderGroupEntity record);

    /**
     *
     * @mbggenerated 2021-05-05
     */
    int insertSelective(UserRecommenderGroupEntity record);

    /**
     *
     * @mbggenerated 2021-05-05
     */
    UserRecommenderGroupEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-05-05
     */
    int updateByPrimaryKeySelective(UserRecommenderGroupEntity record);

    /**
     *
     * @mbggenerated 2021-05-05
     */
    int updateByPrimaryKey(UserRecommenderGroupEntity record);

    int updateGroupCount(@Param("id") Long id);

    int addGroupCount(@Param("group_id") Long group_id);

    UserRecommenderGroupEntity queryAdminByRecommenderId(@Param("param") UserRecommenderEntity userRecommenderEntity);
}