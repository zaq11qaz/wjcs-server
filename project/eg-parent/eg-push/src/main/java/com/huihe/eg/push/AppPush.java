package com.huihe.eg.push;

import com.alibaba.fastjson.JSON;
import com.cy.framework.util.CommonUtils;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.json.JsonUtilFastjson;
import com.gexin.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IPushResult;

import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.VoIPPayload;
import com.gexin.rp.sdk.exceptions.PushSingleException;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.push.model.MyMobPushConfig;
import com.huihe.eg.push.model.MyMobPushConfigTeachPay;
import com.huihe.eg.push.model.PushConfigParam;

import java.io.File;
import java.lang.reflect.Field;

import com.huihe.eg.push.model.PushDeviceEntity;
import com.huihe.eg.push.mybatis.PushDeviceMapper;
import com.turo.pushy.apns.*;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;
import com.turo.pushy.apns.util.concurrent.PushNotificationFuture;
import mob.push.api.http.Http;
import mob.push.api.http.StringResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.turo.pushy.apns.PushType.VOIP;

@Service
public class AppPush {
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    private IGtPush push = null;

    @Resource
    private PushConfigParam pushConfigParam;
    @Resource
    private PushDeviceMapper pushDeviceMapper;
    @Resource
    private MyMobPushConfig myMobPushConfig;
    @Resource
    private MyMobPushConfigTeachPay myMobPushConfigTeachPay;

    /**
     * ????????????: ??????
     *
     * @param param
     * @author yangchengfu
     * @datatime 2017-2-18 ??????11:50:12
     */
    public void send(PushMessageParam param) {
        if(1!=1) {
        /*
        if (param.getIs_teach_pay()){
            push = new IGtPush(pushConfigParam.getUrl(),
                    pushConfigParam.getTechpayAppID(), pushConfigParam.getTechpayAppSecret());
        }else {
            push = new IGtPush(pushConfigParam.getUrl(),
                    pushConfigParam.getAppKey(), pushConfigParam.getMasterSecret());
        }

         */
            push = new IGtPush(pushConfigParam.getUrl(),
                    pushConfigParam.getTechpayAppID(), pushConfigParam.getTechpayAppSecret());
            PushDeviceEntity t = new PushDeviceEntity();
            if (param.getTarget_ids() != null && param.getTarget_ids().size() > 0) {
                for (long l : param.getTarget_ids()) {
                    t.setUser_id(l);
                    t.setPageSize(1);
                    List<PushDeviceEntity> deviceParams = pushDeviceMapper.queryListPage(t);
                    if (deviceParams != null && deviceParams.size() > 0) {
                        PushDeviceEntity deviceParam = deviceParams.get(0);
                        if ("ios".equalsIgnoreCase(deviceParam.getName())) {
                            if (param.getType().equals("video")) {
                                this.call(param);
                                logger.info(JSONUtils.obj2Json(deviceParam).toString());
                                if (StringUtils.isNotEmpty(deviceParam.getRid())) {
                                    this.pushMessageToOneNew(param, deviceParam.getRid(), deviceParam.getName());
                                } else {
                                    ios(param, deviceParam.getClientid(), deviceParam.getToken());
                                }
                            }
                        } else {
                            if (StringUtils.isNotEmpty(deviceParam.getRid())) {
                                this.pushMessageToOneNew(param, deviceParam.getRid(), deviceParam.getName());
                            } else {
                                pushMessageToOne(param, deviceParam.getClientid(), deviceParam.getName());//
                            }
                        }
                    } else {
                        logger.warn("userDevice is null,user_id:" + param.getTarget_id());
                    }
                }
            } else {
                t.setUser_id(param.getTarget_id());
                t.setPageSize(1);
                List<PushDeviceEntity> deviceParams = pushDeviceMapper.queryListPage(t);
                if (deviceParams != null && deviceParams.size() > 0) {
                    PushDeviceEntity deviceParam = deviceParams.get(0);
                    if ("ios".equalsIgnoreCase(deviceParam.getName())) {
                        if (param.getType().equals("video")) {
                            this.call(param);
                        } else {
                            if (StringUtils.isNotEmpty(deviceParam.getRid())) {
                                this.pushMessageToOneNew(param, deviceParam.getRid(), deviceParam.getName());
                            } else {
                                ios(param, deviceParam.getClientid(), deviceParam.getToken());
                            }
                        }
                    } else {
                        if (StringUtils.isNotEmpty(deviceParam.getRid())) {
                            this.pushMessageToOneNew(param, deviceParam.getRid(), deviceParam.getName());
                        } else {
                            pushMessageToOne(param, deviceParam.getClientid(), deviceParam.getName());
                        }
                    }
                } else {
                    logger.warn("userDevice is null,user_id:" + param.getTarget_id());
                }
            }
        }

    }


