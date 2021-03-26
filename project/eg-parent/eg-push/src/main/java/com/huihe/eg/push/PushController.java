package com.huihe.eg.push;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.huihe.eg.comm.push.PushMessageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Api(value = "消息推送接口说明", description = "消息推送可用接口说明", tags = {"消息推送"})
@RestController
@RequestMapping("push")
public class PushController {
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private AppPush appPush;
    @Resource
    private RedisService redisService;

    /**
     * 打电话
     *
     * @param param
     * @throws Exception
     */
//    @RabbitListener(queues = "pushVideo")
//    public void pushVideo(PushMessageParam param) {
//         appPush.call(param);
//    }


    /**
     * 身份认证通知
     *
     * @param param
     * @throws Exception
     */
    @RabbitListener(queues = "updateAuthMessage")
    public void updateAuthMessage(PushMessageParam param) throws Exception {
        try {
            param.setTitle("认证消息");
            param.setType("updateAuth");
            logger.info("updateAuthMessage:" + JSONUtils.obj2Json(param).toString());
            appPush.send(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法描述：资产变动个推
     *
     * @param param
     * @author zwx
     * @datetime 日期
     */
    @RabbitListener(queues = "assetsChangeMessage")
    public void assetsChangeMessage(PushMessageParam param) throws Exception {
        try {
            param.setTitle("交易提醒");
            param.setContent(param.getContent());
            logger.info("assetsChangeMessage:" + JSONUtils.obj2Json(param).toString());
            appPush.send(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法描述：交易支付推送
     *
     * @param param
     * @author zwx
     * @datetime 2019年1月17日17:40:23
     */
    @RabbitListener(queues = "payPushMessage")
    public void payPushMessage(PushMessageParam param) throws Exception {
        try {
            param.setTitle("支付消息");
            param.setContent("购买成功，请点击查看。");
            param.setType("payment");
            logger.info("payPushMessage:" + JSONUtils.obj2Json(param).toString());
            appPush.send(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法描述：认证消息推送
     *
     * @param param
     * @author zwx
     * @datetime 2019年1月17日17:40:13
     */
    @RabbitListener(queues = "userAuthMessage")
    public void userAuthMessage(PushMessageParam param) throws Exception {
        try {
            param.setTitle(param.getTitle() + "消息");
            param.setContent(param.getContent());
            //shortMessageSend(param);
            param.setType("updateAuth");
            logger.info("userAuthMessage:" + JSONUtils.obj2Json(param).toString());
            appPush.send(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法描述：消息推送
     *
     * @param param
     * @author zwx
     * @datetime 2019年1月17日17:40:13
     */
    @RabbitListener(queues = "videoPush")
    public void videoPush(PushMessageParam param) throws Exception {
        try {
            appPush.send(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Description: 好奇新回应，回应新评论和评论新回复，动态推送
     *
     * @param param
     * @author： zwx
     * @date: 2019年1月17日18:07:00
     */
    @RabbitListener(queues = "addNewsMessage")
    public void addNewsMessage(PushMessageParam param) throws Exception {
        try {
            String title = "";
            if ("curiosity" .equals(param.getType())) {
                title = "好奇";
            } else if ("note" .equals(param.getType())) {
                title = "动态";
            } else if ("authentication" .equals(param.getType())) {
                title = "认证";
            } else if ("pay" .equals(param.getType())) {
                title = "交易";
            } else if ("updateAuth" .equals(param.getType())) {
                title = "审核";
            } else if ("gift" .equals(param.getType())) {
                title = "礼物";
            } else if ("redenvelopes" .equals(param.getType())) {
                title = "红包";
            } else if ("add_friends" .equals(param.getType())) {
                title = "添加好友";
            } else {
                title = "通知";
            }
            if (param.getOpera_type() != null) {
                if ("chatting" .equalsIgnoreCase(param.getPush_type())) {
                    param.setContent("购买成功");
                }
                if ("add_friends" .equalsIgnoreCase(param.getPush_type())) {
                }
                if ("redenvelopes" .equalsIgnoreCase(param.getPush_type())) {
                    JSONObject map = JSONUtils.obj2Json(redisService.getStr(param.getSend_id() + "userinfo"));
                    if (map.containsKey("nick_name") && StringUtil.isEmpty(map.get("nick_name").toString())) {
                        param.setContent(map.get("nick_name").toString() + "送给您的红包已到账");
                    }
                }
                if ("gift" .equalsIgnoreCase(param.getPush_type())) {
                    JSONObject map = JSONUtils.obj2Json(redisService.getStr(param.getSend_id() + "userinfo"));
                    if (map.containsKey("nick_name") && StringUtil.isEmpty(map.get("nick_name").toString())) {
                        param.setContent(map.get("nick_name").toString() + "送您礼物了");
                    }
                }
                if ("like" .equalsIgnoreCase(param.getPush_type())) {
                    JSONObject map = JSONUtils.obj2Json(redisService.getStr(param.getSend_id() + "userinfo"));
                    if (map.containsKey("nick_name") && StringUtil.isEmpty(map.get("nick_name").toString())) {
                        param.setContent(map.get("nick_name").toString() + "赞了你");
                    }
                }
                if ("addNoteComment" .equalsIgnoreCase(param.getOpera_type())) {
                    JSONObject map = JSONUtils.obj2Json(redisService.getStr(param.getSend_id() + "userinfo"));
                    if (map.containsKey("nick_name") && StringUtil.isEmpty(map.get("nick_name").toString())) {
                        param.setContent(map.get("nick_name").toString() + "评论了你的动态,请点击查看.");
                    }
                }
                if ("addNoteCommentSub" .equalsIgnoreCase(param.getOpera_type())) {
                    JSONObject map = JSONUtils.obj2Json(redisService.getStr(param.getSend_id() + "userinfo"));
                    if (map.containsKey("nick_name") && StringUtil.isEmpty(map.get("nick_name").toString())) {
                        param.setContent(map.get("nick_name").toString() + "回复了你的评论,请点击查看.");
                    }
                }
                if ("addViewpoint" .equalsIgnoreCase(param.getOpera_type())) {
                    JSONObject map = JSONUtils.obj2Json(redisService.getStr(param.getSend_id() + "userinfo"));
                    if (map.containsKey("nick_name") && StringUtil.isEmpty(map.get("nick_name").toString())) {
                        param.setContent(map.get("nick_name").toString() + "对你的好奇发布了新观点,请点击查看.");
                    }
                }
                if ("addViewpointComment" .equalsIgnoreCase(param.getOpera_type())) {
                    JSONObject map = JSONUtils.obj2Json(redisService.getStr(param.getSend_id() + "userinfo"));
                    if (map.containsKey("nick_name") && StringUtil.isEmpty(map.get("nick_name").toString())) {
                        param.setContent(map.get("nick_name").toString() + "评论了你的回应,请点击查看.");
                    }
                }
                if ("addViewpointSubComment" .equalsIgnoreCase(param.getOpera_type())) {
                    JSONObject map = JSONUtils.obj2Json(redisService.getStr(param.getSend_id() + "userinfo"));
                    if (map.containsKey("nick_name") && StringUtil.isEmpty(map.get("nick_name").toString())) {
                        param.setContent(map.get("nick_name").toString() + "回复了你的评论,请点击查看.");
                    }
                }
                if (StringUtil.isEmpty(param.getContent())) {
                    param.setContent("你有新的" + title + "消息,点击查看.");
                }
            }
            logger.info("addNewsMessage:" + JSONUtils.obj2Json(param).toString());
            param.setTitle(title + "消息");
            appPush.send(param);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 推送
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(value = "推送", httpMethod = "POST", notes = "推送")
    @PostMapping({"pushNewsMessages"})
    public void pushNewsMessages(PushMessageParam param) throws Exception {
        try {
            if (param.getContent() == null || param.getContent().equals("")) {
                param.setContent("您有新的消息,请点击查看");
            }
            logger.info("pushNewsMessages:" + JSONUtils.obj2Json(param).toString());
            appPush.send(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/29 13:32
     * @ Description：综合消息推送
     * @ since: JDk1.8
     */
    @RabbitListener(queues = "classNewsMessage")
    public void classNewsMessage(PushMessageParam param) throws Exception {
        try {
            if (StringUtil.isEmpty(param.getTitle())){
                param.setTitle("课程提醒");
            }
            if (StringUtil.isEmpty(param.getContent())) {
                param.setContent("您有新的消息,请点击查看");
            }
            System.out.println();
            logger.info("classNewsMessage:" + JSONUtils.obj2Json(param).toString() +"date :"+new Date());
            appPush.send(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "classStartMessage")
    public void classStartMessage(PushMessageParam param) throws Exception {
        try {
            param.setTitle("课程提醒");
            if (param.getContent() == null || param.getContent().equals("")) {
                param.setContent("您有新的消息,请点击查看");
            }
            System.out.println(new Date());
            logger.info("classNewsMessage:" + JSONUtils.obj2Json(param).toString());
            appPush.send(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 后台查询推送
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(value = "后台推送", httpMethod = "POST", notes = "后台推送")
    @PostMapping({"addNewsMessages"})
    public void addNewsMessages(PushMessageParam param) {
        String json = "";
        if (param != null) {
            //json= JSONUtils.obj2Json(param).toString();
        }
        String temp_str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        temp_str = sdf.format(param.getPush_time());
        logger.info("addNewsMessages:" + JSONUtils.obj2Json(param).toString());
        redisService.set("taskCuriosity" + temp_str, json);


    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/21 19:06
     * @ Description：systemUpdate
     * @ since: JDk1.8
     */
    @ApiOperation(value = "版本升级推送", httpMethod = "POST", notes = "版本升级推送")
    @RabbitListener(queues = "systemUpdate")
    public void systemUpdate(PushMessageParam param) {
        try {
            System.out.println(new Date());
            logger.info("systemUpdate:" + JSONUtils.obj2Json(param).toString());
            appPush.systemUpdate(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 冻结通知
     *
     * @author : zwy
     * 2020-07-31 02:21
     * @since JDK1.8
     */
    @RabbitListener(queues = "frozenStatusMessage")
    public void frozenStatusMessage(PushMessageParam param) throws Exception {
        try {
            param.setTitle("冻结通知");
            param.setType("frozenStatus");
            logger.info("frozenStatusMessage:" + JSONUtils.obj2Json(param).toString());
            appPush.send(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
