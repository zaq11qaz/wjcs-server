package com.huihe.eg.message.service.impl.Thread;

import com.cy.framework.util.http.HttpsClientRequest;
import com.cy.framework.util.safe.MD5Util;
import com.huihe.eg.message.model.MessageGroupFlowEntity;
import com.huihe.eg.message.mybatis.dao.MessageGroupFlowMapper;
import com.huihe.eg.message.service.dao.MessageGroupFlowService;
import com.huihe.eg.message.service.impl.MessageGroupFlowServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
/**
 * 混流
 */
public class MixedFlowThread implements  Runnable {

    private  String group_id;
    private String group_type;
    private String stream_type;
    private MessageGroupFlowService messageGroupFlowService=null;
    public void init(String groupid,String type,String stream_Type,MessageGroupFlowService messageGroupFlow) {
        group_id = groupid;
        group_type = type;
        stream_type = stream_Type;
        messageGroupFlowService = messageGroupFlow;
    }
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void run(){
        try {
            Thread.sleep(5000);
            MessageGroupFlowEntity flowEntity = new MessageGroupFlowEntity();
            flowEntity.setGroup_id(group_id);
            flowEntity.setStatus(1);
            flowEntity.setPageSize(1);
            List<MessageGroupFlowEntity> messageGroupFlowEntities = messageGroupFlowService.getGroupAllFlow(flowEntity);
            Map<String, Object> mapParam = new HashMap<>();//腾讯云混流api参数
            mapParam.put("timestamp", System.currentTimeMillis() / 1000);
            mapParam.put("eventId", System.currentTimeMillis() / 100);
            Map<String, Object> mapInterface = new HashMap<>();//interface
            mapInterface.put("interfaceName", "Mix_StreamV2");
            Map<String, Object> mapPara = new HashMap<>();//para
            mapPara.put("app_id", 1258876522);
            if(messageGroupFlowEntities!=null&&messageGroupFlowEntities.size()>0){
                mapPara.put("interface", "mix_streamv2.start_mix_stream_advanced");//申请混流
            }else{
                mapPara.put("interface", "mix_streamv2.cancel_mix_stream");//取消混流
            }
            if("app".equalsIgnoreCase(stream_type)){
                mapPara.put("mix_stream_session_id", group_id);
                mapPara.put("output_stream_id", group_id);
            }else{
                   /* mapPara.put("mix_stream_session_id", group_id+"_ipad");
                    mapPara.put("output_stream_id", group_id+"_ipad");*/
            }
            mapPara.put("output_stream_type", 1);
            Map<String, Object> mapInput = new HashMap<>();
            mapInput.put("input_stream_id", System.currentTimeMillis()/1000);
            Map<String, Object> mapLayout = new HashMap<>();//图层
            mapLayout.put("image_layer", 1);
            if("app".equalsIgnoreCase(stream_type)){
                mapLayout.put("image_width", 810);
                mapLayout.put("image_height", 960);
            }else{
                    /*mapLayout.put("image_width", 150);
                    mapLayout.put("image_height", 80);*/
            }
            mapLayout.put("input_type", 3);
            mapLayout.put("color", "0x393F3F");
            mapInput.put("layout_params", mapLayout);
            List<Map<String, Object>> maps = new ArrayList<>();//input_stream_list
            maps.add(mapInput);
            if (messageGroupFlowEntities != null && messageGroupFlowEntities.size() > 0) {
                int i = 2;
                for (MessageGroupFlowEntity entity : messageGroupFlowEntities) {
                    Map<String, Object> mapFlow = new HashMap<>();
                    Map<String, Object> mapLayout1 = new HashMap<>();//图层
                    mapFlow.put("input_stream_id", entity.getStream_id());
                    mapLayout1.put("image_layer", i);
                    mapLayout1.put("image_width", 0.33);
                    mapLayout1.put("image_height", 0.5);
                    double x = (i - 2) * 0.33;
                    double y = 0;
                    if (x >= 0.99) {
                        x = (i - 5) * 0.33;
                        y = 0.5;
                    }
                    if("realtime_voice".equalsIgnoreCase(group_type)){
                        mapLayout1.put("input_type", 4);
                    }
                    mapLayout1.put("location_x", x);
                    mapLayout1.put("location_y", y);
                    mapFlow.put("layout_params", mapLayout1);
                    maps.add(mapFlow);
                    i++;
                }
                mapPara.put("input_stream_list", maps);
                mapInterface.put("para", mapPara);
                mapParam.put("interface", mapInterface);
                String str = null;
                long aLong = System.currentTimeMillis() / 1000 + 29000;//t
                String sign = MD5Util.GetMD5Code("4d8497c7c669e59b3a9c0de6fc481cda" + aLong);
                String url = "http://fcgi.video.qcloud.com/common_access?appid=1258876522&interface=Mix_StreamV2&t="+aLong+"&sign=" + sign;
                net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(mapParam);
                url = url.replaceAll("\r|\n", "");
                str = HttpsClientRequest.post(url, json.toString(), null, null);
                //Map<String, Object> resultMap = JSONUtils.obj2Map(str, null);
                logger.info(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MessageCallbackFactoryImpl  mixedFlow");
        }
    }
}
