package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.StudyCardEntity;
import com.huihe.eg.user.service.dao.StudyCardService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 学习卡详情列表
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="学习卡详情列表可用接口说明",description="学习卡详情列表可用接口说明",tags = {"学习卡详情列表"})
@RestController
@RequestMapping("studyCard")
public class StudyCardController extends BaseFrameworkController<StudyCardEntity, Long> {

    @Resource
    private StudyCardService service;
    @Override
    public void init() {
        setBaseService(service);
    }
    /**
     * 查询我的助学师学习卡
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(
            value = "查询我的助学师学习卡",
            httpMethod = "GET",
            notes = "查询我的助学师学习卡"
    )
    @GetMapping({"queryMyStudyCard"})
    public ResultParam queryMyStudyCard(StudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryMyStudyCard(param,request,response));
    }

    /**
     * 查询学习卡商品列表
     * @author: zwy
     * @date 2020/08/04 10:41
     * @since: JDK1.8
     */
    @ApiOperation(
            value = "查询学习卡商品列表",
            httpMethod = "GET",
            notes = "查询学习卡商品列表"
    )
    @GetMapping({"queryStudyCardList"})
    public ResultParam queryStudyCardList(StudyCardEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryStudyCardList(param,request,response));
    }
}