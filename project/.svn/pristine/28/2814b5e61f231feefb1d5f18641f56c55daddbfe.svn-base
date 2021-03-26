package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.MasterSummaryEntity;
import com.huihe.eg.user.service.dao.MasterSummaryService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 助学师课程总结
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="助学师课程总结可用接口说明",description="助学师课程总结可用接口说明",tags = {"助学师课程总结"})
@RestController
@RequestMapping("masterSummary")
public class MasterSummaryController extends BaseFrameworkController<MasterSummaryEntity, Long> {

    @Resource
    private MasterSummaryService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/24 17:00
     * @ Description：线上课写总结
     * @ since: JDk1.8
     */
    @ApiOperation(value = "线上课写总结")
    @GetMapping("insertSummary")
    public ResultParam insertSummary(MasterSummaryEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.insertSummary(param, request, response);
    }
    
    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/24 16:59
     * @ Description：线下课写总结
     * @ since: JDk1.8
     */
    @ApiOperation(value = "线下课写总结")
    @PostMapping("insertSummaryOffline")
    public ResultParam insertSummaryOffline(@RequestBody MasterSummaryEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.insertSummaryOffline(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/24 16:59
     * @ Description：查询总结列表
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询总结列表")
    @GetMapping("querySummaryList")
    public ResultParam querySummaryList(MasterSummaryEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.querySummaryList(param, request, response));
    }



}