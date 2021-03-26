package com.huihe.eg.push.springAop;

import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.push.model.PushHistoryEntity;
import com.huihe.eg.push.mybatis.PushHistoryMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
@Component
public class MyMethodAfterAdvice {

    private final static Logger logger = LoggerFactory.getLogger(MyMethodAfterAdvice.class);


    @Resource
    private PushHistoryMapper pushHistoryMapper;

    @Pointcut("execution(* com.huihe.eg.push.PushController.*(..))")
    public void myMethodAfterAdvice() {
    }

    @After(value = "myMethodAfterAdvice()")
    public void doAfter(JoinPoint joinPoint) {
        PushMessageParam pushMessageParam = new PushMessageParam();
        try {

            Object[] args = joinPoint.getArgs();

            if (args != null && args.length == 1) {
                for (Object arg : args) {
                    pushMessageParam = (PushMessageParam) arg;
                }
            }
            if (!"video" .equals(pushMessageParam.getType())) {
                PushHistoryEntity pushHistoryEntity = new PushHistoryEntity();
                pushHistoryEntity.setTitle(pushMessageParam.getTitle());
                pushHistoryEntity.setContext(pushMessageParam.getContent());
                pushHistoryEntity.setTarget_id(pushMessageParam.getTarget_id());
                pushHistoryEntity.setLarge_type(pushMessageParam.getType());
                pushHistoryEntity.setType_id(pushMessageParam.getType_id().intValue());
                pushHistoryEntity.setOpera_type(pushMessageParam.getOpera_type());
                if (pushHistoryEntity.getView_type() != null) {
                    pushHistoryEntity.setView_type(pushMessageParam.getView_type());
                }
                if (pushHistoryEntity.getSend_id() != null) {
                    pushHistoryEntity.setSend_id(pushMessageParam.getSend_id().intValue());
                }

                /**
                 * 礼物消息
                 */
                if ("gift" .equals(pushMessageParam.getType())) {

                    if (pushMessageParam.getSend_id() == null) {
                        pushHistoryEntity.setType("event");
                    } else {
                        pushHistoryEntity.setType("dynamic");
                    }
                }
                /**
                 * 金币事件
                 */
                if ("login" .equals(pushMessageParam.getType()) || "register" .equals(pushMessageParam.getType()) ||
                        "task" .equals(pushMessageParam.getType()) || "chat" .equals(pushMessageParam.getType()) ||
                        "recharge" .equals(pushMessageParam.getType()) || "sign" .equals(pushMessageParam.getType()) ||
                        "payment" .equals(pushMessageParam.getType()) || "redenvelopes" .equals(pushMessageParam.getType()) ||
                        "watch_recording" .equals(pushMessageParam.getType())||"sign_in" .equals(pushMessageParam.getType())

                ) {
                    pushHistoryEntity.setType("event");
                }

                /**
                 * 点赞 订阅消息
                 */
                if ("curiosity" .equals(pushMessageParam.getType()) || "viewpoint" .equals(pushMessageParam.getType())
                        || "curiosityviewpoint" .equals(pushMessageParam.getType()) || "note" .equals(pushMessageParam.getType())
                        || "add_friends" .equals(pushMessageParam.getType())

                ) {
                    pushHistoryEntity.setType("dynamic");
                }

                /**
                 * 系统通知
                 */
                if ("updateAuth" .equals(pushMessageParam.getType()) || "pay" .equals(pushMessageParam.getType())
                        || "frozenStatus" .equals(pushMessageParam.getType()) || "cash" .equals(pushMessageParam.getType())
                        || "sale_course" .equals(pushMessageParam.getType()) || "mechanism_offline" .equals(pushMessageParam.getType())
                        || "master_offline" .equals(pushMessageParam.getType())  || "sign" .equals(pushMessageParam.getType())
                        || "buy_course" .equals(pushMessageParam.getType()) || "mechanism_user_need_check" .equals(pushMessageParam.getType())
                        || "buy_coupon" .equals(pushMessageParam.getType())

                ) {
                    pushHistoryEntity.setType("system");
                }

                /**
                 * 课程通知
                 */
                if ("apply_appointment_Cancel" .equals(pushMessageParam.getType()) || "appointment_Cancel_request" .equals(pushMessageParam.getType())
                        || "curriculumCancelRespond" .equals(pushMessageParam.getType()) || "curriculum_insert_Respond_agree" .equals(pushMessageParam.getType())
                        || "curriculum_Cancel_Respond_agree" .equals(pushMessageParam.getType()) || "curriculum_Cancel_Respond_disagree" .equals(pushMessageParam.getType())
                        || "curriculum_update_Respond" .equals(pushMessageParam.getType()) || "curriculum_Cancel_request" .equals(pushMessageParam.getType())
                        || "curriculum_appointment_full" .equals(pushMessageParam.getType())
                        || "curriculum_appointment" .equals(pushMessageParam.getType())
                        || "curriculum_Update" .equals(pushMessageParam.getType()) || "groupClassNotice" .equals(pushMessageParam.getType())
                        || "class" .equals(pushMessageParam.getType()) || "single_class_close" .equals(pushMessageParam.getType())
                        || "offLine_cancel_Course" .equals(pushMessageParam.getType()) || "offLine_course_end" .equals(pushMessageParam.getType())
                        || "offline_course_sign" .equals(pushMessageParam.getType())
                        || "course_evaluation" .equals(pushMessageParam.getType()) || "single_course_evaluation_comment" .equals(pushMessageParam.getType())
                        || "live_class_end" .equals(pushMessageParam.getType()) || "live_class_start" .equals(pushMessageParam.getType())
                        || "student_class_notice" .equals(pushMessageParam.getType()) || "student_single_class_notice" .equals(pushMessageParam.getType())
                        || "teacher_class_notice" .equals(pushMessageParam.getType()) || "teacher_single_class_notice" .equals(pushMessageParam.getType())
                        || "live_class_statr_notice" .equals(pushMessageParam.getType())|| "offline_course_reminders" .equals(pushMessageParam.getType())
                        || "appointment_scheduling" .equals(pushMessageParam.getType())|| "summary" .equals(pushMessageParam.getType())

                ) {
                    pushHistoryEntity.setType("auth");
                }
                pushHistoryMapper.insertSelective(pushHistoryEntity);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}