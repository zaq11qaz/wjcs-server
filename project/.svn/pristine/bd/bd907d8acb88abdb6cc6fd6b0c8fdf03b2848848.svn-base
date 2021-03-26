package com.huihe.eg.message.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.message.model.MessageJoinGroupEntity;
import com.huihe.eg.message.service.dao.MessageJoinGroupService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户消息列表
 *
 * @author zwx
 * @date 2019年11月26日11:21:54
 * @since JDK1.8
 */
@Api(value="用户加群记录可用接口说明",description="用户加群记录可用接口说明",tags = {"用户加群记录"})
@RestController
@RequestMapping("messageJoinGroup")
public class MessageJoinGroupController extends BaseFrameworkController<MessageJoinGroupEntity, Long> {

    @Resource
    private MessageJoinGroupService service;
    @Override
    public void init() {
        setBaseService(service);
    }
    @RequestMapping("queryGroupUserInfos")
    @ResponseBody
    public String queryGroupUserInfos(@RequestBody Long group_id) {
        return service.queryGroupUserInfos(group_id).toString();
    }
    /**
     * 更新状态
     * @author zwx
     * @date 2019年11月26日11:23:04
     * @since JDK1.8
     */
    @ApiOperation(value = "更新状态", httpMethod = "POST", notes = "更新状态")
    @PostMapping({"updateStatus"})
    public ResultParam updateStatus(@RequestBody MessageJoinGroupEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.updateStatus(param,request,response);
    }
    /**
     * 分页查询我的
     * @author zwx
     * @date 2019年11月26日11:23:04
     * @since JDK1.8
     */
    @ApiOperation(value = "分页查询我的", httpMethod = "GET", notes = "分页查询我的")
    @GetMapping({"queryMyListPage"})
    public ResultParam queryMyListPage(MessageJoinGroupEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryMyListPage(param,request,response));
    }
    /**
     * 查询在线人数
     * @author zwx
     * @date 2020年4月20日15:02:15
     * @since JDK1.8
     */
    @ApiOperation(value = "查询在线人数", httpMethod = "GET", notes = "查询在线人数")
    @GetMapping({"queryOnlineCount"})
    public ResultParam queryOnlineCount(MessageJoinGroupEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryOnlineCount(param,request,response));
    }
}