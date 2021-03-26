package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.MasterCommentEntity;
import com.huihe.eg.user.service.dao.MasterCommentService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 助学师打分评论列表
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="打分评论列表可用接口说明",description="打分评论列表可用接口说明",tags = {"打分评论列表"})
@RestController
@RequestMapping("masterComment")
public class MasterCommentController extends BaseFrameworkController<MasterCommentEntity, Long> {

    @Resource
    private MasterCommentService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/27 11:42
     * @ Description：点赞
     * @since: JDk1.8
     */
    @ApiOperation(value = "点赞", notes = "点赞")
    @RequestMapping("updateCommentLikeCount")
    @ResponseBody
    public void updateLikeCount(@RequestBody Long id, HttpServletRequest request, HttpServletResponse response) {
        service.updateCommentLikeCount(id,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/27 11:42
     * @ Description：取消点赞
     * @since: JDk1.8
     */
    @ApiOperation(value = "取消点赞", notes = "取消点赞")
    @RequestMapping("updateCommentLikeCountCancel")
    @ResponseBody
    public void updateLikeCountCancel(@RequestBody Long id, HttpServletRequest request, HttpServletResponse response) {
        service.updateCommentLikeCountCancel(id,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/27 11:42
     * @ Description：分享
     * @since: JDk1.8
     */
    @ApiOperation(value = "分享", notes = "分享")
    @RequestMapping("updateCommentShareCount")
    @ResponseBody
    public void updateShareCount(@RequestBody Long id, HttpServletRequest request, HttpServletResponse response) {
        service.updateCommentShareCount(id,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/27 11:42
     * @ Description：点赞
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询机构平均分", notes = "查询机构平均分")
    @GetMapping("queryMechanismAverageScore")
    public ResultParam queryMechanismAverageScore(MasterCommentEntity masterCommentEntity, HttpServletRequest request, HttpServletResponse response) {
        return  ResultUtil.success(service.queryMechanismAverageScore( masterCommentEntity,request,response));
    }

    @ApiOperation(value = "查询机构平均分", notes = "查询机构平均分")
    @GetMapping("queryMechanismList")
    public ResultParam queryMechanismList(MasterCommentEntity masterCommentEntity, HttpServletRequest request, HttpServletResponse response) {
        return  ResultUtil.success(service.queryMechanismList( masterCommentEntity,request,response));
    }

    @ApiOperation(value = "查询机构平均分", notes = "查询机构平均分")
    @GetMapping("queryByMessage")
    public ResultParam queryByMessage(MasterCommentEntity masterCommentEntity, HttpServletRequest request, HttpServletResponse response) {
        return  service.queryByMessage( masterCommentEntity,request,response);
    }

}