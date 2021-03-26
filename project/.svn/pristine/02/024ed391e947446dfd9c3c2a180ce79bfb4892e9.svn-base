package com.huihe.eg.message.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.message.model.GroupVideoEntity;
import com.huihe.eg.message.service.dao.GroupVideoService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="群课堂视频录制文件记录可用接口说明",description="群课堂视频录制文件记录可用接口说明",tags = {"群课堂视频录制文件记录"})
@RestController
@RequestMapping("groupVideo")
public class GroupVideoController extends BaseFrameworkController<GroupVideoEntity, Long> {

    @Resource
    private GroupVideoService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}