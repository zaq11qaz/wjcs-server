package com.huihe.eg.message.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.http.HttpsClientRequest;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.message.model.MessageGroupEntity;
import com.huihe.eg.message.model.MessageJoinGroupEntity;
import com.huihe.eg.message.model.NoteUserEntity;
import com.huihe.eg.message.mybatis.dao.MessageJoinGroupMapper;
import com.huihe.eg.message.service.dao.MessageGroupService;
import com.huihe.eg.message.service.dao.MessageJoinGroupService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageJoinGroupServiceImpl extends BaseFrameworkServiceImpl<MessageJoinGroupEntity, Long> implements MessageJoinGroupService {

    @Resource
    private MessageJoinGroupMapper mapper;
    @Resource
    private MessageGroupService messageGroupService;
    @Resource
    private RedisService redisService;
    @Override
    public void init() {
        setMapper(mapper);
    }

    /**
     * 分页查询
     *
     * @Description: 分页查询
     * @author zwx
     * @Date : 2019年8月29日16:22:10
     */
    @Override
    public List<MessageJoinGroupEntity> queryListPage(MessageJoinGroupEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MessageJoinGroupEntity> list=null;
        try{
            list=mapper.queryListPage(param);
            for (MessageJoinGroupEntity messageJoinGroupEntity:list){
                Map<String,Object> map=new HashMap<>();
                MessageGroupEntity messageGroupEntity=messageGroupService.findById(messageJoinGroupEntity.getGroup_id(),request,response);
                map.put("groupinfo", messageGroupEntity);//地球圈
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(messageJoinGroupEntity.getUser_id() + "userinfo")));//地球圈发布用户信息
                messageJoinGroupEntity.setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("MessageJoinGroupServiceImpl    queryListPage");
        }
        return list;
    }

    @Override
    public List<Long> queryGroupUserInfos(Long group_id){
        MessageJoinGroupEntity messageJoinGroupEntity=new MessageJoinGroupEntity();
        messageJoinGroupEntity.setGroup_id(group_id);
        messageJoinGroupEntity.setStatus(1);
        return mapper.queryGroupUserInfos(messageJoinGroupEntity);

    }

    /**
     * 更新状态
     *
     * @Description: 更新状态
     * @author zwx
     * @Date : 2019年9月5日10:04:09
     */
    @Override
    public ResultParam updateStatus(MessageJoinGroupEntity param, HttpServletRequest request, HttpServletResponse response) {
        try{
            MessageJoinGroupEntity entity = new MessageJoinGroupEntity();
            entity.setGroup_id(param.getGroup_id());
            entity.setUser_id(param.getUser_id());
            entity.setPageSize(1);
            List<MessageJoinGroupEntity> entityList = mapper.queryListPage(entity);
            if (entityList != null && entityList.size() > 0) {
                int ret=mapper.updateStatus(param);
                if(ret>0){
                    return ResultUtil.success();
                }
            }else{
                int ret=mapper.insertSelective(param);
                if(ret>0){
                    return ResultUtil.success();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("MessageJoinGroupServiceImpl    queryListPage");
        }
        return ResultUtil.error(ResultEnum.result_3.getCode(),ResultEnum.result_3.getDesc());
    }
    /**
     * 分页查询我的
     *
     * @Description: 分页查询我的
     * @author zwx
     * @Date : 2019年11月5日15:35:53
     */
    @Override
    public List<MessageJoinGroupEntity> queryMyListPage(MessageJoinGroupEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MessageJoinGroupEntity> list=null;
        try{
            list=mapper.queryMyListPage(param);
            for (MessageJoinGroupEntity messageJoinGroupEntity:list){
                Map<String,Object> map=new HashMap<>();
                MessageGroupEntity messageGroupEntity=messageGroupService.findById(messageJoinGroupEntity.getGroup_id(),request,response);
                map.put("groupinfo", messageGroupEntity);//地球圈
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(messageJoinGroupEntity.getUser_id() + "userinfo")));//地球圈发布用户信息
                messageJoinGroupEntity.setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("MessageJoinGroupServiceImpl    queryListPage");
        }
        return list;
    }
    /**
     * 查询在线人数
     *
     * @Description: 查询在线人数
     * @author zwx
     * @Date : 2020年4月20日15:01:08
     */
    @Override
    public Map<String,Object> queryOnlineCount(MessageJoinGroupEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> stringObjectMap=new HashMap<>();
        try{
            List<MessageJoinGroupEntity> messageJoinGroupEntities=mapper.queryGroupListPage(param);
            for (MessageJoinGroupEntity messageJoinGroupEntity:messageJoinGroupEntities){
                Map<String,Object> map=new HashMap<>();
                MessageGroupEntity messageGroupEntity=messageGroupService.findById(messageJoinGroupEntity.getGroup_id(),request,response);
                map.put("groupinfo", messageGroupEntity);//地球圈
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(messageJoinGroupEntity.getUser_id() + "userinfo")));//地球圈发布用户信息
                messageJoinGroupEntity.setMap(map);
            }
            stringObjectMap.put("messageJoinGroupEntities",messageJoinGroupEntities);
            stringObjectMap.put("totalCount",mapper.queryListPageCount(param));//总人数
            param.setStatus(1);
            Integer onlineCount=mapper.queryListPageCount(param);//在线人数
            param.setStatus(3);
            Integer onlineEvenCount=mapper.queryListPageCount(param);//连麦人数
            onlineCount+=onlineEvenCount;
            stringObjectMap.put("onlineCount",onlineCount);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("MessageJoinGroupServiceImpl    queryListPage");
        }
        return stringObjectMap;
    }
}