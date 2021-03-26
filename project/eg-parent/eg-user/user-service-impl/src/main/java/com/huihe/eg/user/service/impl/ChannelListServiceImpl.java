package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.ChannelListEntity;
import com.huihe.eg.user.mybatis.dao.ChannelListMapper;
import com.huihe.eg.user.service.dao.ChannelListService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelListServiceImpl extends BaseFrameworkServiceImpl<ChannelListEntity, Integer> implements ChannelListService {

    @Resource
    private ChannelListMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(ChannelListEntity channelListEntity, HttpServletRequest request, HttpServletResponse response) {
        ChannelListEntity channelListEntity1 = new ChannelListEntity();
        channelListEntity1.setName(channelListEntity.getName());
        channelListEntity1.setPageSize(1);
        List<ChannelListEntity> query = mapper.queryListPage(channelListEntity1);
        if (query!=null&&query.size()>0){
            channelListEntity.setId(query.get(0).getId());
            return super.update(channelListEntity,request,response);
        }
        return super.insert(channelListEntity, request, response);
    }
}