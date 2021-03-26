package com.huihe.eg.news.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.news.model.CuriosityCommentEntity;
import com.huihe.eg.news.model.CuriosityEntity;
import com.huihe.eg.news.service.dao.CuriosityCommentService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="好奇观点评论可用接口说明",description="好奇观点评论可用接口说明",tags = {"好奇观点评论"})
@RestController
@RequestMapping("curiositycomment")
public class CuriosityCommentController extends BaseFrameworkController<CuriosityCommentEntity, Long> {

    @Resource
    private CuriosityCommentService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 查询最热评论
     * 2019年1月15日
     * zwx
     */
    @ApiOperation(value = "查询最热评论", httpMethod = "GET", notes = "查询最热评论")
    @GetMapping({"queryHotCommentListPage"})
    public ResultParam queryHotCommentListPage(CuriosityCommentEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryHotCommentListPage(param,request,response));

    }
}