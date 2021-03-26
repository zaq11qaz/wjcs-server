package com.huihe.eg.message.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.message.model.SystemFeedBackEntity;
import com.huihe.eg.message.service.dao.SystemFeedBackService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 反馈记录
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="反馈记录可用接口说明",description="反馈记录可用接口说明",tags = {"反馈记录"})
@RestController
@RequestMapping("systemFeedBack")
public class SystemFeedBackController extends BaseFrameworkController<SystemFeedBackEntity, Long> {

    @Resource
    private SystemFeedBackService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}