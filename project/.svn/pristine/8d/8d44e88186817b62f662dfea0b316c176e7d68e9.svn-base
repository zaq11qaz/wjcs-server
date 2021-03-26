package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.RechargeRecordEntity;
import com.huihe.eg.user.service.dao.RechargeRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 充值记录
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value = "充值记录可用接口说明", description = "充值记录可用接口说明", tags = {"充值记录"})
@RestController
@RequestMapping("rechargerecord")
public class RechargeRecordController extends BaseFrameworkController<RechargeRecordEntity, Long> {

    @Resource
    private RechargeRecordService service;

    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 后台-总资产-学习卡
     *
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "后台-总资产-学习卡")
    @GetMapping("studyCardCount")
    public ResultParam studyCardCount(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.studyCardCount(param));
    }

    /**
     * 后台 总资产 会员资产
     *
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "后台 总资产 会员资产")
    @GetMapping("memberAssetsCount")
    public ResultParam memberAssetsCount(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.memberAssetsCount(param));
    }

    /**
     * 后台 总资产 猫币资产
     *
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "后台 总资产 猫币资产")
    @GetMapping("AssetsCount")
    public ResultParam AssetsCount(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.AssetsCount(param));
    }

    /**
     * 后台 总资产 总资产
     *
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "后台 总资产 总资产")
    @GetMapping("totalAssetsCount")
    public ResultParam totalAssetsCount(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.totalAssetsCount(param));
    }

    /**
     * 购买金额统计
     *
     * @author: zwy
     * @date: 20:10 2020/7/7
     * @since: JDk1.8
     */
    @ApiOperation(value = "购买金额统计")
    @GetMapping("queryPayTotal")
    public ResultParam queryPayCount(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryPayCount(param, request, response));
    }

    /**
     * 今日首冲 统计
     *
     * @author : zwy
     * 2020-08-20 10:26
     * @since JDK1.8
     */
    @ApiOperation(value = "今日首冲 统计")
    @GetMapping("queryTodayFirstTimePay")
    public ResultParam queryTodayFirstTimePay(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryTodayFirstTimePay(param, request, response));
    }

    /**
     * 查询各渠道支付总数
     *
     * @author : zwy
     * 2020-08-20 01:39
     * @since JDK1.8
     */
    @ApiOperation(value = "查询各渠道支付总数")
    @GetMapping("queryTodayPayTotal")
    public ResultParam queryTodayPayTotal(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryTodayPayTotal(param, request, response));
    }

    /**
     * 查询购买课程
     *
     * @author : zwy
     * 2020-08-25 09:21
     * @since JDK1.8
     */
    @ApiOperation(value = "查询购买课程")
    @GetMapping("queryBuyCourse")
    public ResultParam queryBuyCourse(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryBuyCourse(param, request, response));
    }

    /**
     * 查询订单
     *
     * @author : zwy
     * 2020-08-27 01:43
     * @since JDK1.8
     */
    @ApiOperation(value = "查询订单")
    @GetMapping("queryMyRecordList")
    public ResultParam queryMyRecordList(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMyRecordList(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/18 10:25
     * @ Description：查询订单PC
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询订单PC")
    @GetMapping("queryMyRecordListCount")
    public ResultParam queryMyRecordListCount(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMyRecordListCount(param, request, response));
    }


    /**
     * 核销订单
     *
     * @author : zwy
     * 2020-08-28 01:59
     * @since JDK1.8
     */
    @ApiOperation(value = "核销订单")
    @PostMapping("updateRechargeRecordStatus")
    public ResultParam updateRechargeRecordStatus(@RequestBody RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.updateRechargeRecordStatus(param, request, response);
    }

    /**
     * 管理员充值
     *
     * @author : zwy
     * 2020-08-28 01:59
     * @since JDK1.8
     */
    @ApiOperation(value = "管理员充值")
    @PostMapping("insertCatCoinRecharge")
    public ResultParam insertCatCoinRecharge(@RequestBody RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.insertCatCoinRecharge(param, request, response);
    }

    /**
     * 商品购买统计
     *
     * @author: zwy
     * @date: 20:40 2020/9/2
     * @since: JDk1.8
     */
    @ApiOperation(value = "商品统计")
    @GetMapping("queryRecommendListCount")
    public ResultParam queryRecommendListCount(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryRecommendListCount(param, request, response));
    }

    /**
     * 查询我的收益中心
     *
     * @author: zwy
     * @date: 15:18 2020/9/7
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询我的收益中心")
    @GetMapping("queryMyEarn")
    public ResultParam queryMyEarn(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMyEarn(param, request, response));
    }

    /**
     * 查询出售课程列表
     *
     * @author: zwy
     * @date: 15:18 2020/9/7
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询出售课程列表")
    @GetMapping("querySaleCourse")
    public ResultParam querySaleCourse(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.querySaleCourse(param, request, response));
    }

    /**
     * 机构收益统计
     *
     * @author: zwy
     * @date: 15:18 2020/9/7
     * @since: JDk1.8
     */
    @ApiOperation(value = "机构收益统计")
    @GetMapping("queryMechanismCash")
    public ResultParam queryMechanismCash(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismCash(param, request, response));
    }

    /**
     * 日收益统计
     *
     * @author: zwy
     * @date: 15:18 2020/9/7
     * @since: JDk1.8
     */
    @ApiOperation(value = "日收益统计")
    @GetMapping("queryDayCash")
    public ResultParam queryDayCash(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryDayCash(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/24 11:18
     * @ Description：查询学习卡购买列表
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询学习卡购买列表")
    @GetMapping("queryCardPayList")
    public ResultParam queryCardPayList(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryCardPayList(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/24 11:18
     * @ Description：查询学习卡购买统计
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询学习卡购买统计")
    @GetMapping("queryCardPayCount")
    public ResultParam queryCardPayCount(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryCardPayCount(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/24 11:18
     * @ Description：查询购买记录根据昵称
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询购买记录根据昵称")
    @GetMapping("queryCardPayListByNickName")
    public ResultParam queryCardPayListByNickName(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryCardPayListByNickName(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/24 11:18
     * @ Description：条件查询
     * @since: JDk1.8statistics_time
     */
    @ApiOperation(value = "条件查询")
    @GetMapping("queryByMessage")
    public ResultParam queryCardByMessage(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryByMessage(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/24 11:18
     * @ Description：购买体验学习卡
     * @since: JDk1.8statistics_time
     */
    @ApiOperation(value = "购买体验学习卡")
    @PostMapping("insertExperienceCard")
    public ResultParam insertExperienceCard(@RequestBody RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.insertExperienceCard(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/12 16:05
     * @ Description：购买体验直播卡
     * @ since: JDk1.8
     */
    @ApiOperation(value = "购买体验直播卡")
    @PostMapping("insertExperienceLiveCard")
    public ResultParam insertExperienceLiveCard(@RequestBody RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.insertExperienceLiveCard(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/17 16:00
     * @ Description：单节课购买
     * @ since: JDk1.8
     */
    @ApiOperation(value = "单节课购买",httpMethod = "POST")
//    @RequestMapping(value = "payOneCourse")
    public ResultParam payOneCourse(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.payOneCourse( entity,request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/17 16:00
     * @ Description：直播商品单节课购买
     * @ since: JDk1.8
     */
    @ApiOperation(value = "直播商品单节课购买",httpMethod = "POST")
    @RequestMapping(value = "payOneCourseLiveStream")
    public ResultParam payOneCourseLiveStream(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.payOneCourseLiveStream( entity,request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/10 18:55
     * @ Description：查询购买机构订单列表
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询购买机构订单列表")
    @GetMapping(value = "queryMechanismList")
    public ResultParam queryMechanismList(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryMechanismList( entity,request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/22 16:16
     * @ Description：查询购买详情
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询单节课后付费购买详情")
    @GetMapping(value = "queryPayDetails")
    public ResultParam queryPayDetails(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryPayDetails( entity,request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/22 16:16
     * @ Description：查询换课详情
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询换课详情")
    @GetMapping(value = "queryUpdateCourseDetails")
    public ResultParam queryUpdateCourseDetails(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.queryUpdateCourseDetails( entity,request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/22 16:16
     * @ Description：queryPayLiveStreamDetails
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询全额购购买详情")
    @GetMapping(value = "queryPayLiveStreamDetails")
    public ResultParam queryPayLiveStreamDetails(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryPayLiveStreamDetails( entity,request, response));
    }


    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/22 16:16
     * @ Description：查询购买详情
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询课程详情")
    @GetMapping(value = "queryInfoDetail")
    public ResultParam queryInfoDetail(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryInfoDetail( entity,request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/30 18:15
     * @ Description：查询机构收益列表
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询机构收益列表")
    @GetMapping(value = "queryMechanismOfflineDetails")
    public ResultParam queryMechanismOfflineDetails(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryMechanismOfflineDetails( entity,request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/30 18:15
     * @ Description：查询机构收益统计
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询机构收益统计")
    @GetMapping(value = "queryMechanismOfflineDetailsCount")
    public ResultParam queryMechanismOfflineDetailsCount(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryMechanismOfflineDetailsCount( entity,request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/30 18:15
     * @ Description：按支付状态查询订单
     * @ since: JDk1.8
     */
    @ApiOperation(value = "按支付状态查询订单")
    @GetMapping(value = "queryListPageByStatus")
    public ResultParam queryListPageByStatus(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryListPageByStatus( entity,request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/30 18:15
     * @ Description：退款
     * @ since: JDk1.8
     */
    @ApiOperation(value = "退款")
    @PostMapping(value = "tradeRefund")
    public ResultParam tradeRefund(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.tradeRefund( entity,request, response);
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
    public ResultParam updateInviteCouponCash(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.updateInviteCouponCash( entity,request, response);
    }


}