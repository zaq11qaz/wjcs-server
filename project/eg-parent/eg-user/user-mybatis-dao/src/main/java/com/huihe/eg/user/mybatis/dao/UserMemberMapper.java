package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserMemberEntity;
import com.huihe.eg.user.model.count.ChartParam;
import com.huihe.eg.user.model.count.MemberParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMemberMapper extends BaseFrameworkMapper<UserMemberEntity, Long> {
    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    int insert(UserMemberEntity record);

    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    int insertSelective(UserMemberEntity record);

    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    UserMemberEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    int updateByPrimaryKeySelective(UserMemberEntity record);

    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    int updateByPrimaryKey(UserMemberEntity record);

    /**
     * 会员统计
     *
     * @return
     */
    List<MemberParam> memberCount();

    /**
     * 会员统计表
     *
     * @return
     */
    List<ChartParam> memberStatistics(@Param("param") UserMemberEntity record);

    /**
     * 会员比例表
     *
     * @return
     */
    List<MemberParam> memberCountProportion(@Param("param") UserMemberEntity record);

    /**
     * 查询累计会员数量
     *
     * @return
     */
    List<MemberParam> memberTotalCount();

    /**
     * 查询今日注册会员数量
     *
     * @return
     */
    List<MemberParam> memberTedayCount();

    /**
     * 查询今日到期会员数量
     *
     * @return
     */
    List<MemberParam> memberExpireCount();

    /**
     * 查询当前在线会员数量
     *
     * @return
     */
    List<MemberParam> memberOnlineCount();
}