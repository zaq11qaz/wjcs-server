package com.huihe.eg.user.service.impl.tim;

import com.huihe.eg.user.model.UserRecommenderIncomeLogEntity;
import com.huihe.eg.user.mybatis.dao.CronMapper;
import com.huihe.eg.user.service.dao.MasterAppointmentService;
import com.huihe.eg.user.service.dao.UserAppointmentService;
import com.huihe.eg.user.service.dao.UserRecommenderIncomeLogService;
import com.huihe.eg.user.service.dao.UserStudyCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;

/**
 * 定时
 */
@Configuration
@EnableScheduling
public class AppointmentScheduleConfig implements SchedulingConfigurer {



    @Resource
    private UserAppointmentService userAppointmentService;
    @Resource
    private UserStudyCardService userStudyCardService;
    @Resource
    private MasterAppointmentService masterAppointmentService;
    @Autowired
    @SuppressWarnings("all")
    CronMapper cronMapper;
    @Resource
    private UserRecommenderIncomeLogService userRecommenderIncomeLogService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        /*
        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> userAppointmentService.querySoonClass(),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = cronMapper.getQuerySoonClassCron();
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> masterAppointmentService.getQueryExpireClass(),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = cronMapper.getQueryExpireClassCron();
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> masterAppointmentService.queryClassStartPush(),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = cronMapper.queryClassStartPush();
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
        UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
        userRecommenderIncomeLogEntity.setIs_settlement(false);
        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> userRecommenderIncomeLogService.querySettlementLog(userRecommenderIncomeLogEntity),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = cronMapper.querySettlementLog();
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> userAppointmentService.querySoonMechanismCourse(),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = cronMapper.querySoonMechanismUserCourse();
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> masterAppointmentService.querySoonMechanismCourse(),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = cronMapper.querySoonMechanismMasterCourse();
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> userAppointmentService.queryEndMechanismCourse(),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = cronMapper.queryEndMechanismCourse();
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );

         */
    }

}

