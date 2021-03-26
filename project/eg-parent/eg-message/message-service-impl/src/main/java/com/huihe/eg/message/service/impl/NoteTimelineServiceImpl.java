package com.huihe.eg.message.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.message.model.NoteTimelineEntity;
import com.huihe.eg.message.mybatis.dao.NoteTimelineMapper;
import com.huihe.eg.message.service.dao.NoteTimelineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NoteTimelineServiceImpl extends BaseFrameworkServiceImpl<NoteTimelineEntity, Long> implements NoteTimelineService {

    @Resource
    private NoteTimelineMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}