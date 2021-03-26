package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.UserRecommenderEntity;
import com.huihe.eg.user.service.dao.UserRecommenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @ Author     ：zwy
 * @ Date       ：2021/1/5 13:29
 * @ Description：机构招募师列表
 * @ since: JDk1.8
 */
@Api(value="机构招募师列表可用接口说明",description="机构招募师列表可用接口说明",tags = {"机构招募师列表"})
@RestController
@RequestMapping("userRecommender")
public class UserRecommenderController extends BaseFrameworkController<UserRecommenderEntity, Long> {

    @Resource
    private UserRecommenderService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 后台 认证中心 机构招募师审核
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "后台 认证中心 机构招募师审核")
    @PostMapping("recommenderAudit")
    public ResultParam recommenderAudit(@RequestBody UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.recommenderAudit(param, request, response);
    }

    /**
     * 计算收益
     * @author : zwy
     * 2020-08-26 09:25
     * @since JDK1.8
     */
    @ApiOperation(value = "计算收益")
    @PostMapping("queryEarningsThisMonth")
    public ResultParam queryEarningsThisMonth(@RequestBody UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryEarningsThisMonth(param, request, response);
    }

    /**
     * 机构推荐官申请
     * @author: zwy
     * @date: 13:48 2020/9/2
     * @since: JDk1.8
     */
    @ApiOperation(value = "机构推荐官申请")
    @PostMapping("insertMechismRecommender")
    public ResultParam insertMechismRecommender(@RequestBody UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.insertMechismRecommender(param, request, response);
    }
    
    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/12 13:24
     * @ Description：机构推荐官详情
     * @since: JDk1.8
     */
    @ApiOperation(value = "机构推荐官详情")
    @GetMapping("queryMyMechanismRecommenderInfo")
    public ResultParam queryMyMechanismRecommenderInfo(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMyMechanismRecommenderInfo(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/10/14 17:12
     * @ Description：推荐官认证统计
     * @since: JDk1.8
     */
    @ApiOperation(value = "推荐官认证统计")
    @GetMapping("queryRecommenderCount")
    public ResultParam queryRecommenderCount(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryRecommenderCount(param, request, response));
    }

    /**
     * 根据条件查询课程
     *
     * @author: zwy
     * @date 2020/07/21 21:23
     * @since: JDK1.8
     */
    @ApiOperation(value = "根据条件查询课程")
    @GetMapping("queryByMessage")
    public ResultParam queryByMessage(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryByMessage(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/10/14 19:43
     * @ Description：推荐官统计
     * @since: JDk1.8
     */
    @ApiOperation(value = "推荐官统计")
    @GetMapping("queryRecommenderListCount")
    public ResultParam queryRecommenderListCount(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryRecommenderListCount(param, request, response));
    }



}