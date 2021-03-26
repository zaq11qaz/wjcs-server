package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.UserMemberEntity;
import com.huihe.eg.user.model.count.MemberParam;
import com.huihe.eg.user.service.dao.UserMemberService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员信息
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="会员信息可用接口说明",description="会员信息可用接口说明",tags = {"会员信息"})
@RestController
@RequestMapping("userMember")
public class UserMemberController extends BaseFrameworkController<UserMemberEntity, Long> {

    @Resource
    private UserMemberService service;
    @Override
    public void init() {
        setBaseService(service);
    }
    /**
     * 后台 会员管理 会员统计
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "后台 会员管理 会员统计")
    @GetMapping("memberCount")
    public ResultParam memberCount(UserMemberEntity param,HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.memberCount(param));
    }
    /**
     * 后台 会员管理 注册信息
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "后台 会员管理 注册信息")
    @GetMapping("memberRegister")
    public ResultParam memberRegister(UserMemberEntity memberEntity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.memberRegister(memberEntity,request,response));
    }

    /**
     * 会员定时检查过期
     */
    @RabbitListener(queues = "membershipExpired")
    public void membershipExpired(){
        service.membershipExpired();
    }
    /**
     * 月初月底定时检查
     * zwx
     * 2019年4月27日20:50:48
     */
    @RabbitListener(queues = "membermonthly")
    public void membermonthly(){
        service.membermonthly();
    }

}