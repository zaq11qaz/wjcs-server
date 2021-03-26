package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.ClassCardEntity;
import com.huihe.eg.user.service.dao.ClassCardService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 直播课程学习卡商品列表
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="直播课程学习卡商品列表可用接口说明",description="直播课程学习卡商品列表可用接口说明",tags = {"直播课程学习卡商品列表"})
@RestController
@RequestMapping("classcard")
public class ClassCardController extends BaseFrameworkController<ClassCardEntity, Long> {

    @Resource
    private ClassCardService service;
    @Override
    public void init() {
        setBaseService(service);
    }
    /*
        我的学习卡-直播课堂学习卡
     */
    @ApiOperation(value = "我的学习卡-直播课堂学习卡")
    @PostMapping("queryMyClassCard")
    public ResultParam queryMyClassCard(@RequestBody ClassCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryMyClassCard(param,request,response));
    }

    /**
     * 查询直播学习卡列表
     * @author: zwy
     * @date 2020/08/04 11:38
     * @since: JDK1.8
     */
    @ApiOperation(value = "查询直播学习卡列表")
    @GetMapping("queryClassCardList")
    public ResultParam queryClassCardList(ClassCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryClassCardList(param,request,response));
    }

}