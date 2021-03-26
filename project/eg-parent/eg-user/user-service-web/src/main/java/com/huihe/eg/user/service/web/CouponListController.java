package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.CouponListEntity;
import com.huihe.eg.user.service.dao.CouponListService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 国家语言列表
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="可领劵清单列表可用接口说明",description="可领劵清单列表可用接口说明",tags = {"可领劵清单列表"})
@RestController
@RequestMapping("couponList")
public class CouponListController extends BaseFrameworkController<CouponListEntity, Long> {

    @Resource
    private CouponListService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 使用优惠卷,锦鲤码
     * @author zwx
     * @date 2019年11月25日17:52:49
     * @since JDK1.8
     */
    /*
    @ApiOperation(value = "使用优惠卷,锦鲤码")
    @GetMapping("useDiscountVolume")
    public ResultParam useDiscountVolume(CouponListEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.useDiscountVolume(param,request,response);
    }

     */

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/17 14:08
     * @ Description：使用锦鲤码
     * @since: JDk1.8
     */
    @ApiOperation(value = "使用优惠卷,锦鲤码")
//    @PostMapping("useVoucher")
    @GetMapping("useDiscountVolume")
    public ResultParam useVoucher(CouponListEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.useVoucher(param,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/17 14:08
     * @ Description：使用锦鲤码
     * @since: JDk1.8
     */
    @ApiOperation(value = "更新券状态")
    @PostMapping("updateStatus")
    public ResultParam updateStatus(@RequestBody CouponListEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.updateStatus(param,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/17 14:08
     * @ Description：条件查询
     * @since: JDk1.8
     */
    @ApiOperation(value = "条件查询")
    @GetMapping("queryByMessage")
    public ResultParam queryByMessage(CouponListEntity param, HttpServletRequest request, HttpServletResponse response){
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
    public ResultParam queryCoupListCount(CouponListEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryCoupListCount(param,request,response));
    }

}