package com.huihe.eg.message.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.message.model.NoteCommentEntity;
import com.huihe.eg.message.service.dao.NoteCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 友圈评论表
 *
 * @author zwx
 * @date 2019年11月26日11:21:54
 * @since JDK1.8
 */
@Api(value="友圈评论表可用接口说明",description="友圈评论表可用接口说明",tags = {"友圈评论表"})
@RestController
@RequestMapping("noteComment")
public class NoteCommentController extends BaseFrameworkController<NoteCommentEntity, Long> {

    @Resource
    private NoteCommentService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    @RequestMapping("queryCommentAddCount")
    @ResponseBody
    public Integer queryCommentAddCount(@RequestBody Long user_id) {
        return service.queryCommentAddCount(user_id);
    }

    /**
     * 动态详情评论分页查询
     * @author zwx
     * @date 2019年11月26日11:23:04
     * @since JDK1.8
     */
    @ApiOperation(value = "动态详情评论分页查询", httpMethod = "GET", notes = "动态详情评论分页查询")
    @GetMapping({"queryDetailsListPage"})
    public ResultParam queryDetailsListPage(NoteCommentEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryDetailsListPage(param,request,response));
    }


    /**
     * @ Author     ：zwy
     * @ Date       ：2021/4/1 17:08
     * @ Description：查询评论列表
     * @ since: JDk1.8
     */
    @ApiOperation(value = "动态详情评论分页查询", httpMethod = "GET", notes = "动态详情评论分页查询")
    @GetMapping({"queryManagerListPageSystem"})
    public ResultParam queryManagerListPageSystem(NoteCommentEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryManagerListPageSystem(param,request,response));
    }
}