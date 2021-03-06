package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.UserGroupingEntity;
import com.huihe.eg.user.service.dao.UserGroupingService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.*;

@Api(value="用户拼团记录可用接口说明",description="用户拼团记录可用接口说明",tags = {"用户拼团记录"})
@RestController
@RequestMapping("userGrouping")
public class UserGroupingController extends BaseFrameworkController<UserGroupingEntity, Long> {

    @Resource
    private UserGroupingService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/4 11:25
     * @ Description：参加拼团
     * @ since: JDk1.8
     */
    @PostMapping("insertUserGrouping")
    @ApiOperation(value = "参加拼团")
    public ResultParam insertUserGrouping(@RequestBody UserGroupingEntity userGroupingEntity , HttpServletRequest request , HttpServletResponse response){
        return service.insertUserGrouping(userGroupingEntity,request,response);
    }

    @GetMapping("updateOnPaymentSettlementCash")
    @RabbitListener(queues = "updateOnPaymentSettlementCash")
    @ApiOperation(value = "结算全额购拼团")
    public void updateOnPaymentSettlementCash(){
        UserGroupingEntity userGroupingEntity = new UserGroupingEntity();
        service.updateOnPaymentSettlementCash(userGroupingEntity);
    }

}