package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.LogOperationHistoryEntity;
import com.huihe.eg.user.service.dao.LogOperationHistoryService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ Author     ：zwy
 * @ Date       ：2021/1/5 13:31
 * @ Description：操作日志
 * @ since: JDk1.8
 */
@Api(value="操作日志可用接口说明",description="操作日志可用接口说明",tags = {"操作日志"})
@RestController
@RequestMapping("logOperationHistory")
public class LogOperationHistoryController extends BaseFrameworkController<LogOperationHistoryEntity, Integer> {

    @Resource
    private LogOperationHistoryService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}