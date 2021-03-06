package com.huihe.eg.push;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.util.json.JSONUtils;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.push.mybatis.PushDeviceMapper;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class PushJobs {

    private static final Logger logger = Logger.getLogger(PushJobs.class);

    public final static long ONE_MINUTE = 60 * 1000;
    @Resource
    private RedisService redisService;
    @Resource
    private PushController pushController;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private PushDeviceMapper pushDeviceMapper;

    @Scheduled(cron="0 0 8 * * ?")
    public void queryPayStatus() {
        String temp_str="";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        temp_str=sdf.format(dt);
        String param=redisService.getStr("taskCuriosity"+temp_str);
        PushMessageParam pushMessageParam=JSONUtils.json2Obj(param,PushMessageParam.class);
        if(pushMessageParam!=null&&pushMessageParam.getPush_time()!=null){
            pushController.addNewsMessages(pushMessageParam);
        }
    }


   /* @Scheduled(cron="10 * * * * ?")
    public void getTemplate(){
        System.out.println("Scheduled   queryUserinfoInsertRedis");

    }*/
    //定时修改redis的用户数据
    @Scheduled(cron="0 0 1 * * ?")
    public void queryUserinfoInsertRedis(){
        logger.info("Scheduled   queryUserinfoInsertRedis");
        rabbitTemplate.convertAndSend("queryUserinfoInsertRedis","");
    }

    /**
     * 定时修改redis中管理员数据
     */
    @Scheduled(cron="0 0 1 * * ?")
    public void queryManagerInfoInsertRedis(){
        logger.info("Scheduled   queryManagerInfoInsertRedis");
        rabbitTemplate.convertAndSend("queryManagerInfoInsertRedis","");
    }
    /*//视频检查
    @Scheduled(cron="0 5 * * * ?")
    public void timeTaskVideoChat(){
        System.out.println("Scheduled   timeTaskVideoChat");
        rabbitTemplate.convertAndSend("timeTaskVideoChat","");
    }*/
    //定时修改es中好奇数据
    @Scheduled(cron="0 0 3 * * ?")
    public void insertESCuriosity(){
        logger.info("Scheduled   insertESCuriosity");
        rabbitTemplate.convertAndSend("insertESCuriosity","");
    }
    //定时修改es中动态数据
    @Scheduled(cron="0 0 4 * * ?")
    public void insertNoteToEs(){
        logger.info("Scheduled   insertNoteToEs");
        rabbitTemplate.convertAndSend("insertNoteToEs","");
    }
    //定时修改es中用户数据
    @Scheduled(cron="0 0 5 * * ?")
    public void insertEsUserInfo(){
        logger.info("Scheduled   insertEsUserInfo");
        rabbitTemplate.convertAndSend("insertEsUserInfo","");
    }
    //会员过期
    @Scheduled(cron="0 0 0 * * ?")
    public void membershipExpired(){
        logger.info("Scheduled   membershipExpired");
        rabbitTemplate.convertAndSend("membershipExpired","");
    }
    //月初会员检查
    @Scheduled(cron="0 0 0 1 * ?")
    public void membermonthly(){
        logger.info("Scheduled   membermonthly");
        rabbitTemplate.convertAndSend("membermonthly","");
    }
   /* public static void main(String[] args) {
        String temp_str="";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        temp_str=sdf.format(dt);
        System.out.println(temp_str);
    }*/
   //定时修改es中消费记录数据
   @Scheduled(cron="0 1 5 * * ?")
   public void insertEsUserOrder(){
       logger.info("Scheduled   insertEsUserOrder");
       rabbitTemplate.convertAndSend("insertEsUserOrder","");
   }
   @Scheduled(cron="0 2 5 * * ?")
   public void insertEsMasterAppointment(){
       logger.info("Scheduled   insertEsMasterAppointment");
       rabbitTemplate.convertAndSend("insertEsMasterAppointment","");
   }

   @Scheduled(cron="0 3 5 * * ?")
   public void insertEsMessageGroup(){
       logger.info("Scheduled   insertEsMessageGroup");
       rabbitTemplate.convertAndSend("insertEsMessageGroup","");
   }

   @Scheduled(cron="0 4 5 * * ?")
   public void insertEsMasterMechanism(){
       logger.info("Scheduled   insertEsMasterMechanism");
       rabbitTemplate.convertAndSend("insertEsMasterMechanism","");
   }

   @Scheduled(cron="0 5 5 * * ?")
   public void insertEsMasterSetPrice(){
       logger.info("Scheduled   insertEsMasterSetPrice");
       rabbitTemplate.convertAndSend("insertEsMasterSetPrice","");
   }

   @Scheduled(cron="0 6 5 * * ?")
   public void insertEsMasterInfo(){
       logger.info("Scheduled   insertEsMasterInfo");
       rabbitTemplate.convertAndSend("insertEsMasterInfo","");
   }
   @Scheduled(cron="0 0 5 * * ?")
   public void updateInviteCode(){
       logger.info("Scheduled   updateInviteCode");
       rabbitTemplate.convertAndSend("updateInviteCode","");
   }

   @Scheduled(cron="0 7 5 * * ?")
   public void updateSettlementCash(){
       logger.info("Scheduled   updateSettlementCash");
       rabbitTemplate.convertAndSend("updateSettlementCash","");
   }


   @Scheduled(cron="0 8 5 * * ?")
   public void updateOnPaymentSettlementCash(){
       logger.info("Scheduled   updateOnPaymentSettlementCash");
       rabbitTemplate.convertAndSend("updateOnPaymentSettlementCash","");
   }

   @Scheduled(cron="0 * * * * ?")
   public void updateInviteCouponCash(){
       logger.info("Scheduled   updateInviteCouponCash");
       rabbitTemplate.convertAndSend("updateInviteCouponCash","");
   }

}
