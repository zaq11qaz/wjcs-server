package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.MasterHistoryEntity;
import com.huihe.eg.user.service.dao.MasterHistoryService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 助学师评论操作记录
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="助学师评论操作记录可用接口说明",description="助学师评论操作记录可用接口说明",tags = {"助学师评论操作记录"})
@RestController
@RequestMapping("masterHistory")
public class MasterHistoryController extends BaseFrameworkController<MasterHistoryEntity, Long> {

    @Resource
    private MasterHistoryService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}