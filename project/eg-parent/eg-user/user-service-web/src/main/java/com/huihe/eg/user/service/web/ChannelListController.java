package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.ChannelListEntity;
import com.huihe.eg.user.service.dao.ChannelListService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ Author     ：zwy
 * @ Date       ：2021/1/5 13:31
 * @ Description：渠道列表
 * @ since: JDk1.8
 */
@Api(value="渠道列表可用接口说明",description="渠道列表可用接口说明",tags = {"渠道列表"})
@RestController
@RequestMapping("channelList")
public class ChannelListController extends BaseFrameworkController<ChannelListEntity, Integer> {

    @Resource
    private ChannelListService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}