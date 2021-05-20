package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.MechanismExamScoreEntity;
import com.huihe.eg.user.service.dao.MechanismExamScoreService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(value="机构考试表可用接口说明",description="机构考试表可用接口说明",tags = {"机构考试表"})
@RestController
@RequestMapping("mechanismExamScore")
public class MechanismExamScoreController extends BaseFrameworkController<MechanismExamScoreEntity, Long> {

    @Resource
    private MechanismExamScoreService service;
    @Override
    public void init() {
        setBaseService(service);
    }


    /**
     * @ Author     ：zwy
     * @ Date       ：2021/5/20 15:01
     * @ Description：插入考试成绩评分
     * @ since: JDk1.8
     */
    @ApiOperation(value = "插入考试成绩评分")
    @PostMapping("insertExamScore")
    public ResultParam insertExamScore(@RequestBody MechanismExamScoreEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.insertExamScore(param,request,response);
    }
}