    private void pushMessageToOneNew(PushMessageParam param, String rid, String name) {
        try {
            if (param.getIs_teach_paypal()==null || !param.getIs_teach_paypal()) {
                myMobPushConfig.init();
                HashMap<String, Object> map = Maps.newHashMap();
                String string = CommonUtils.generateFlowingCode();
                map.put("workno", string);
                map.put("appkey", myMobPushConfig.getAppKey());
                HashMap<String, Object> pushTargetMap = Maps.newHashMap();
                pushTargetMap.put("target", "4");
                pushTargetMap.put("rids", new String[]{rid});
                map.put("pushTarget", pushTargetMap);

                HashMap<String, Object> pushNotifyMap = Maps.newHashMap();
                pushNotifyMap.put("plats", new int[]{1, 2});
                pushNotifyMap.put("title", param.getTitle());
                pushNotifyMap.put("content", param.getContent());
                pushNotifyMap.put("iosProduction", "0");
                pushNotifyMap.put("offlineSeconds", 60 * 60 * 24 * 7);

                if ("video".equals(param.getType()) && "class".equals(param.getOpera_type())) {
                    pushNotifyMap.put("type", "2");
                    Map<String, Object> androidNotifyMap = Maps.newHashMap();
                    androidNotifyMap.put("warn", 123);
                    androidNotifyMap.put("sound", 1);
                    pushNotifyMap.put("androidNotify", androidNotifyMap);
                } else {
                    pushNotifyMap.put("type", "1");
                }

                HashMap<String, Object> extrasMap = Maps.newHashMap();
                extrasMap.put("key", "pushMessageParam");

                String str = JSONObject.toJSONString(param);
                extrasMap.put("value", str);

                List<Map<String, Object>> extrasMapList = Lists.newArrayList();
                extrasMapList.add(extrasMap);
                pushNotifyMap.put("extrasMapList", extrasMapList);
                map.put("pushNotify", pushNotifyMap);

                String mapString = JSON.toJSONString(map);
                StringResult result = Http.post(myMobPushConfig.getBaseUrl(), null, mapString);
                System.out.println("result :" + result.toString());
            }else {
                myMobPushConfigTeachPay.init();
                HashMap<String, Object> map = Maps.newHashMap();
                String string = CommonUtils.generateFlowingCode();
                map.put("workno", string);
                map.put("appkey", myMobPushConfigTeachPay.getAppKey());
                HashMap<String, Object> pushTargetMap = Maps.newHashMap();
                pushTargetMap.put("target", "4");
                pushTargetMap.put("rids", new String[]{rid});
                map.put("pushTarget", pushTargetMap);

                HashMap<String, Object> pushNotifyMap = Maps.newHashMap();
                pushNotifyMap.put("plats", new int[]{1, 2});
                pushNotifyMap.put("title", param.getTitle());
                pushNotifyMap.put("content", param.getContent());
                pushNotifyMap.put("iosProduction", "0");
                pushNotifyMap.put("offlineSeconds", 60 * 60 * 24 * 7);

                if ("video".equals(param.getType()) && "class".equals(param.getOpera_type())) {
                    pushNotifyMap.put("type", "2");
                    Map<String, Object> androidNotifyMap = Maps.newHashMap();
                    androidNotifyMap.put("warn", 123);
                    androidNotifyMap.put("sound", 1);
                    pushNotifyMap.put("androidNotify", androidNotifyMap);
                } else {
                    pushNotifyMap.put("type", "1");
                }

                HashMap<String, Object> extrasMap = Maps.newHashMap();
                extrasMap.put("key", "pushMessageParam");

                String str = JSONObject.toJSONString(param);
                extrasMap.put("value", str);

                List<Map<String, Object>> extrasMapList = Lists.newArrayList();
                extrasMapList.add(extrasMap);
                pushNotifyMap.put("extrasMapList", extrasMapList);
                map.put("pushNotify", pushNotifyMap);

                String mapString = JSON.toJSONString(map);
                StringResult result = Http.post(myMobPushConfigTeachPay.getBaseUrl(), null, mapString);
                System.out.println("result :" + result.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("new pushMessage error :" + param);
        }
    }

    @SuppressWarnings("deprecation")
    public NotificationTemplate android(PushMessageParam param, String clientid) {
        try {
            logger.info("clientid:" + clientid);
            NotificationTemplate template = new NotificationTemplate();
            template.setAppId(pushConfigParam.getAppID());
            template.setAppkey(pushConfigParam.getAppKey());
            template.setTitle(param.getTitle());
            template.setText(param.getContent());
            template.setLogo("icon.png");
            // ???????????????????????????????????????????????????
            template.setIsRing(true);
            template.setIsVibrate(true);
            template.setIsClearable(true);
            SingleMessage message = new SingleMessage();
            message.setPushNetWorkType(param.getNew_work_type());
            message.setOffline(true);
            net.sf.json.JSONObject json = null;
            if (param.getMap() != null && !param.getMap().isEmpty()) {
                json = JSONUtils.obj2Json(param.getMap());
                json.put("type", param.getType());
                template.setTransmissionContent(json.toString());
            } else {
                json = new net.sf.json.JSONObject();
                json.put("type", param.getType());
            }
            IPushResult result = null;
            Target target = new Target();
            target.setAppId(pushConfigParam.getAppID());
            target.setClientId(clientid);
            template.setTransmissionType(2);
            message.setData(template);
            result = push.pushMessageToSingle(message, target);
            logger.warn("android push result:" + result.getResponse().toString()
                    + ",clientId:" + target.getClientId().toString());
        } catch (PushSingleException e) {
            logger.warn("android push Exception:" + e.toString());

        }
        return null;
    }

    /**
     * ??????????????????
     */
    public TransmissionTemplate getTemplateWithoutAlert(PushMessageParam pushMessage, String platform) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(pushConfigParam.getAppID());
        template.setAppkey(pushConfigParam.getAppKey());
        getDictionaryAlertMsg(pushMessage);
        if ("android".equalsIgnoreCase(platform)) {
            template.setTransmissionContent(JSONUtils.obj2Json(pushMessage).toString());
        } else {
            template.setTransmissionContent(pushMessage.getContent());
        }
        template.setTransmissionType(2);
        VoIPPayload payload1 = new VoIPPayload();
        JSONObject jo = new JSONObject();
        Map<String, Object> map = null;
        try {
            map = objectToMap(pushMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*if (param.getMap() != null && !param.getMap().isEmpty()) {*/
        for (String key : map.keySet()) {
            jo.put(key, map.get(key));
        }
        payload1.setVoIPPayload(jo.toString());
        template.setAPNInfo(payload1);
        return template;
    }


    /**
     * ios???android????????????
     */
    public TransmissionTemplate getTemplate(PushMessageParam pushMessage, String platform) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(pushConfigParam.getAppID());
        template.setAppkey(pushConfigParam.getAppKey());
        getDictionaryAlertMsg(pushMessage);
        if ("android".equalsIgnoreCase(platform)) {
            template.setTransmissionContent(JSONUtils.obj2Json(pushMessage).toString());
        } else {
            template.setTransmissionContent(pushMessage.getContent());
        }
        template.setTransmissionType(2);
        APNPayload payload = new APNPayload();
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setTitle(pushMessage.getTitle());
        alertMsg.setBody(pushMessage.getContent());
        payload.setAlertMsg(alertMsg);
        //ios????????????
        template.setAPNInfo(payload);
        return template;
    }

    /**
     * ???????????????ios??????????????? ???????????????????????????????????????????????????
     *
     * @param pushMessage
     * @param
     */
    public void pushMessageToOne(PushMessageParam pushMessage, String cid, String platform) {
        TransmissionTemplate template;
        if ("video".equals(pushMessage.getType()) && "chat".equals(pushMessage.getOpera_type())) {
            template = getTemplateWithoutAlert(pushMessage, platform);
        } else {
            template = getTemplate(pushMessage, platform);
        }
        Style0 style = new Style0();
        // ??????????????????????????????
        style.setTitle(pushMessage.getTitle());
        style.setText(pushMessage.getContent());
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        style.setChannelLevel(3); //???????????????????????????

        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // ?????????????????????????????????????????????
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // ?????????1???wifi???0?????????????????????????????????????????????????????????????????????????????????
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(pushConfigParam.getAppID());
        target.setClientId(cid); //??????cid?????????
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
            logger.info(ret.getResponse().toString());
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
            logger.info(ret.getResponse().toString());
        }

    }

    /**
     * ios??????APNS??????
     */
    private static void getDictionaryAlertMsg(PushMessageParam pushMessage) {
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody(pushMessage.getContent());
        alertMsg.setTitle(pushMessage.getTitle());
    }


    /**
     * ????????????: ios ??????
     *
     * @param param
     * @author yangchengfu
     * @datatime 2017-2-18 ??????11:07:38
     */
    public void ios(PushMessageParam param, String cid, String token) {
        try {
            APNPayload apnpayload = new APNPayload();
            Map<String, Object> map = JSONUtils.obj2Map(param, null);
            for (String key : map.keySet()) {
                apnpayload.addCustomMsg(key, map.get(key));
            }

            APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
            alertMsg.setBody(param.getContent());
            alertMsg.setTitle(param.getTitle());
            apnpayload.setAlertMsg(alertMsg);

            NotificationTemplate template = new NotificationTemplate();
            template.setAppId(pushConfigParam.getAppID());
            template.setAppkey(pushConfigParam.getAppKey());
            template.setAPNInfo(apnpayload);
            Style0 style = new Style0();
//             ??????????????????????????????
            style.setTitle(param.getTitle());
            style.setText(param.getContent());
//             ???????????????????????????????????????????????????
            style.setRing(true);
            style.setVibrate(true);
            style.setClearable(true);
            style.setChannelLevel(3); //???????????????????????????
            template.setStyle(style);
            template.setTransmissionType(1);  // ?????????????????????????????????1???????????????APP???2?????????????????????????????????????????????
            template.setTransmissionContent(param.getContent());

            SingleMessage message = new SingleMessage();
            message.setOffline(true);
            // ????????????????????????????????????
            message.setOfflineExpireTime(24 * 360 * 1000);
            // ?????????1???wifi???0?????????????????????????????????????????????????????????????????????????????????
            message.setPushNetWorkType(0);
            System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
            // ????????????????????????
            message.setStrategyJson("{\"default\":1,\"ios\":2}");
            message.setData(template);

            Target target = new Target();
            target.setClientId(cid);
            target.setAppId(pushConfigParam.getAppID());

            IPushResult ret = push.pushMessageToSingle(message, target);
            if (ret != null) {
                logger.info(ret.getResponse().toString());
            } else {
                logger.warn("?????????????????????");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("ios:" + JSONUtils.obj2Json(param).toString() + "token:" + token);
        }
    }


    /**
     * ??????null?????????
     *
     * @param requestParameters
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object requestParameters) throws IllegalAccessException {

        Map<String, Object> map = new HashMap<>();
        // ??????f????????????????????????????????????
        Field[] fields = requestParameters.getClass().getDeclaredFields();
        for (Field field : fields) {
            String varName = field.getName();
            // ?????????????????????????????????
            boolean accessFlag = field.isAccessible();
            // ????????????????????????
            field.setAccessible(true);
            // ???????????????f?????????fields[i]???????????????????????????
            Object o = field.get(requestParameters);
            if (o != null && StringUtils.isNotBlank(o.toString().trim())) {
                map.put(varName, o.toString().trim());
                // ????????????????????????
                field.setAccessible(accessFlag);
            }
        }
        return map;
    }

    public void call(PushMessageParam param) {
        try {
            PushDeviceEntity pushDeviceEntity = new PushDeviceEntity();
            pushDeviceEntity.setUser_id(param.getTarget_id());
            pushDeviceEntity.setPageSize(1);
            pushDeviceEntity = pushDeviceMapper.queryListPage(pushDeviceEntity).get(0);
            //IOS?????????????????????????????????DeviceToken
            String deviceToken = pushDeviceEntity.getVoip_token();
            String topic = pushConfigParam.getTopic();

            HashMap<String, Object> map = new HashMap<>();
            HashMap<String, Object> aps = new HashMap<>();
            aps.put("alert", param.getTitle());
            aps.put("sound", "default");
            aps.put("badge", 1);
            map.put("aps", aps);
            map.put("content", param.getContent());
            String payload = JsonUtilFastjson.toJSONString(map, (String) null);
//            String payload = "{ \"aps\" : {\"alert\" : \"??????\", \"sound\" : \"default\", \"badge\" :1},\"liguoxin\":\"liguoxin\" }";
            //????????????
            Date invalidationTime = new Date(System.currentTimeMillis() + 60 * 60 * 1000L);
            //???????????? apns-priority 10????????? 5?????????
            DeliveryPriority priority = DeliveryPriority.IMMEDIATE;
            //????????????????????????alert???background???voip???complication???fileprovider???mdm
            PushType pushType = VOIP;
            //???????????????ID???????????? apns-collapse-id??????App?????????
            String collapseId = UUID.randomUUID().toString();
            //apnsId ??????????????????????????????APNs????????????????????????
            UUID apnsId = UUID.randomUUID();
            //????????????APNs?????????????????????
            SimpleApnsPushNotification msg = new SimpleApnsPushNotification(deviceToken, topic, payload, invalidationTime, priority, pushType, collapseId, apnsId);
            String SANDBOX_APNS_HOST = pushConfigParam.getSandBoxApnsHost();

            ApnsClient apnsClient = new ApnsClientBuilder().setApnsServer(SANDBOX_APNS_HOST)
                    .setClientCredentials(new File(pushConfigParam.getPushSecret()), pushConfigParam.getPassWord())
                    .build();
            //????????????
            PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>> future = apnsClient.sendNotification(msg);
            PushNotificationResponse<SimpleApnsPushNotification> response = future.get();

            System.out.println(response.getRejectionReason());
            //????????????????????????success???true??????????????????????????????
            //???????????????????????????rejectionReason???????????????????????????????????????????????????????????????
            //https://developer.apple.com/documentation/usernotifications/setting_up_a_remote_notification_server/handling_notification_responses_from_apns?language=objc

            System.out.println("------------->" + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void systemUpdate(PushMessageParam param) {
        push = new IGtPush(pushConfigParam.getUrl(),
                pushConfigParam.getTechpayAppID(), pushConfigParam.getTechpayAppSecret());
        PushDeviceEntity t = new PushDeviceEntity();
        try {
            myMobPushConfig.init();
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("workno", CommonUtils.generateFlowingCode());
            map.put("appkey", myMobPushConfig.getAppKey());

            HashMap<String, Object> pushTargetMap = Maps.newHashMap();
            pushTargetMap.put("target", "1");
            map.put("pushTarget", pushTargetMap);
            HashMap<String, Object> pushNotifyMap = Maps.newHashMap();
            pushNotifyMap.put("plats", new int[]{Integer.parseInt(param.getSource())});
            pushNotifyMap.put("title", param.getTitle());
            pushNotifyMap.put("content", param.getContent());
            pushNotifyMap.put("iosProduction", "0");
            pushNotifyMap.put("offlineSeconds", 60 * 60 * 24 * 7);
            pushNotifyMap.put("type", "1");
            map.put("pushNotify", pushNotifyMap);

            String mapString = JSON.toJSONString(map);
            StringResult result = Http.post(myMobPushConfig.getBaseUrl(), null, mapString);
//            System.out.println("result :" + result.toString());
            logger.warn("systemUpdate :" + param);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("systemUpdate error :" + param);
        }
    }
}

