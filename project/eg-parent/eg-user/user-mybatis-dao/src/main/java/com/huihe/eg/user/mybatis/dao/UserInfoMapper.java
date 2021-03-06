package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.RechargeRecordEntity;
import com.huihe.eg.user.model.UserEarnRoleEntity;
import com.huihe.eg.user.model.UserInfoEntity;
import com.huihe.eg.user.model.count.ChartParam;
import com.huihe.eg.user.model.count.StatisticsParam;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserInfoMapper extends BaseFrameworkMapper<UserInfoEntity, Long> {
    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    int deleteByPrimaryKey(Long user_id);

    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    int insert(UserInfoEntity record);

    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    int insertSelective(UserInfoEntity record);

    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    UserInfoEntity selectByPrimaryKey(Long user_id);

    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    int updateByPrimaryKeySelective(UserInfoEntity record);

    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    int updateByPrimaryKey(UserInfoEntity record);

    /**
     * 查询同事
     *
     * @param userInfoEntity
     * @return
     */
    List<UserInfoEntity> queryColleagueListPage(@Param("param") UserInfoEntity userInfoEntity);

    /**
     * 查询同学
     *
     * @param userInfoEntity
     * @return
     */
    List<UserInfoEntity> querySchoolmateListPage(@Param("param") UserInfoEntity userInfoEntity);

    /**
     * 查询通讯录好友
     *
     * @param userInfoEntity
     * @return
     */
    List<UserInfoEntity> queryContactsListPage(@Param("param") UserInfoEntity userInfoEntity);

    /**
     * 查询通讯录好友
     *
     * @param userInfoEntity
     * @return
     */
    List<UserInfoEntity> queryNearbyListPage(@Param("param") UserInfoEntity userInfoEntity);

    /**
     * 默认首页列表查询
     *
     * @param userInfoEntity
     * @return
     */
    List<UserInfoEntity> queryUserListPage(@Param("param") UserInfoEntity userInfoEntity);

    /**
     * 后台 用户的统计
     *
     * @return
     */
    StatisticsParam userStatistics();

    /**
     * 后台 统计图统计
     *
     * @return
     */
    ChartParam queryChart(@Param("param") UserInfoEntity userInfoEntity);

    int queueUpdateUser(UserInfoEntity userInfoEntity);

    List<Long> queryAllUserid(@Param("param") UserInfoEntity userInfoEntity);



    /**
     * 减
     *
     * @mbggenerated 2019年4月17日19:00:15
     */
    int updateChattingCount(@Param("param") UserInfoEntity record);

    /**
     * 增加
     *
     * @mbggenerated 2019年4月17日19:00:43
     */
    int updateAddChattingCount(@Param("param") UserInfoEntity record);

    /**
     * 增加注册数量
     *
     * @mbggenerated 2019年4月17日19:00:15
     */
    int updateRegistrNum(@Param("param") UserInfoEntity record);

    /**
     * 增加猫币数量
     * 2019年4月24日20:48:05
     */
    int updateAddCatCoin(@Param("param") UserInfoEntity record);

    /**
     * 扣除猫币数量
     * 2019年4月24日20:48:05
     */
    int updateReduceCatCoin(@Param("param") UserInfoEntity record);

    List<Long> queryNewListPage(@Param("param") UserInfoEntity userInfoEntity);

    List<UserInfoEntity> queryMasterListPage(@Param("param") UserInfoEntity userInfoEntity);

    List<UserInfoEntity> queryUserOnline(@Param("param") UserInfoEntity userInfoEntity);

    /**
     * 会员管理注册信息
     *
     * @param userInfoEntity
     * @return
     */
    List<UserInfoEntity> queryMemberListPage(@Param("param") UserInfoEntity userInfoEntity);

    /**
     * 会员管理注册信息
     *
     * @param userInfoEntity
     * @return
     */
    Integer queryMemberListPageCount(@Param("param") UserInfoEntity userInfoEntity);

    List<UserInfoEntity> queryByMessage(@Param("param") UserInfoEntity userInfoEntity);

    List<UserInfoEntity> queryByNickName(@Param("param")UserInfoEntity userInfoEntity);

    //查询自然月入驻
    List<UserInfoEntity> queryThisMonth(@Param("param") UserInfoEntity userInfoEntity);

    Integer queryMasterCountTotal(@Param("param") UserInfoEntity userInfoEntity);

    Integer updateCash(@Param("param") UserInfoEntity userInfoEntity);

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/11 11:03
     * @ Description：根据邀请码查询用户信息
     * @since: JDk1.8
     */
    UserInfoEntity queryByInviteCode(@Param("invite_code") String invite_code);

    void updateRoleId(@Param("param") UserEarnRoleEntity param);

    void updateInvateCode(@Param("param") UserInfoEntity userInfoEntity);

    List<Long> queryIdByNickName(@Param("nick_name") String nick_name);

    Integer updateSubCash(@Param("user_id") Long user_id, @Param("bigDecimal") BigDecimal bigDecimal);

    Integer queryByMessageCount(@Param("param") UserInfoEntity param);

    UserInfoEntity queryHelperList(@Param("identity_id") Long identity_id);

    int updateReduceChattingCount(@Param("param") UserInfoEntity entity);

    List<UserInfoEntity> queryInfoList(@Param("param") UserInfoEntity param);

    int updateIsCollection(@Param("param") UserInfoEntity param);

    int updateSubPoint(@Param("param") UserInfoEntity param);

    int updateAddPoint(@Param("param") UserInfoEntity param);

    String[] queryUserAvatar(@Param("param")UserInfoEntity userInfoEntity);

    BigDecimal queryUserPayPreAmountByUserId(@Param("user_id") Long user_id);

    ResultParam updateUserPayPreAmountByUserId(@Param("param") RechargeRecordEntity rechargeRecordEntity);

    int updatePaypreAmount0(@Param("user_id") Long user_id);

    List<UserInfoEntity> queryInviteCodeNull(@Param("param") UserInfoEntity param);

    int updateManagerId(@Param("id") Long id, @Param("user_id") Long user_id);
}