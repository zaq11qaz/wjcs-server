package com.huihe.eg.message.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.json.JSONUtils;
import com.huihe.eg.message.model.GroupRecordingEntity;
import com.huihe.eg.message.model.MessageGroupEntity;
import com.huihe.eg.message.service.dao.GroupRecordingService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="群课堂录制记录可用接口说明",description="群课堂录制记录可用接口说明",tags = {"群课堂录制记录"})
@RestController
@RequestMapping("groupRecording")
public class GroupRecordingController extends BaseFrameworkController<GroupRecordingEntity, Long> {

    @Resource
    private GroupRecordingService service;
    @Override
    public void init() {
        setBaseService(service);
    }
    @RequestMapping("endCourseNotice")
    @ResponseBody
    public void endCourseNotice(@RequestBody Long appointment_id, HttpServletRequest request, HttpServletResponse response){
        service.endCourseNotice(appointment_id,request,response);
    }
    @RequestMapping("queryRecordingByAppointmentId")
    @ResponseBody
    public String queryRecordingByAppointmentId(@RequestBody Long appointment_id, HttpServletRequest request, HttpServletResponse response){
        GroupRecordingEntity groupRecordingEntity=new GroupRecordingEntity();
        groupRecordingEntity.setAppointment_id(appointment_id);
        groupRecordingEntity.setPageSize(1);
        List<GroupRecordingEntity> groupRecordingEntities=service.queryListPage(groupRecordingEntity,request,response);
        if(groupRecordingEntities!=null&&groupRecordingEntities.size()>0){
            return JSONUtils.obj2Json(groupRecordingEntities.get(0)).toString();
        }else{
            return "";
        }
    }
    @RequestMapping("updateWatchCount")
    @ResponseBody
    public void updateWatchCount(@RequestBody Long appointment_id, HttpServletRequest request, HttpServletResponse response){
        service.updateWatchCount(appointment_id,request,response);
    }

    @RequestMapping("queryRecordingUrl")
    @ResponseBody
    public String queryRecordingUrl(@RequestBody Long appointment_id, HttpServletRequest request, HttpServletResponse response){
        return  service.queryRecordingUrl(appointment_id,request,response);
    }

    @RequestMapping("queryRecordingUrlTeachPaypal")
    @ResponseBody
    public String queryRecordingUrlTeachPaypal( Long appointment_id, Boolean is_teach_paypal , HttpServletRequest request, HttpServletResponse response){
        return  service.queryRecordingUrlTeachPaypal(appointment_id,is_teach_paypal,request,response);
    }

}