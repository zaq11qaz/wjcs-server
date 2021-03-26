package com.huihe.eg.user.mybatis.dao;

import org.apache.ibatis.annotations.Select;

public interface CronMapper {
    @Select("select cron from eg_cron where type='querySoonClass'")
    String getQuerySoonClassCron();
    @Select("select cron from eg_cron where type='queryExpireClass'")
    String getQueryExpireClassCron();
    @Select("select cron from eg_cron where type='queryClassStartPush'")
    String queryClassStartPush();
    @Select("select cron from eg_cron where type='querySettlementLog'")
    String querySettlementLog();
    @Select("select cron from eg_cron where type='queryStudyCardCourse'")
    String queryStudyCardCourse();
    @Select("select cron from eg_cron where type='querySoonMechanismUserCourse'")
    String querySoonMechanismUserCourse();
    @Select("select cron from eg_cron where type='querySoonMechanismMasterCourse'")
    String querySoonMechanismMasterCourse();
    @Select("select cron from eg_cron where type='queryEndMechanismCourse'")
    String queryEndMechanismCourse();
}
