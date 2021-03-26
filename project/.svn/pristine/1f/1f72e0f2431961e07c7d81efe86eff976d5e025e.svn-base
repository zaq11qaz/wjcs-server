package com.huihe.eg.message.service.impl.Thread;


import com.cy.framework.util.json.JSONUtils;
import com.huihe.eg.message.model.GroupRecordingEntity;
import com.huihe.eg.message.mybatis.dao.GroupRecordingMapper;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import com.tencentcloudapi.live.v20180801.LiveClient;

import com.tencentcloudapi.live.v20180801.models.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//创建录制模版
//创建录制任务
@Service
public class EstablishRecordThread implements  Runnable {

    private GroupRecordingEntity groupRecordingEntity;
    private String stream_Name;//流名称
    private GroupRecordingMapper groupRecordingMapper=null;
    public void init(GroupRecordingEntity groupRecording,String streamName,GroupRecordingMapper recordingMapper) {
        groupRecordingEntity = groupRecording;
        stream_Name = streamName;
        groupRecordingMapper = recordingMapper;
    }
    @Override
    public void run(){
        try{
            GroupRecordingEntity groupRecordingE=new GroupRecordingEntity();
            groupRecordingE.setAppointment_id(groupRecordingEntity.getAppointment_id());
            groupRecordingE.setPageSize(1);
            List<GroupRecordingEntity> groupRecordingEntities=groupRecordingMapper.queryListPage(groupRecordingE);
            if(groupRecordingEntities==null||groupRecordingEntities.size()==0){
                Credential cred = new Credential("AKIDuhAVim8M0Xs7A0kAWgu57zhDXhfRHg5D", "8D7C8xc6PdVGAvLadaPnr381iFfuAzio");
                Map<String,Object> map=new HashMap<>();
                HttpProfile httpProfile = new HttpProfile();
                httpProfile.setEndpoint("live.tencentcloudapi.com");
                ClientProfile clientProfile = new ClientProfile();
                clientProfile.setHttpProfile(httpProfile);
                LiveClient client = new LiveClient(cred, "ap-shanghai", clientProfile);
                map.put("DomainName","58839.livepush.myqcloud.com");
                map.put("AppName","live");
                map.put("TemplateId",11084);
                map.put("StreamName",stream_Name);
                CreateLiveRecordRuleRequest req = CreateLiveRecordRuleRequest.fromJsonString(JSONUtils.obj2Json(map).toString(), CreateLiveRecordRuleRequest.class);
                CreateLiveRecordRuleResponse resp = client.CreateLiveRecordRule(req);
                System.out.println(CreateLiveRecordRuleRequest.toJsonString(resp));
                groupRecordingEntity.setRequestId(resp.getRequestId());
                groupRecordingMapper.insertSelective(groupRecordingEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }
    public   void createRecordingTemplate(){
        try{

            Credential cred = new Credential("AKIDuhAVim8M0Xs7A0kAWgu57zhDXhfRHg5D", "8D7C8xc6PdVGAvLadaPnr381iFfuAzio");
            Map<String,Object> map=new HashMap<>();
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("live.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            LiveClient client = new LiveClient(cred, "ap-shanghai", clientProfile);
            map.put("DomainName","58839.livepush.myqcloud.com");
            map.put("AppName","live");
            map.put("TemplateId",11084);
            map.put("StreamName",stream_Name);
            CreateLiveRecordRuleRequest req = CreateLiveRecordRuleRequest.fromJsonString(JSONUtils.obj2Json(map).toString(), CreateLiveRecordRuleRequest.class);
            CreateLiveRecordRuleResponse resp = client.CreateLiveRecordRule(req);
            System.out.println(CreateLiveRecordRuleRequest.toJsonString(resp));
        }catch (Exception e){

        }
    }
}
