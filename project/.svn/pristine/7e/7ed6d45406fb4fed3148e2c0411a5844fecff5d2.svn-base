package com.huihe.eg.message.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.message.model.HistoryEntity;
import com.huihe.eg.message.service.dao.HistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 历史操作记录
 *
 * @author zwx
 * @date 2019年11月26日11:21:54
 * @since JDK1.8
 */
@Api(value="历史操作记录可用接口说明",description="历史操作记录可用接口说明",tags = {"历史操作记录"})
@RestController
@RequestMapping("history")
public class HistoryController extends BaseFrameworkController<HistoryEntity, Long> {

    @Resource
    private HistoryService service;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Override
    public void init() {
        setBaseService(service);
    }
    /*
    @ApiOperation(value = "测试")
    @PostMapping("overseasAudit")
    public void HistoryController(){


    }

     */
    @RequestMapping("queryNoteInteraction")
    @ResponseBody
    @ApiOperation(value = "查询互动", notes = "查询互动")
    public Integer queryNoteInteraction(@RequestBody Long user_id) {
        return service.queryNoteInteraction(user_id);
    }

    @RequestMapping("queryHistoryId")
    @ResponseBody
    @ApiOperation(value = "根据id查询发起人id", notes = "根据id查询发起人id")
    public Integer queryHistoryId(@RequestBody Long id) {
        return service.queryHistoryId(id);
    }

    @RequestMapping("queryIsLike")
    @ResponseBody
    @ApiOperation(value = "查询是否点赞", notes = "查询是否点赞")
    public Boolean queryIsLike(@RequestBody @RequestParam("user_id")Long user_id,@RequestParam("app_id")Long app_id) {
        return service.queryIsLike(user_id,app_id);
    }
}