package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.ClassRecordEntity;
import com.huihe.eg.user.service.dao.ClassRecordService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 直播课堂学习卡使用记录
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="直播课堂学习卡使用记录可用接口说明",description="直播课堂学习卡使用记录可用接口说明",tags = {"直播课堂学习卡使用记录"})
@RestController
@RequestMapping("classRecord")
public class ClassRecordController extends BaseFrameworkController<ClassRecordEntity, Long> {

    @Resource
    private ClassRecordService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 查询是否第一次进群
     * @author zwx
     * @date 2019年11月25日17:52:49
     * @since JDK1.8
     */
    @ApiOperation(value = "查询是否第一次进群")
    @GetMapping("queryProgressiveGroup")
    public ResultParam queryProgressiveGroup(ClassRecordEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.queryProgressiveGroup(param,request,response);
    }

    /**
     * 等待评论列表
     * @author zwx
     * @date 2020年5月8日14:23:28
     * @since JDK1.8
     */
    @ApiOperation(value = "等待评论列表")
    @GetMapping("queryStayListPage")
    public ResultParam queryStayListPage(ClassRecordEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success( service.queryStayListPage(param,request,response));
    }
    /**
     * 查询直播课堂扣费记录
     * @author zwx
     * @date 2020年5月8日14:23:28
     * @since JDK1.8
     */
    @ApiOperation(value = "查询直播课堂扣费记录")
    @GetMapping("queryHistoryListPage")
    public ResultParam queryHistoryListPage(ClassRecordEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success( service.queryHistoryListPage(param,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/25 14:11
     * @ Description：条件查询
     * @since: JDk1.8
     */
    @ApiOperation(value = "条件查询")
    @GetMapping("queryByMessage")
    public ResultParam queryByMessage(ClassRecordEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success( service.queryByMessage(param,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/3 10:34
     * @ Description：新增课程观看记录
     * @ since: JDk1.8
     */
    @ApiOperation(value = "新增课程观看记录")
    @PostMapping("insertViewRecordings")
    public ResultParam insertViewRecordings(@RequestBody ClassRecordEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.insertViewRecordings(param,request,response);
    }

}