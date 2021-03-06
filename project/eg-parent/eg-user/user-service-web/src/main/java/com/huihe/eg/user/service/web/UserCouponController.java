package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.RechargeRecordEntity;
import com.huihe.eg.user.model.UserCouponEntity;
import com.huihe.eg.user.service.dao.UserCouponService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 用户优惠劵领取记录
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="用户优惠劵领取记录可用接口说明",description="用户优惠劵领取记录可用接口说明",tags = {"用户优惠劵领取记录"})
@RestController
@RequestMapping("userCoupon")
public class UserCouponController extends BaseFrameworkController<UserCouponEntity, Long> {

    @Resource
    private UserCouponService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 使用锦鲤码
     * @author : zwy
     * 2020-08-19 02:00
     * @since JDK1.8
     */
    @ApiOperation(value = "使用锦鲤码")
    @GetMapping("useCoupon")
    public ResultParam useCoupon(UserCouponEntity userCouponEntity, HttpServletRequest request, HttpServletResponse response){
        return service.useCoupon(userCouponEntity,request,response);
    }

    /**
     * 查看我拥有的券
     * @author : zwy
     * 2020-08-19 03:09
     * @since JDK1.8
     */
    @ApiOperation(value = "查看我拥有的券")
    @GetMapping("queryMyCoupon")
    public ResultParam queryMyCoupon(UserCouponEntity userCouponEntity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryMyCoupon(userCouponEntity,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/17 14:08
     * @ Description：条件查询
     * @since: JDk1.8
     */
    @ApiOperation(value = "条件查询")
    @GetMapping("queryByMessage")
    public ResultParam queryByMessage(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryByMessage(param,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/17 14:08
     * @ Description：统计
     * @since: JDk1.8
     */
    @ApiOperation(value = "统计")
    @GetMapping("queryCoupListCount")
    public ResultParam queryCoupListCount(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryCoupListCount(param,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/22 14:03
     * @ Description：用户领取优惠券
     * @ since: JDk1.8
     */
    @ApiOperation(value = "用户领取优惠券")
    @PostMapping("insertUserCollection")
    public ResultParam insertUserCollection(@RequestBody UserCouponEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.insertUserCollection(param,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/3/2 11:19
     * @ Description：已领取优惠券统计
     * @ since: JDk1.8
     */
    @ApiOperation(value = "已领取优惠券统计")
    @GetMapping("queryUserCoupCount")
    public ResultParam queryUserCoupCount(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryUserCoupCount(param,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/3/2 11:19
     * @ Description：兑换学习卡
     * @ since: JDk1.8
     */
    @ApiOperation(value = "兑换学习卡")
    @PostMapping("insertStudyCardByCoup")
    public ResultParam insertStudyCardByCoup(@RequestBody UserCouponEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.insertStudyCardByCoup(param,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/3/2 11:19
     * @ Description：查询详情
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询详情")
    @GetMapping("queryDetail")
    public ResultParam queryDetail(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.queryDetail(param,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/3/2 11:19
     * @ Description：绑定
     * @ since: JDk1.8
     */
    @ApiOperation(value = "绑定")
    @GetMapping("updateBind")
    public ResultParam updateBind(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.updateBind(param,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/3/2 11:19
     * @ Description：到店签到
     * @ since: JDk1.8
     */
    @ApiOperation(value = "到店签到")
    @PostMapping("updateCheckIn")
    public ResultParam updateCheckIn(@RequestBody UserCouponEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.updateCheckIn(param,request,response);
    }
    /**
     * @ Author     ：zwy
     * @ Date       ：2021/3/2 11:19
     * @ Description：到店签到
     * @ since: JDk1.8
     */
    @ApiOperation(value = "到店签到")
    @PostMapping("insertUserCoupon198")
    public ResultParam insertUserCoupon198(@RequestBody RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.insertUserCoupon198(param,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/3/2 11:19
     * @ Description：购买77券
     * @ since: JDk1.8
     */
    /**
    @ApiOperation(value = "购买77券")
    @PostMapping("insertUserCoupon77")
    public ResultParam insertUserCoupon77(@RequestBody RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response){
        return service.insertUserCoupon77(rechargeRecordEntity);
    }
    */

}