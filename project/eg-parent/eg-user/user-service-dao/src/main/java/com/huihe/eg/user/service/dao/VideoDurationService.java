package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.VideoDurationEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface VideoDurationService extends BaseFrameworkService<VideoDurationEntity, Long> {
    //void timeTaskVideoChat();
    //查询是否有非正常断开的视频
    List<VideoDurationEntity> queryVideoReconnection(VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response);
    //接通视频
    ResultParam videoDurationConnect(VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertVideoOrder(VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response);
    ResultParam pushVideo(VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam pushVideoIos(VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response);
}