package com.huihe.eg.message.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.message.model.MessageFriendEntity;
import com.huihe.eg.message.mybatis.dao.MessageFriendMapper;
import com.huihe.eg.message.service.dao.MessageFriendService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户好友列表
 *
 * @author zwx
 * @date 2019年11月26日11:21:54
 * @since JDK1.8
 */
@Api(value="用户好友列表可用接口说明",description="用户好友列表可用接口说明",tags = {"用户好友列表"})
@RestController
@RequestMapping("messageFriend")
public class MessageFriendController extends BaseFrameworkController<MessageFriendEntity, Long> {

    @Resource
    private MessageFriendService service;
    @Resource
    private MessageFriendMapper messageFriendMapper;

    @Override
    public void init() {
        setBaseService(service);
    }
    /**
     * 查询好友
     * @author zwx
     * @date 2019年11月26日11:23:04
     * @since JDK1.8
     */
    @ApiOperation(value = "查询好友", httpMethod = "GET", notes = "查询好友")
    @GetMapping({"queryUserFirendInfo"})
    public ResultParam queryUserFirendInfo(MessageFriendEntity param, HttpServletRequest request, HttpServletResponse response) {
        init();
        return ResultUtil.success(service.queryUserFirendInfo(param,request,response));
    }
    @RequestMapping("queryIsFriend")
    @ResponseBody
    public String queryIsFriend(Long user_id,Long oather_id) {
        MessageFriendEntity messageFriendEntity=new MessageFriendEntity();
        messageFriendEntity.setUser_id(user_id);
        messageFriendEntity.setFirend_id(oather_id);
        messageFriendEntity.setPageSize(1);
        List<MessageFriendEntity> entityList =messageFriendMapper.query(messageFriendEntity);
        if(entityList!=null&&entityList.size()>0){
            return "true";
        }else{
            return "false";
        }

    }
}