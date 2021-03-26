package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.huihe.eg.user.model.CurriculumRecordEntity;
import com.huihe.eg.user.model.UserOrderEntity;
import com.huihe.eg.user.service.dao.CurriculumRecordService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程安排记录
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="课程安排记录可用接口说明",description="课程安排记录可用接口说明",tags = {"课程安排记录"})
@RestController
@RequestMapping("curriculumRecord")
public class CurriculumRecordController extends BaseFrameworkController<CurriculumRecordEntity, Long> {

    @Resource
    private CurriculumRecordService service;
    @Override
    public void init() {
        setBaseService(service);
    }
    /**
     * 新增课节
     * @author zwx
     * @date 2019年11月25日17:52:49
     * @since JDK1.8
     */
    @ApiOperation(value = "新增课节", notes = "新增课节")
    @RequestMapping("insertCurriculum")
    @ResponseBody
    public void insertCurriculum(Long user_id, String type, Long group_id,Integer status) {
        service.insertCurriculum(user_id,type,group_id, status);
    }
}