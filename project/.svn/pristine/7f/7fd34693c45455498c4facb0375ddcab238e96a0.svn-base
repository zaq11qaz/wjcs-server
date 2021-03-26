package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.VideoDurationEntity;
import com.huihe.eg.user.service.dao.VideoDurationService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 视频使用记录
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="视频使用记录可用接口说明",description="视频使用记录可用接口说明",tags = {"视频使用记录"})
@RestController
@RequestMapping("videoDuration")
public class VideoDurationController extends BaseFrameworkController<VideoDurationEntity, Long> {

    @Resource
    private VideoDurationService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 定时查询未挂断视频数据
     *//*
    @RabbitListener(queues = "timeTaskVideoChat" )
    public void timeTaskVideoChat()throws Exception{
        try {
            service.timeTaskVideoChat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    /**
     * 查询是否有非正常断开的视频
     *
     * @author zwx
     * @date 2019年11月25日18:01:05
     * @since JDK1.8
     */
    @ApiOperation(value = "查询是否有非正常断开的视频")
    @GetMapping("queryVideoReconnection")
    public ResultParam queryVideoReconnection(VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryVideoReconnection(param,request,response));
    }
    /**
     * 接通视频
     *
     * @author zwx
     * @date 2019年11月25日18:01:05
     * @since JDK1.8
     */
    @ApiOperation(value = "接通视频")
    @GetMapping("videoDurationConnect")
    public ResultParam videoDurationConnect(VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.videoDurationConnect(param,request,response));
    }
    /**
     * 视频扣费
     *
     * @author zwx
     * @date 2019年11月25日18:01:05
     * @since JDK1.8
     */
    @ApiOperation(value = "视频扣费")
    @PostMapping("insertVideoOrder")
    public ResultParam insertVideoOrder(@RequestBody VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.insertVideoOrder(param,request,response);
    }
    /**
     * 语音视频聊天提醒
     *
     * @author zwx
     * @date 2019年11月25日18:01:05
     * @since JDK1.8
     */
    @ApiOperation(value = "一对一语音视频聊天提醒")
    @PostMapping("pushVideo")
    public ResultParam pushVideo(@RequestBody VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.pushVideo(param,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/25 17:52
     * @ Description：一对一语音视频聊天提醒
     * @ since: JDk1.8
     */
    @ApiOperation(value = "一对一语音视频聊天提醒")
    @PostMapping("pushVideoIos")
    public ResultParam pushVideoIos(@RequestBody VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.pushVideoIos(param,request,response);
    }
}