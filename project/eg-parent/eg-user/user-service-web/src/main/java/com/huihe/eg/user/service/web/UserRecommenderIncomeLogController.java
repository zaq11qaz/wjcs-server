package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.UserRecommenderIncomeLogEntity;
import com.huihe.eg.user.service.dao.UserRecommenderIncomeLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @ Author     ：zwy
 * @ Date       ：2021/1/5 13:29
 * @ Description：收益日志
 * @ since: JDk1.8
 */
@Api(value="收益日志可用接口说明",description="收益日志可用接口说明",tags = {"收益日志"})
@RestController
@RequestMapping("userRecommenderIncomeLog")
public class UserRecommenderIncomeLogController extends BaseFrameworkController<UserRecommenderIncomeLogEntity, Long> {

    @Resource
    private UserRecommenderIncomeLogService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 查询我的机构推荐官收益列表
     * @author: zwy
     * @date: 19:21 2020/9/7
     * @since: JDk1.8
     */
    @GetMapping("queryMyMechanismRecommenderList")
    @ApiOperation(value = "查询我的收益列表")
    public ResultParam queryMyMechanismRecommenderList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMyMechanismRecommenderList(param, request, response));
    }

    /**
     * 查询我的机构推荐官收益列表
     * @author: zwy
     * @date: 19:21 2020/9/7
     * @since: JDk1.8
     */
    @GetMapping("queryMechanismManagerList")
    @ApiOperation(value = "查询我的收益列表")
    public ResultParam queryMechanismManagerList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismManagerList(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/8 20:29
     * @ Description：机构招募官收益统计
     * @since: JDk1.8
     */
    @GetMapping("queryMechanismRecommenderCash")
    @ApiOperation(value = "机构招募官收益统计")
    public ResultParam queryMechanismRecommenderCash(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismRecommenderCash(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/8 20:29
     * @ Description：查看详情
     * @since: JDk1.8
     */
    @GetMapping("queryDetails")
    @ApiOperation(value = "查看详情")
    public ResultParam queryDetails(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryDetails(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020年9月12日 14:17:46
     * @ Description：机构招募官收益记录
     * @since: JDk1.8
     */
    @GetMapping("queryMechanismCashList")
    @ApiOperation(value = "机构招募官收益记录")
    public ResultParam queryMechanismCashList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismCashList(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/16 16:57
     * @ Description：查询邀请用户收益列表
     * @since: JDk1.8
     */
    @GetMapping("queryInviteCashList")
    @ApiOperation(value = "查询邀请用户收益列表")
    public ResultParam queryInviteCashList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryInviteCashList(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/16 17:05
     * @ Description：查询邀请用户收益
     * @since: JDk1.8
     */
    @GetMapping("queryInviteCash")
    @ApiOperation(value = "查询邀请用户收益")
    public ResultParam queryInviteCash(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryInviteCash(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/16 17:05
     * @ Description：结算课程收益
     * @since: JDk1.8
     */
    @GetMapping("querySettlementLog")
    @ApiOperation(value = "结算课程收益")
    public ResultParam querySettlementLog(UserRecommenderIncomeLogEntity param) {
        return service.querySettlementLog(param);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/10/10 15:20
     * @ Description：查询结算详情
     * @since: JDk1.8
     */
    @PostMapping("queryCourseDetails")
    @ApiOperation(value = "查询结算详情")
    public ResultParam queryCourseDetails(@RequestBody UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryCourseDetails(param,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/10/17 13:46
     * @ Description：机构收益列表
     * @since: JDk1.8
     */
    @GetMapping("queryMechanismCashTotalList")
    @ApiOperation(value = "机构收益列表")
    public ResultParam queryMechanismCashTotalList( UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryMechanismCashTotalList(param,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/10/17 13:46
     * @ Description：查询排行榜
     * @since: JDk1.8
     */
    @GetMapping("queryRanking")
    @ApiOperation(value = "查询排行榜")
    public ResultParam queryRanking( UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryRanking(param,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/10/17 13:46
     * @ Description：查询详情列表
     * @since: JDk1.8
     */
    @GetMapping("queryDetailList")
    @ApiOperation(value = "查询详情列表")
    public ResultParam queryDetailList( UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryDetailList(param,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/10/17 13:46
     * @ Description：结算邀请
     * @since: JDk1.8
     */
    @PostMapping("detail")
    @ApiOperation(value = "结算邀请")
    public ResultParam detail(@RequestBody UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.detail(param,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/30 18:15
     * @ Description：定时结算推荐购买优惠券
     * @ since: JDk1.8
     */
    @ApiOperation(value = "定时结算推荐购买优惠券")
    @GetMapping(value = "updateInviteCouponCash")
    @RabbitListener(queues = "updateInviteCouponCash")
    public void updateInviteCouponCash() {
        try {
            UserRecommenderIncomeLogEntity param = new UserRecommenderIncomeLogEntity();
            service.updateInviteCouponCash(param);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}