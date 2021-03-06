package com.huihe.eg.message.service.impl.callback;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.util.http.HttpsClientRequest;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.safe.MD5Util;
import com.cy.framework.util.thread.ThreadPool;
import com.huihe.eg.cloud.feign.UserApiService;
import com.huihe.eg.message.model.*;
import com.huihe.eg.message.mybatis.dao.*;
import com.huihe.eg.message.service.dao.MessageGroupFlowService;
import com.huihe.eg.message.service.dao.MessageGroupService;
import com.huihe.eg.message.service.dao.callback.MessageCallbackFactoryBean;
import com.huihe.eg.message.service.impl.Thread.MixedFlowThread;
import com.huihe.eg.message.service.impl.tim.TimConfig;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MessageCallbackFactoryImpl implements MessageCallbackFactoryBean {

    public org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MessageUserMapper messageUserMapper;
    @Resource
    private MessageContentMapper messageContentMapper;
    @Resource
    private MessageGroupMapper messageGroupMapper;
    @Resource
    private MessageGroupService messageGroupService;
    @Resource
    private MessageJoinGroupMapper messageJoinGroupMapper;
    @Resource
    private MessageFriendMapper messageFriendMapper;
    @Resource
    private MessageBlacklistMapper blacklistMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private RedisService redisService;
    @Resource
    private MessageGroupFlowMapper messageGroupFlowMapper;
    @Resource
    private TimConfig timConfig;
    @Resource
    private MessageGroupFlowService messageGroupFlowService;
    @Resource
    private GroupRecordingMapper groupRecordingMapper;
    @Resource
    private GroupVideoMapper groupVideoMapper;
    @Resource
    private UserApiService userApiService;
    /**
     * ???????????????????????????
     * 2019???1???9???
     *
     * @param map zwx
     */
    public void C2CCallbackBeforeSendMsg(Map<String, Object> map) {

    }

    /**
     * ???????????????????????????
     * 2019???1???9???
     *
     * @param map zwx
     */
    public TIMResultEntity C2CCallbackAfterSendMsg(Map<String, Object> map) {
        int ret = 0;
        TIMResultEntity timResultEntity = new TIMResultEntity();
        try {
            MessageUserEntity messageUserEntity = new MessageUserEntity();
            messageUserEntity.setSend_id(Long.parseLong(map.get("From_Account").toString()));
            messageUserEntity.setTarget_id(Long.parseLong(map.get("To_Account").toString()));
            messageUserEntity.setStatus(1);
            List<MessageUserEntity> messageUserEntities = messageUserMapper.query(messageUserEntity);
            if (messageUserEntities == null || messageUserEntities.size() == 0) {
                messageUserEntity.setMessage_type("personal");
                ret = messageUserMapper.insert(messageUserEntity);
            } else {
                messageUserEntity = messageUserEntities.get(0);
            }
            MessageContentEntity entity = new MessageContentEntity();
            entity.setMessage_id(messageUserEntity.getId());
            List<Map<String, Object>> mapMsgBody = JSONUtils.json2MapList(map.get("MsgBody").toString());
            if (mapMsgBody.size() > 1) {
                entity.setContent_type("TIMCombination");//????????????1?????????????????????
            } else {
                entity.setContent_type(mapMsgBody.get(0).get("MsgType").toString());
            }
            //entity.setContent(mapMsgBody.toString().replaceAll("\\\\",""));
            ret = messageContentMapper.insert(entity);
            if (ret < 1) {
                timResultEntity.setActionStatus("FAIL");
                timResultEntity.setErrorCode(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return timResultEntity;
    }

    /**
     * ????????????????????????
     * 2019???1???9???
     *
     * @param map zwx
     */
    public void GroupCallbackBeforeSendMsg(Map<String, Object> map) {

    }

    /**
     * ????????????????????????
     * 2019???1???9???
     *
     * @param map
     * @auther zwx
     */
    public TIMResultEntity GroupCallbackAfterSendMsg(Map<String, Object> map) {
        int ret = 0;
        TIMResultEntity timResultEntity = new TIMResultEntity();
        try {
            String groupId = map.get("GroupId").toString();
            MessageGroupEntity messageGroupEntity = new MessageGroupEntity();
            messageGroupEntity.setGroup_id(groupId);
            List<MessageGroupEntity> entities = messageGroupMapper.query(messageGroupEntity);
            if (entities != null && entities.size() > 0) {
                MessageUserEntity messageUserEntity = new MessageUserEntity();
                messageUserEntity.setSend_id(Long.parseLong(map.get("From_Account").toString()));
                messageUserEntity.setTarget_id(entities.get(0).getId());
                messageUserEntity.setStatus(1);
                List<MessageUserEntity> messageUserEntities = messageUserMapper.query(messageUserEntity);
                MessageContentEntity entity = new MessageContentEntity();
                if (messageUserEntities == null || messageUserEntities.size() == 0) {
                    messageUserEntity.setMessage_type("groupchat");
                    ret = messageUserMapper.insertSelective(messageUserEntity);
                    entity.setMessage_id(messageUserEntity.getId());
                } else {
                    entity.setMessage_id(messageUserEntities.get(0).getId());
                }
                List<Map<String, Object>> mapMsgBody = JSONUtils.json2MapList(map.get("MsgBody").toString());
                if (mapMsgBody.size() > 1) {
                    entity.setContent_type("TIMCombination");//????????????1?????????????????????
                } else {
                    entity.setContent_type(mapMsgBody.get(0).get("MsgType").toString());
                }
                entity.setContent(mapMsgBody.toString());
                entity.setMessage_seq((int) map.get("MsgSeq"));
                ret = messageContentMapper.insert(entity);
            }
            if (ret < 1) {
                timResultEntity.setActionStatus("FAIL");
                timResultEntity.setErrorCode(1);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return timResultEntity;

    }

    /**
     * ????????????????????????
     * 2019???1???9???
     *
     * @param map zwx
     */
    public void GroupCallbackBeforeCreateGroup(Map<String, Object> map) {
        logger.info("GroupCallbackBeforeCreateGroup");
    }

    /**
     * ????????????????????????
     * 2019???1???9???
     *
     * @param map zwx
     */
    public TIMResultEntity GroupCallbackAfterCreateGroup(Map<String, Object> map) {
        int ret = 0;
        TIMResultEntity timResultEntity = new TIMResultEntity();
        try {
            MessageGroupEntity messageGroupEntity = new MessageGroupEntity();
            messageGroupEntity.setGroup_id(map.get("GroupId").toString());
            messageGroupEntity.setGroup_name(map.get("Name").toString());
            //messageGroupEntity.setFaceUrl();
            messageGroupEntity.setGroup_type(map.get("Type").toString());
            messageGroupEntity.setOwner_id(Long.parseLong(map.get("Owner_Account").toString()));
            ret = messageGroupMapper.insertSelective(messageGroupEntity);
            if (ret > 0) {
                List<Map<String, Object>> mapMsgBody = JSONUtils.json2MapList(map.get("MemberList").toString());
                for (Map<String, Object> map1 : mapMsgBody) {
                    MessageJoinGroupEntity entity = new MessageJoinGroupEntity();
                    entity.setGroup_id(messageGroupEntity.getId());
                    entity.setUser_id(Long.parseLong(map1.get("Member_Account").toString()));
                    entity.setOperator_account(messageGroupEntity.getOperator_account());
                    entity.setJoin_type("Invited");
                    ret = messageJoinGroupMapper.insertSelective(entity);
                }
            }
            if (ret < 1) {
                timResultEntity.setActionStatus("FAIL");
                timResultEntity.setErrorCode(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timResultEntity;
    }

    /**
     * ????????????????????????
     * 2019???1???9???
     *
     * @param map zwx
     */
    public TIMResultEntity GroupCallbackBeforeApplyJoinGroup(Map<String, Object> map) {
        int ret = 0;
        TIMResultEntity timResultEntity = new TIMResultEntity();
        try {
            MessageGroupEntity messageGroupEntity = new MessageGroupEntity();
            messageGroupEntity.setGroup_id(map.get("GroupId").toString());
            List<MessageGroupEntity> entities = messageGroupMapper.query(messageGroupEntity);
            if (entities != null && entities.size() > 0) {
                if (entities.get(0).getStatus() == 3 || entities.get(0).getStatus() == 2) {
                    timResultEntity.setErrorCode(1);
                    return timResultEntity;
                }
                //List<Map<String,Object>> mapMsgBody=JSONUtils.json2MapList(map.get("DestinationMembers").toString());
                MessageJoinGroupEntity entity = new MessageJoinGroupEntity();
                entity.setGroup_id(entities.get(0).getId());
                entity.setUser_id(map.get("Requestor_Account").toString().equals("") ?0:Long.parseLong(map.get("Requestor_Account").toString()));
                List<MessageJoinGroupEntity> list = messageJoinGroupMapper.queryListPage(entity);
                entity.setOperator_account(messageGroupEntity.getOperator_account());
                entity.setJoin_type("Apply");
                if (list != null && list.size() > 0) {
                    entity.setId(list.get(0).getId());
                    ret = messageJoinGroupMapper.updateByPrimaryKeySelective(entity);
                } else {
                    ret = messageJoinGroupMapper.insertSelective(entity);
                }
            }
            if (ret < 1) {
                timResultEntity.setErrorCode(1);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return timResultEntity;
    }

    /**
     * ????????????????????????
     * 2019???1???9???
     *
     * @param map zwx
     */
    public TIMResultEntity GroupCallbackBeforeInviteJoinGroup(Map<String, Object> map) {
        int ret = 0;
        TIMResultEntity timResultEntity = new TIMResultEntity();
        try {
            MessageGroupEntity messageGroupEntity = new MessageGroupEntity();
            messageGroupEntity.setGroup_id(map.get("GroupId").toString());
            List<MessageGroupEntity> entities = messageGroupMapper.query(messageGroupEntity);
            List<Map<String, Object>> mapMsgBody = JSONUtils.json2MapList(map.get("DestinationMembers").toString());
            if (entities != null && entities.size() > 0) {
                if (entities.get(0).getStatus() == 3 || entities.get(0).getStatus() == 2) {
                    timResultEntity.setErrorCode(1);
                    return timResultEntity;
                }
                for (Map<String, Object> map1 : mapMsgBody
                        ) {
                    MessageJoinGroupEntity entity = new MessageJoinGroupEntity();
                    entity.setGroup_id(entities.get(0).getId());
                    entity.setUser_id(Long.parseLong(map1.get("Member_Account").toString()));
                    List<MessageJoinGroupEntity> list = messageJoinGroupMapper.queryListPage(entity);
                    entity.setOperator_account(map.get("Operator_Account").toString());
                    entity.setJoin_type("Invited");
                    if (list != null && list.size() > 0) {
                        entity.setId(list.get(0).getId());
                        ret = messageJoinGroupMapper.updateByPrimaryKeySelective(entity);
                    } else {
                        ret = messageJoinGroupMapper.insertSelective(entity);
                    }
                }
            }
            if (ret < 1) {
                timResultEntity.setErrorCode(1);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return timResultEntity;
    }

    /**
     * ???????????????????????????
     * 2019???1???9???
     *
     * @param map zwx
     */
    public TIMResultEntity GroupCallbackAfterNewMemberJoin(Map<String, Object> map) {
        int ret = 0;
        TIMResultEntity timResultEntity = new TIMResultEntity();
        try {
            MessageGroupEntity messageGroupEntity = new MessageGroupEntity();
            messageGroupEntity.setGroup_id(map.get("GroupId").toString());
            List<MessageGroupEntity> entities = messageGroupMapper.query(messageGroupEntity);
            if (entities != null && entities.size() > 0) {
                List<Map<String, Object>> mapMsgBody = JSONUtils.json2MapList(map.get("NewMemberList").toString());
                for (Map<String, Object> map1 : mapMsgBody) {
                    MessageJoinGroupEntity entity = new MessageJoinGroupEntity();
                    entity.setGroup_id(entities.get(0).getId());
                    entity.setUser_id(Long.parseLong(map1.get("Member_Account").toString()));
                    List<MessageJoinGroupEntity> entityList = messageJoinGroupMapper.queryListPage(entity);
                    entity.setJoin_type(map.get("JoinType").toString());
                    entity.setOperator_account(map.get("Operator_Account").toString());
                    entity.setStatus(1);
                    if (entityList != null && entityList.size() > 0) {
                        entity.setId(entityList.get(0).getId());
                        ret = messageJoinGroupMapper.updateByPrimaryKeySelective(entity);
                    } else {
                        ret = messageJoinGroupMapper.insertSelective(entity);
                    }
                }
            }
            if (ret < 1) {
                timResultEntity.setActionStatus("FAIL");
                timResultEntity.setErrorCode(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timResultEntity;
    }

    /**
     * ???????????????????????????
     * 2019???1???10???
     *
     * @param map zwx
     */
    public TIMResultEntity GroupCallbackAfterMemberExit(Map<String, Object> map) {
        int ret = 0;
        TIMResultEntity timResultEntity = new TIMResultEntity();
        try {
            MessageGroupEntity messageGroupEntity = new MessageGroupEntity();
            messageGroupEntity.setGroup_id(map.get("GroupId").toString());
            List<MessageGroupEntity> entities = messageGroupMapper.query(messageGroupEntity);
            if (entities != null && entities.size() > 0) {
                if (entities.get(0).getStatus() == 3) {
                    MessageGroupEntity messageGroupEntity1 = new MessageGroupEntity();
                    messageGroupEntity1.setId(entities.get(0).getId());
                    messageGroupEntity1.setStatus(1);
                    ret = messageGroupMapper.updateByPrimaryKeySelective(messageGroupEntity1);
                }
                List<Map<String, Object>> mapMsgBody = JSONUtils.json2MapList(map.get("ExitMemberList").toString());
                for (Map<String, Object> map1 : mapMsgBody) {
                    MessageJoinGroupEntity entity = new MessageJoinGroupEntity();
                    entity.setGroup_id(entities.get(0).getId());
                    entity.setUser_id(Long.parseLong(map1.get("Member_Account").toString()));
                    List<MessageJoinGroupEntity> entityList = messageJoinGroupMapper.queryListPage(entity);
                    entity.setOperator_account(map.get("Operator_Account").toString());
                    entity.setStatus(4);
                    entity.setExit_type(map.get("ExitType").toString());
                    if (entityList != null && entityList.size() > 0) {
                        entity.setId(entityList.get(0).getId());
                        ret = messageJoinGroupMapper.updateByPrimaryKeySelective(entity);
                    } else {
                        ret = messageJoinGroupMapper.insertSelective(entity);
                    }
                }
            }
            if (ret < 1) {
                timResultEntity.setActionStatus("FAIL");
                timResultEntity.setErrorCode(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timResultEntity;

    }

    /**
     * ????????????????????????
     * 2019???1???9???
     *
     * @param map zwx
     */
    public void GroupCallbackAfterGroupFull(Map<String, Object> map) {
        try {
            MessageGroupEntity messageGroupEntity = new MessageGroupEntity();
            messageGroupEntity.setGroup_id(map.get("GroupId").toString());
            List<MessageGroupEntity> entities = messageGroupMapper.query(messageGroupEntity);
            MessageGroupEntity messageGroupEntity1 = new MessageGroupEntity();
            messageGroupEntity1.setId(entities.get(0).getId());
            messageGroupEntity1.setStatus(3);
            messageGroupMapper.updateByPrimaryKeySelective(messageGroupEntity1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ????????????????????????
     * 2019???1???10???
     *
     * @param map zwx
     */
    public TIMResultEntity GroupCallbackAfterGroupDestroyed(Map<String, Object> map) {
        TIMResultEntity timResultEntity = new TIMResultEntity();
        int ret = 0;
        try {
            logger.info(map.get("GroupId").toString());
            MessageGroupEntity messageGroupEntity=new MessageGroupEntity();
            messageGroupEntity.setGroup_id(map.get("GroupId").toString());
            List<MessageGroupEntity> entities=messageGroupMapper.query(messageGroupEntity);
            if(entities!=null&&entities.size()>0){
                messageGroupEntity=entities.get(0);
                //ret=messageGroupMapper.deleteByPrimaryKey(entities.get(0).getId());
                messageGroupService.insert(messageGroupEntity,null,null);
            }
            if(ret<1){
                timResultEntity.setActionStatus("FAIL");
                timResultEntity.setErrorCode(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timResultEntity;
    }

    /**
     * ??????????????????????????????
     * 2019???1???10???
     *
     * @param map zwx
     */
    public TIMResultEntity GroupCallbackAfterGroupInfoChanged(Map<String, Object> map) {

        int ret = 0;
        TIMResultEntity timResultEntity = new TIMResultEntity();
        try {
            MessageGroupEntity messageGroupEntity = new MessageGroupEntity();
            messageGroupEntity.setGroup_id(map.get("GroupId").toString());
            List<MessageGroupEntity> entities = messageGroupMapper.query(messageGroupEntity);
            if (entities != null && entities.size() > 0) {
                MessageGroupEntity groupEntity = new MessageGroupEntity();
                groupEntity.setId(entities.get(0).getId());
                if (map.containsKey("Operator_Account")) {
                    groupEntity.setOperator_account(map.get("Operator_Account").toString());
                }
                if (map.containsKey("Name")) {
                    groupEntity.setGroup_name(map.get("Name").toString());
                }
                if (map.containsKey("Type")) {
                    groupEntity.setGroup_type(map.get("Type").toString());
                }
                if (map.containsKey("Introduction")) {
                    groupEntity.setIntroduction(map.get("Introduction").toString());
                }
                if (map.containsKey("Notification")) {
                    groupEntity.setNotification(map.get("Notification").toString());
                }
                if (map.containsKey("FaceUrl")) {
                    groupEntity.setFaceUrl(map.get("FaceUrl").toString());
                }
                ret = messageGroupMapper.updateByPrimaryKeySelective(groupEntity);

            }
            if (ret < 1) {
                timResultEntity.setActionStatus("FAIL");
                timResultEntity.setErrorCode(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timResultEntity;
    }

    /**
     * ??????????????????
     * 2019???1???9???
     *
     * @param message zwx
     */
    public void StateStateChange(Map<String, Object> message) {
        try {
            Map<String, Object> map1 = JSONUtils.obj2Map(message.get("Info"), null);

            if(isStr2Num(map1.get("To_Account").toString())){
                Long userid = Long.parseLong(map1.get("To_Account").toString());
                String state = map1.get("Action").toString();
                Map<String, Object> map2 = new HashMap<>();
                map2.put("user_id", userid);
                map2.put("logonStatus", state);
                map2.put("login_time", new Date());
                rabbitTemplate.convertAndSend("updateUserOnlineState", map2);//???????????????
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MessageCallbackFactoryImpl  StateStateChange");
        }
    }
    /**
     * ????????????????????????????????????????????????
     * @param str ?????????
     * @return true ??????; false ?????????
     */
    public static boolean isStr2Num(String str) {
        try {
            Long.parseLong(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    /**
     * ????????????????????????
     * 2019???1???11???
     *
     * @param map zwx
     */
    public TIMResultEntity SnsCallbackFriendAdd(Map<String, Object> map) {
        int ret = 0;
        TIMResultEntity timResultEntity = new TIMResultEntity();
        try {
            List<Map<String, Object>> pairList = JSONUtils.json2MapList(map.get("PairList").toString());
            MessageFriendEntity friendEntity = new MessageFriendEntity();
            for (Map<String, Object> map1 : pairList
                    ) {
                friendEntity.setUser_id(Long.parseLong(map1.get("From_Account").toString()));
                friendEntity.setFirend_id(Long.parseLong(map1.get("To_Account").toString()));
                List<MessageFriendEntity> messageFriendEntities = messageFriendMapper.query(friendEntity);
                if (messageFriendEntities != null && messageFriendEntities.size() > 0) {
                    friendEntity.setStatus(2);
                    ret = messageFriendMapper.updateByPrimaryKeySelective(friendEntity);
                } else {
                    ret = messageFriendMapper.insertSelective(friendEntity);
                }
            }
            if (ret < 1) {
                timResultEntity.setActionStatus("FAIL");
                timResultEntity.setErrorCode(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MessageCallbackFactoryImpl  SnsCallbackFriendAdd");
        }
        return timResultEntity;
    }

    /**
     * ????????????????????????
     * 2019???1???11???
     *
     * @param map zwx
     */
    public TIMResultEntity SnsCallbackFriendDelete(Map<String, Object> map) {
        int ret = 0;
        TIMResultEntity timResultEntity = new TIMResultEntity();
        try {
            List<Map<String, Object>> pairList = JSONUtils.json2MapList(map.get("PairList").toString());
            MessageFriendEntity friendEntity = new MessageFriendEntity();
            for (Map<String, Object> map1 : pairList
                    ) {
                friendEntity.setUser_id(Long.parseLong(map1.get("From_Account").toString()));
                friendEntity.setFirend_id(Long.parseLong(map1.get("To_Account").toString()));
                List<MessageFriendEntity> messageFriendEntities = messageFriendMapper.query(friendEntity);
                friendEntity.setStatus(3);
                if (messageFriendEntities != null && messageFriendEntities.size() > 0) {
                    ret = messageFriendMapper.updateByPrimaryKeySelective(friendEntity);
                } else {
                    ret = messageFriendMapper.insertSelective(friendEntity);
                }
            }
            if (ret < 1) {
                timResultEntity.setActionStatus("FAIL");
                timResultEntity.setErrorCode(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MessageCallbackFactoryImpl  SnsCallbackFriendDelete");
        }
        return timResultEntity;
    }

    /**
     * ???????????????????????????
     * 2019???1???11???
     *
     * @param map zwx
     */
    public TIMResultEntity SnsCallbackBlackListAdd(Map<String, Object> map) {
        int ret = 0;
        TIMResultEntity timResultEntity = new TIMResultEntity();
        try {
            List<Map<String, Object>> pairList = JSONUtils.json2MapList(map.get("PairList").toString());
            MessageFriendEntity friendEntity = new MessageFriendEntity();
            for (Map<String, Object> map1 : pairList
                    ) {
                friendEntity.setUser_id(Long.parseLong(map1.get("From_Account").toString()));
                friendEntity.setFirend_id(Long.parseLong(map1.get("To_Account").toString()));
                List<MessageFriendEntity> messageFriendEntities = messageFriendMapper.query(friendEntity);
                friendEntity.setStatus(4);
                if (messageFriendEntities != null && messageFriendEntities.size() > 0) {
                    ret = messageFriendMapper.updateByPrimaryKeySelective(friendEntity);
                } else {
                    ret = messageFriendMapper.insertSelective(friendEntity);
                }
            }
            if (ret < 1) {
                timResultEntity.setActionStatus("FAIL");
                timResultEntity.setErrorCode(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MessageCallbackFactoryImpl  SnsCallbackBlackListAdd");
        }
        return timResultEntity;
    }

    /**
     * ???????????????????????????
     * 2019???1???11???
     *
     * @param map zwx
     */
    public TIMResultEntity SnsCallbackBlackListDelete(Map<String, Object> map) {
        int ret = 0;
        TIMResultEntity timResultEntity = new TIMResultEntity();
        try {
            List<Map<String, Object>> pairList = JSONUtils.json2MapList(map.get("PairList").toString());
            MessageFriendEntity friendEntity = new MessageFriendEntity();
            for (Map<String, Object> map1 : pairList
                    ) {
                friendEntity.setUser_id(Long.parseLong(map1.get("From_Account").toString()));
                friendEntity.setFirend_id(Long.parseLong(map1.get("To_Account").toString()));
                List<MessageFriendEntity> messageFriendEntities = messageFriendMapper.query(friendEntity);
                friendEntity.setStatus(2);
                if (messageFriendEntities != null && messageFriendEntities.size() > 0) {
                    ret = messageFriendMapper.updateByPrimaryKeySelective(friendEntity);
                } else {
                    ret = messageFriendMapper.insertSelective(friendEntity);
                }
            }
            if (ret < 1) {
                timResultEntity.setActionStatus("FAIL");
                timResultEntity.setErrorCode(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MessageCallbackFactoryImpl  SnsCallbackBlackListDelete");
        }
        return timResultEntity;
    }

    /**
     * ??????/???????????????
     * 2019???1???11???
     *
     * @param map zwx
     */
    public void plugCutOff(Map<String, Object> map) {
        int ret = 0;
        try {
            if (map.get("sign").toString().equalsIgnoreCase(MD5Util.GetMD5Code("huihe123321"+map.get("t").toString()))) {
                String stream_param = map.get("stream_param").toString();
                stream_param = stream_param.replace("=", ":");
                String[] string1 = stream_param.split("&");
                Map<String, Object> map1 = new HashMap<>();
                for (String s : string1) {
                    String[] strings = s.split(":");
                    map1.put(strings[0], strings[1]);
                }
                String groupid = null;
                if(map1.containsKey("groupid")&&map1.get("groupid")!=null){
                    groupid = map1.get("groupid").toString();
                }else{
                    if(map.containsKey("stream_id")&&map.get("stream_id")!=null) {
                        String stream_id = map.get("stream_id").toString();
                        MessageGroupFlowEntity messageGroupFlow = new MessageGroupFlowEntity();
                        messageGroupFlow.setStream_id(stream_id);
                        messageGroupFlow.setStatus(1);
                        messageGroupFlow.setPageSize(1);
                        List<MessageGroupFlowEntity> messageGroupFlowEntities = messageGroupFlowMapper.queryListPage(messageGroupFlow);
                        if (messageGroupFlowEntities != null && messageGroupFlowEntities.size() > 0) {
                            groupid=messageGroupFlowEntities.get(0).getGroup_id();
                        }
                    }
                }
                if(groupid!=null&&!"".equals(groupid)) {
                    MessageGroupEntity messageGroupEntity = new MessageGroupEntity();
                    messageGroupEntity.setGroup_id(groupid);
                    messageGroupEntity.setPageSize(1);
                    List<MessageGroupEntity> entities = messageGroupMapper.queryListPage(messageGroupEntity);
                    if (entities != null && entities.size() > 0) {//?????????????????????
                        if ("1".equals(map.get("event_type").toString())) {
                            MessageGroupFlowEntity messageGroupFlowEntity = new MessageGroupFlowEntity();
                            messageGroupFlowEntity.setGroup_id(groupid);
                            messageGroupFlowEntity.setSequence(map.get("sequence").toString());
                            messageGroupFlowEntity.setStream_param(map.get("stream_param").toString());
                            messageGroupFlowEntity.setChannel_id(map.get("channel_id").toString());
                            messageGroupFlowEntity.setStream_id(map.get("stream_id").toString());
                            messageGroupFlowEntity.setApp(map.get("app").toString());
                            messageGroupFlowEntity.setAppname(map.get("appname").toString());
                            MessageGroupFlowEntity messageGroupFlow = new MessageGroupFlowEntity();
                            messageGroupFlow.setStream_id(map.get("stream_id").toString());
                            messageGroupFlow.setGroup_id(groupid);
                            messageGroupFlow.setStatus(1);
                            messageGroupFlow.setPageSize(1);
                            List<MessageGroupFlowEntity> messageGroupFlowEntities = messageGroupFlowMapper.queryListPage(messageGroupFlow);
                            if (messageGroupFlowEntities != null && messageGroupFlowEntities.size() > 0) {
                                messageGroupFlowEntity.setId(messageGroupFlowEntities.get(0).getId());
                                ret = messageGroupFlowMapper.updateByPrimaryKeySelective(messageGroupFlowEntity);
                            } else {
                                ret = messageGroupFlowMapper.insertSelective(messageGroupFlowEntity);
                            }
                        } else if ("0".equals(map.get("event_type").toString())) {
                            MessageGroupFlowEntity messageGroupFlowEntity = new MessageGroupFlowEntity();
                            messageGroupFlowEntity.setStream_id(map.get("stream_id").toString());
                            messageGroupFlowEntity.setStatus(1);
                            messageGroupFlowEntity.setGroup_id(groupid);
                            messageGroupFlowEntity.setPageSize(1);
                            List<MessageGroupFlowEntity> messageGroupFlowEntities = messageGroupFlowMapper.queryListPage(messageGroupFlowEntity);
                            if (messageGroupFlowEntities != null && messageGroupFlowEntities.size() > 0) {
                                messageGroupFlowEntity.setId(messageGroupFlowEntities.get(0).getId());
                                messageGroupFlowEntity.setStatus(2);
                                ret = messageGroupFlowMapper.updateByPrimaryKeySelective(messageGroupFlowEntity);
                            }
                        }
                        if (ret > 0) {
                            if(entities.get(0).getType()!=null&&entities.get(0).getType().contains("realtime")){//?????????????????????
                                MixedFlowThread messageServive = new MixedFlowThread();
                                messageServive.init(messageGroupEntity.getGroup_id(),entities.get(0).getType(),"app",messageGroupFlowService);
                                ThreadPool pool = ThreadPool.getInstance();
                                pool.execute(messageServive);
                            }
                                /*mixedFlow(groupid, entities.get(0).getType(), "app","Apply");//app
                                mixedFlow(groupid, entities.get(0).getType(), "ipad","Apply");//ipad*/
                                /*MixedFlowThread mixedFlowThread = new MixedFlowThread();
                                mixedFlowThread.init(messageGroupEntity.getGroup_id(),messageGroupEntity.getType(),"ipad","Apply",messageGroupFlowService);
                                pool.execute(mixedFlowThread);*/
                        }
                    }
                }
            }else{
                logger.warn(map.get("sign").toString()+":"+MD5Util.GetMD5Code("huihe123321"+map.get("t").toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MessageCallbackFactoryImpl plugCutOff");
        }
    }

    /**
     * ????????????
     * 2020???3???14???17:41:12
     *
     * @param map zwx
     */
    public void recordingRecord(Map<String, Object> map) {
        try {
            System.out.println(map.toString());
            if(map.containsKey("stream_id")&&map.get("stream_id")!=null){
                String stream_id=map.get("stream_id").toString();
                GroupRecordingEntity groupRecordingEntity=new GroupRecordingEntity();
                groupRecordingEntity.setStream_id(stream_id);
                groupRecordingEntity.setStatus(1);
                groupRecordingEntity.setPageSize(1);
                List<GroupRecordingEntity> groupRecordingEntities=groupRecordingMapper.queryListPage(groupRecordingEntity);
                if(groupRecordingEntities!=null&&groupRecordingEntities.size()>0){
                    groupRecordingEntity=groupRecordingEntities.get(0);
                    GroupVideoEntity groupVideoEntity=new GroupVideoEntity();
                    groupVideoEntity.setRecording_id(groupRecordingEntity.getId());
                    groupVideoEntity.setStream_param(map.get("stream_param").toString());
                    groupVideoEntity.setRecord_file_id(map.get("record_file_id").toString());
                    groupVideoEntity.setVideo_url(map.get("video_url").toString());
                    groupVideoEntity.setStream_id(stream_id);
                    groupVideoEntity.setVideo_format(map.get("file_format").toString());
                    int ret=groupVideoMapper.insertSelective(groupVideoEntity);
                    if(ret>0){
                        groupRecordingEntity.setVideo_format(groupVideoEntity.getVideo_format());
                        if(groupRecordingEntity.getRecord_url()==null||"".equals(groupRecordingEntity.getRecord_url())){
                            groupRecordingEntity.setRecord_url(groupVideoEntity.getVideo_url());
                            int res=groupRecordingMapper.updateByPrimaryKeySelective(groupRecordingEntity);
                            if(res>0){
                            }
                        }
                    }
                }else{
                    //??????????????????
                    logger.warn("??????????????????");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MessageCallbackFactoryImpl plugCutOff");
        }
    }
    /**
     * ????????????????????????
     * 2020???4???15???17:55:12
     *
     * @param map zwx
     */
    public void splicingCallback(Map<String, Object> map) {
        if(map.containsKey("EventType")&&map.get("EventType")!=null&& "EditMediaComplete".equals(map.get("EventType"))){
            Map<String,Object> stringObjectMap=JSONUtils.obj2Map(map.get("EditMediaCompleteEvent"),null);
            if(stringObjectMap.containsKey("Message")&& "SUCCESS".equalsIgnoreCase(stringObjectMap.get("Message").toString())){
                Map<String,Object> objectMap=JSONUtils.obj2Map(stringObjectMap.get("Output"),null);
                GroupRecordingEntity groupRecordingEntity=new GroupRecordingEntity();
                groupRecordingEntity.setAppointment_id(Long.parseLong(objectMap.get("MediaName").toString()));
                groupRecordingEntity.setPageSize(1);
                List<GroupRecordingEntity> groupRecordingEntities=groupRecordingMapper.queryListPage(groupRecordingEntity);
                if(groupRecordingEntities!=null&&groupRecordingEntities.size()>0){
                    groupRecordingEntity=groupRecordingEntities.get(0);
                    groupRecordingEntity.setRecord_url(objectMap.get("FileUrl").toString());
                    groupRecordingEntity.setVideo_format(objectMap.get("FileType").toString());
                    groupRecordingEntity.setStatus(2);
                    int ret=groupRecordingMapper.updateByPrimaryKeySelective(groupRecordingEntity);
                    if(ret>0){
                        userApiService.updateRecording(groupRecordingEntity.getAppointment_id());
                        GroupVideoEntity groupVideoEntity=new GroupVideoEntity();
                        groupVideoEntity.setRecording_id(groupRecordingEntity.getId());
                        List<GroupVideoEntity> groupVideoEntities=groupVideoMapper.query(groupVideoEntity);
                        for (GroupVideoEntity videoEntity:groupVideoEntities){
                            videoEntity.setStatus(2);
                            groupVideoMapper.updateByPrimaryKeySelective(videoEntity);
                        }
                    }
                }
            }
        }
    }
}
