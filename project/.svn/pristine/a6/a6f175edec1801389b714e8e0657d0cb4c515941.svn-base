package com.huihe.eg.news.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.news.model.HistoryEntity;
import com.huihe.eg.news.service.dao.HistoryService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value="历史操作记录可用接口说明",description="历史操作记录可用接口说明",tags = {"历史操作记录"})
@RestController
@RequestMapping("history")
public class HistoryController extends BaseFrameworkController<HistoryEntity, Long> {

    @Resource
    private HistoryService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    @RequestMapping("queryCuriosityInteraction")
    public Integer queryCuriosityInteraction(@RequestBody Long user_id) {
        return service.queryCuriosityInteraction(user_id);
    }

    @ApiOperation(value = "根据id查询一条记录", notes = "根据id查询一条记录")
    @RequestMapping("queryHistoryId")
    public Integer queryHistoryById(@RequestBody Long id){
        this.init();
        return service.queryHistoryById(id);
    }
}