package com.huihe.eg.message.service.impl.Thread;

import com.cy.framework.util.json.JSONUtils;
import com.huihe.eg.message.model.GroupRecordingEntity;
import com.huihe.eg.message.model.GroupVideoEntity;
import com.huihe.eg.message.mybatis.dao.GroupRecordingMapper;
import com.huihe.eg.message.mybatis.dao.GroupVideoMapper;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import com.tencentcloudapi.live.v20180801.LiveClient;
import com.tencentcloudapi.live.v20180801.models.*;
import com.tencentcloudapi.vod.v20180717.VodClient;

import com.tencentcloudapi.vod.v20180717.models.EditMediaRequest;
import com.tencentcloudapi.vod.v20180717.models.EditMediaResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//录制视频拼接
@Service
public class EditMediaThread implements  Runnable {
    private Long appointment_id;//课程id
    private GroupRecordingMapper groupRecordingMapper=null;
    private GroupVideoMapper groupVideoMapper=null;
    public void init(Long appointmentId,GroupRecordingMapper groupRecording,GroupVideoMapper videoMapper) {
        appointment_id = appointmentId;
        groupRecordingMapper = groupRecording;
        groupVideoMapper = videoMapper;

    }
    @Override
    public void run(){
        try{
            Thread.sleep(10000);
            Map<String,Object> map=new HashMap<>();
            List<GroupVideoEntity> groupVideoEntities=null;
            map.put("InputType","File");
            List<Map<String,Object>> fileInfos=new ArrayList<>();
            GroupRecordingEntity groupRecordingEntity=new GroupRecordingEntity();
            groupRecordingEntity.setAppointment_id(appointment_id);
            groupRecordingEntity.setPageSize(1);
            List<GroupRecordingEntity> groupRecordingEntities=groupRecordingMapper.queryListPage(groupRecordingEntity);
            if(groupRecordingEntities!=null&&groupRecordingEntities.size()>0){
                groupRecordingEntity=groupRecordingEntities.get(0);
                GroupVideoEntity groupVideoEntity=new GroupVideoEntity();
                groupVideoEntity.setRecording_id(groupRecordingEntity.getId());
                groupVideoEntity.setStatus(1);
                groupVideoEntities=groupVideoMapper.query(groupVideoEntity);
                for (GroupVideoEntity  videoEntity:groupVideoEntities){
                    Map<String,Object> stringObjectMap=new HashMap<>();
                    stringObjectMap.put("FileId",videoEntity.getRecord_file_id());
                    fileInfos.add(stringObjectMap);
                }
            }
           if(groupVideoEntities!=null&&groupVideoEntities.size()>1){
               map.put("FileInfos",fileInfos);
               Map<String,Object> outputConfigMaps=new HashMap<>();
               outputConfigMaps.put("MediaName",appointment_id.toString());
               outputConfigMaps.put("Type","mp4");
               map.put("OutputConfig",outputConfigMaps);
               Credential cred = new Credential("AKIDuhAVim8M0Xs7A0kAWgu57zhDXhfRHg5D", "8D7C8xc6PdVGAvLadaPnr381iFfuAzio");

               HttpProfile httpProfile = new HttpProfile();
               httpProfile.setEndpoint("vod.tencentcloudapi.com");

               ClientProfile clientProfile = new ClientProfile();
               clientProfile.setHttpProfile(httpProfile);
               VodClient client = new VodClient(cred, "ap-shanghai", clientProfile);
               // String params = "{\"InputType\":\"Stream\",\"StreamInfos\":[{\"StreamId\":\"8888\"},{\"StreamId\":\"999\"}]}";
               EditMediaRequest req = EditMediaRequest.fromJsonString(JSONUtils.obj2Json(map).toString(), EditMediaRequest.class);
               EditMediaResponse resp = client.EditMedia(req);
               System.out.println(EditMediaRequest.toJsonString(resp));
               if(resp.getTaskId()!=null&& !resp.getTaskId().equals("")){
                   for (GroupVideoEntity  videoEntity:groupVideoEntities){
                       videoEntity.setStatus(2);
                       groupVideoMapper.updateByPrimaryKeySelective(videoEntity);

                   }
                   groupRecordingEntity.setStatus(2);
                   groupRecordingMapper.updateByPrimaryKeySelective(groupRecordingEntity);
                   new Thread().start();{
                       this.DeleteLiveRecordRule(groupRecordingEntity.getStream_id());
                   }

               }
           }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }
    //删除录播规则
    public void DeleteLiveRecordRule(String stream_Name){
        try{
            Credential cred = new Credential("AKIDuhAVim8M0Xs7A0kAWgu57zhDXhfRHg5D", "8D7C8xc6PdVGAvLadaPnr381iFfuAzio");
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("live.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            LiveClient client = new LiveClient(cred, "ap-shanghai", clientProfile);
            Map<String,Object> map=new HashMap<>();
            map.put("DomainName","58839.livepush.myqcloud.com");
            map.put("AppName","live");
            map.put("StreamName",stream_Name);
            DeleteLiveRecordRuleRequest req = DeleteLiveRecordRuleRequest.fromJsonString(JSONUtils.obj2Json(map).toString(), DeleteLiveRecordRuleRequest.class);
            DeleteLiveRecordRuleResponse resp = client.DeleteLiveRecordRule(req);
            System.out.println(DeleteLiveRecordRuleRequest.toJsonString(resp));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }
}
