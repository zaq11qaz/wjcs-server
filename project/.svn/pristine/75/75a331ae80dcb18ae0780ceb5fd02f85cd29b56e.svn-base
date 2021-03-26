package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.UserStudyCardEntity;
import com.huihe.eg.user.service.dao.UserStudyCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户学习卡记录
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="用户学习卡记录可用接口说明",description="用户学习卡记录可用接口说明",tags = {"用户学习卡记录"})
@RestController
@RequestMapping("userStudyCard")
public class UserStudyCardController extends BaseFrameworkController<UserStudyCardEntity, Long> {

    @Resource
    private UserStudyCardService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 我的学习卡
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "我的学习卡")
    @GetMapping("queryStudyCore")
    public ResultParam queryStudyCore(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryStudyCore(param,request,response));
    }

    /**
     * 查询学习卡对应用户信息
     * @author: zwy
     * @date 2020/7/13 13:59
     * @since: JDK1.8
     */
    @ApiOperation(value = "查询学习卡对应用户信息")
    @GetMapping("queryUserByCard")
    public ResultParam queryUserByCard(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryUserByCard(param,request,response));
    }

    /**
     * 查询助学师学习卡日月年总
     * @author: zwy
     * @date 2020/07/13 19:52
     * @since: JDK1.8
     */
    @ApiOperation(value = "查询助学师学习卡点卡次卡")
    @GetMapping({"queryStudyEachTime"})
    public ResultParam queryStudyEachTime(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryStudyEachTime(param,request,response));
    }

    /**
     * 查询助学师学习卡日月年总
     * @author: zwy
     * @date 2020/07/13 19:52
     * @since: JDK1.8
     */
    @ApiOperation(value = "查询助学师学习卡点卡次卡")
    @GetMapping({"queryStudyEachTimeCount"})
    public ResultParam queryStudyEachTimeCount(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryStudyEachTimeCount(param,request,response));
    }

    /**
     * 根据条件查询
     * @author: zwy
     * @date 2020/07/13 20:15
     * @since: JDK1.8
     */
    @ApiOperation(value = "根据条件查询")
    @GetMapping({"queryByMessage"})
    public ResultParam queryByMessage(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryByMessage(param,request,response));
    }

    /**
     * 直播学习卡购买列表
     * @author: zwy
     * @date 2020/08/03 11:09
     * @since: JDK1.8
     */
    @ApiOperation(
            value = "直播学习卡购买列表",
            httpMethod = "GET",
            notes = "直播学习卡购买列表"
    )
    @GetMapping({"queryStudyList"})
    public ResultParam queryStudyList(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryStudyList(param,request,response));
    }

    /**
     * 学习中心
     * @author : zwy
     * 2020-08-24 03:31
     * @since JDK1.8
     */
    @ApiOperation(
            value = "学习中心",
            httpMethod = "GET",
            notes = "学习中心"
    )
    @GetMapping({"queryMyStudyCore"})
    public ResultParam queryMyStudyCore(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryMyStudyCore(param,request,response));
    }

   /**
    * @ Author     ：zwy
    * @ Date       ：2020/11/23 16:16
    * @ Description：学习中心v1.1
    * @ since: JDk1.8
    */
    @ApiOperation(
            value = "学习中心v1.1",
            httpMethod = "GET",
            notes = "学习中心v1.1"
    )
    @GetMapping({"queryMyStudyCoreNew"})
    public ResultParam queryMyStudyCoreNew(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryMyStudyCoreNew(param,request,response));
    }

    /**
     * 学习中心
     * @author : zwy
     * 2020-08-24 03:31
     * @since JDK1.8
     */
    @ApiOperation(
            value = "学习中心PC",
            httpMethod = "GET",
            notes = "学习中心PC"
    )
    @GetMapping({"queryMyStudyCoreListPage"})
    public ResultParam queryMyStudyCoreListPage(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryMyStudyCoreListPage(param,request,response));
    }

    /**
     * 查询购买商品列表
     * @author : zwy
     * 2020-08-24 03:31
     * @since JDK1.8
     */
    @ApiOperation(
            value = "查询购买商品列表",
            httpMethod = "GET",
            notes = "查询购买商品列表"
    )
    @GetMapping({"queryMyStudyCoreList"})
    public ResultParam queryMyStudyCoreList(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryMyStudyCoreList(param,request,response));
    }

    @ApiOperation(
            value = "查询购买专属学习卡列表",
            httpMethod = "GET",
            notes = "查询购买专属学习卡列表"
    )
    @GetMapping({"queryExclusiveCoursesList"})
    public ResultParam queryExclusiveCoursesList(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryExclusiveCoursesList(param,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/11 19:07
     * @ Description：查询是否购买过
     * @ since: JDk1.8
     */
    @ApiOperation(
            value = "查询购买专属学习卡列表",
            httpMethod = "GET",
            notes = "查询购买专属学习卡列表"
    )
    @GetMapping({"queryIsPayed"})
    public ResultParam queryIsPayed(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryIsPayed(param,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/16 9:23
     * @ Description：查询体验学习卡信息
     * @ since: JDk1.8
     */
    @ApiOperation(
            value = "查询体验学习卡信息",
            httpMethod = "GET",
            notes = "查询体验学习卡信息"
    )
    @GetMapping({"queryMyExperience"})
    public ResultParam queryMyExperience(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryMyExperience(param,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/14 17:16
     * @ Description：排课设置学生列表
     * @ since: JDk1.8
     */
    @ApiOperation(
            value = "排课设置学生列表",
            httpMethod = "GET",
            notes = "排课设置学生列表"
    )
    @GetMapping({"queryStudentList"})
    public ResultParam queryStudentList(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryStudentList(param,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/18 16:48
     * @ Description：查询支付详情
     * @ since: JDk1.8
     */
    @ApiOperation(
            value = "查询支付详情",
            httpMethod = "GET",
            notes = "查询支付详情"
    )
    @GetMapping({"queryPayInfo"})
    public ResultParam queryPayInfo(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.queryPayInfo(param,request,response);
    }


    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/22 16:45
     * @ Description：查询学员列表
     * @ since: JDk1.8
     */
    @ApiOperation(
            value = "查询学员列表",
            httpMethod = "GET",
            notes = "查询学员列表"
    )
    @GetMapping({"queryStudentInfo"})
    public ResultParam queryStudentInfo(UserStudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryStudentInfo(param,request,response));
    }

}