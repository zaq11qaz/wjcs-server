package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.UserClassCardEntity;
import com.huihe.eg.user.service.dao.UserClassCardService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 用户购买直播课程学习卡记录
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="用户购买直播课程学习卡记录可用接口说明",description="用户购买直播课程学习卡记录可用接口说明",tags = {"用户购买直播课程学习卡记录"})
@RestController
@RequestMapping("userClassCard")
public class UserClassCardController extends BaseFrameworkController<UserClassCardEntity, Long> {

    @Resource
    private UserClassCardService service;
    @Override
    public void init() {
        setBaseService(service);
    }
    /**
     * 修改默认使用
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "修改默认使用")
    @GetMapping("updateDefaultUse")
    public ResultParam updateDefaultUse(UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.updateDefaultUse(param,request,response);
    }
    /**
     * 后台-新增课程学习卡
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "后台-新增课程学习卡")
    @PostMapping("newlyAddedCard")
    public ResultParam newlyAddedCard(@RequestBody  UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.newlyAddedCard(param,request,response);
}
    /**
     * 学习卡购买统计
     * @author: zwy
     * @date 2020/07/13 14:12
     * @since: JDK1.8
     */
    @ApiOperation(value = "学习卡购买统计")
    @GetMapping("cardPayCount")
    public ResultParam cardPayCount(UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.cardPayCount(param,request,response));
    }

    /**
     * 条件查询直播学习卡
     * @author: zwy
     * @date 2020/07/13 14:12
     * @since: JDK1.8
     */
    @ApiOperation(value = "条件查询直播学习卡")
    @GetMapping("queryByMessage")
    public ResultParam queryByMessage(UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryByMessage(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/16 9:31
     * @ Description：查询体验学习卡信息
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询体验学习卡信息")
    @GetMapping("queryMyExperience")
    public ResultParam queryMyExperience(UserClassCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryMyExperience(param, request, response));
    }

}