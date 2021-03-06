package com.huihe.eg.authorization.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.authorization.model.ManagerUserEntity;
import com.huihe.eg.authorization.service.dao.ManagerUserService;
import com.huihe.eg.comm.SystemEum;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.*;

import static com.cy.framework.util.result.ResultEnum.SYSTEM_ERROR;

@Api(value="后台管理的用户商户可用接口说明",description="后台管理的用户商户可用接口说明",tags = {"后台管理的用户/商户"})
@RestController
@RequestMapping("managerUser")
public class ManagerUserController extends BaseFrameworkController<ManagerUserEntity, Long> {

    @Resource
    private ManagerUserService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    @RequestMapping("queryManager")
    public String queryManager(@RequestBody Long id) {
        return service.queryManager(id);
    }

    /**
     * 查询总条数
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    @ApiOperation(value = "查询总条数", httpMethod = "GET", notes = "查询总条数")
    @GetMapping("queryListPageCount")
    public ResultParam queryListPageCount(ManagerUserEntity param, HttpServletRequest request, HttpServletResponse response) {
        init();
        return ResultUtil.success(service.queryListPageCount(param,request,response));
    }

    /**
    * @Description:登录
    * @Param:
    * @return:
    * @Author: zwy
    * @Date: 2020/8/8
    */
    @ApiOperation(
            value = "管理员登录",
            httpMethod = "POST",
            notes = "管理员登录"
    )
    @PostMapping({"login"})
    public ResultParam login(@RequestBody ManagerUserEntity entity, HttpServletRequest request, HttpServletResponse response) {
        this.init();
        ResultParam resultParam=new ResultParam();
        try {
            resultParam=service.login(entity, request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultParam;
    }

    @ApiOperation(
            value = "导入redis",
            httpMethod = "GET",
            notes = "导入redis"
    )
    @RequestMapping({"queryManagerInfoInsertRedis"})
    @RabbitListener(queues = "queryManagerInfoInsertRedis")
    public void insertRedis(ManagerUserEntity param ,HttpServletRequest request, HttpServletResponse response) {
        try {
            service.queryManagerInfoInsertRedis(param,request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出登录
     * @author : zwy
     * 2020-08-11 04:06
     * @since JDK1.8
     */
    @ApiOperation(
            value = "退出登录",
            httpMethod = "POST",
            notes = "退出登录"
    )
    @PostMapping({"logOut"})
    public ResultParam logOut(ManagerUserEntity param ,HttpServletRequest request, HttpServletResponse response) {
        try {
            return ResultUtil.success(service.logOut(param,request,response));
        } catch (Exception e) {
            e.printStackTrace();
        }
     return ResultUtil.error(SYSTEM_ERROR.getCode(),SYSTEM_ERROR.getDesc());
    }

    @ApiOperation(
            value = "查询管理员id",
            httpMethod = "GET",
            notes = "查询管理员id"
    )
    @RequestMapping({"queryManagerId"})
    public Long queryManagerId(@RequestBody String type ,HttpServletRequest request, HttpServletResponse response) {
        return service.queryManagerId(type,request,response);
    }

    @ApiOperation(
            value = "查询管理员id",
            httpMethod = "GET",
            notes = "查询管理员id"
    )
    @PostMapping({"updateLevel"})
    public ResultParam updateLevel(@RequestBody ManagerUserEntity param ,HttpServletRequest request, HttpServletResponse response) {
        return service.updateLevel(param,request,response);
    }

    @ApiOperation(
            value = "新增活动管理员",
            httpMethod = "GET",
            notes = "新增活动管理员"
    )
    @PostMapping({"insertActivityManager"})
    public ResultParam insertActivityManager(@RequestBody ManagerUserEntity param ,HttpServletRequest request, HttpServletResponse response) {
        return service.insertActivityManager(param,request,response);
    }



}