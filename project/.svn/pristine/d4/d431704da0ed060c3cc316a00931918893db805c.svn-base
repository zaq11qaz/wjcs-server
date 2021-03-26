package com.huihe.eg.message.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.huihe.eg.message.model.MessageFriendEntity;
import com.huihe.eg.message.mybatis.dao.MessageFriendMapper;
import com.huihe.eg.message.service.dao.MessageFriendService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MessageFriendServiceImpl extends BaseFrameworkServiceImpl<MessageFriendEntity, Long> implements MessageFriendService {

    @Resource
    private MessageFriendMapper mapper;
    @Resource
    private RedisService redisService;
    @Override
    public void init() {
        setMapper(mapper);
    }

    /**
     * 查询好友
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<MessageFriendEntity> queryUserFirendInfo(MessageFriendEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MessageFriendEntity> messageFriendEntities=null;
        try {
            messageFriendEntities=mapper.queryUserFirendInfo(param);
            for (MessageFriendEntity messageFriendEntity:messageFriendEntities){
                HashMap<String,Object> map = new HashMap<>();
                if(param.getUser_id().equals(messageFriendEntity.getFirend_id())){
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(messageFriendEntity.getUser_id()+"userinfo")));
                }else {
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(messageFriendEntity.getFirend_id()+"userinfo")));
                }
                messageFriendEntity.setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("MessageFriendServiceImpl queryUserFirendInfo");
        }
        return messageFriendEntities;
    }
}