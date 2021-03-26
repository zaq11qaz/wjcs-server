package com.huihe.eg.message.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.message.model.NoteTimelineEntity;
import com.huihe.eg.message.service.dao.NoteTimelineService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 友圈时间轴
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value = "友圈时间轴可用接口说明", description = "友圈时间轴可用接口说明", tags = {"友圈时间轴"})
@RestController
@RequestMapping("noteTimeline")
public class NoteTimelineController extends BaseFrameworkController<NoteTimelineEntity, Long> {

    @Resource
    private NoteTimelineService service;

    @Override
    public void init() {
        setBaseService(service);
    }
}