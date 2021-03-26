package com.huihe.eg.message.service.web.callback;

import com.cy.framework.util.json.JSONUtils;
import com.huihe.eg.message.service.impl.callback.MessageCallbackFactoryImpl;


import io.swagger.annotations.Api;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
@Api(value="TIM回调可用接口说明",description="TIM回调可用接口说明",tags = {"TIM回调"})
@RestController
@RequestMapping("messageCallback")
public class MessageCallbackController {

    public org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private MessageCallbackFactoryImpl bean;


    @PostMapping("TIMCallbackCommand")
    public Object TIMCallbackCommand(HttpServletRequest request){
        String responseMsg=null;
        Object str=null;
        Map<String,Object> map=new HashMap<>();
        try {
            StringBuilder sb = new StringBuilder(2000);
            InputStream is = request.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            // 读取HTTP请求内容
            String buffer = null;
            while ((buffer = br.readLine()) != null) {
                // 在页面中显示读取到的请求参数
                sb.append(buffer);
            }
            responseMsg = sb.toString().trim();
            responseMsg=responseMsg.replaceAll("\\\\","");
            map=JSONUtils.json2Map(responseMsg);
            if(map.containsKey("CallbackCommand")){
                String callbackCommand=map.get("CallbackCommand").toString();
                String[] callbackUrl=callbackCommand.split("\\.");
                Method method = bean.getClass().getMethod(
                        callbackUrl[0]+callbackUrl[1],Map.class);
                str = method.invoke(bean, map);
            }
            logger.info("服务器响应报文:【" + responseMsg+"】");
        }catch (Exception e){
            e.printStackTrace();
            logger.info("TIMCallbackCommand:   "+responseMsg);
        }
        return str;
    }
    //断流
    @PostMapping("cutOff")
    public void cutOff(HttpServletRequest request){
        String responseMsg=null;
        Object str=null;
        Map<String,Object> map=new HashMap<>();
        try {
            StringBuilder sb = new StringBuilder(2000);
            System.out.println(request.getInputStream());
            InputStream is = request.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            // 读取HTTP请求内容
            String buffer = null;
            while ((buffer = br.readLine()) != null) {
                // 在页面中显示读取到的请求参数
                sb.append(buffer);
            }
            responseMsg = sb.toString().trim();
            map=JSONUtils.json2Map(responseMsg);
            Method method = bean.getClass().getMethod(
                    "plugCutOff",Map.class);
            method.invoke(bean, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("断流响应报文:【" + JSONUtils.obj2Json(responseMsg.toString().trim()).toString()+"】");
    }
    //推流
    @PostMapping("plugFlow")
    public void plugFlow(HttpServletRequest request){

        String responseMsg=null;
        Object str=null;
        Map<String,Object> map=new HashMap<>();
        try {
            StringBuilder sb = new StringBuilder(2000);//sb 接收到的请求参数
            InputStream is = request.getInputStream();//输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));//使用utf-8接收
            // 读取HTTP请求内容
            String buffer = null;
            while ((buffer = br.readLine()) != null) {//读一行如果有
                // 在页面中显示读取到的请求参数
                sb.append(buffer);//拼接sb
            }
            responseMsg = sb.toString().trim();//去空格
            map=JSONUtils.json2Map(responseMsg);//转map
            Method method = bean.getClass().getMethod(
                    "plugCutOff",Map.class);
            method.invoke(bean, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("推流响应报文:【" + JSONUtils.obj2Json(responseMsg.toString().trim()).toString()+"】");
    }
    //录制
    @PostMapping("recordingRecord")
    public void recordingRecord(HttpServletRequest request){

        String responseMsg=null;
        Object str=null;
        Map<String,Object> map=new HashMap<>();
        try {
            StringBuilder sb = new StringBuilder(2000);
            InputStream is = request.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            // 读取HTTP请求内容
            String buffer = null;
            while ((buffer = br.readLine()) != null) {
                // 在页面中显示读取到的请求参数
                sb.append(buffer);
            }
            responseMsg = sb.toString().trim();
            map=JSONUtils.json2Map(responseMsg);
            System.out.println("RECORDINGRECORD:"+map);
            Method method = bean.getClass().getMethod(
                    "recordingRecord",Map.class);
            method.invoke(bean, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("录制响应报文:【" + JSONUtils.obj2Json(responseMsg.toString().trim()).toString()+"】");
    }
    //拼接
    @PostMapping("splicingCallback")
    public void splicingCallback(HttpServletRequest request){
        String responseMsg=null;
        Map<String,Object> map=new HashMap<>();
        try {
            StringBuilder sb = new StringBuilder(2000);
            InputStream is = request.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            // 读取HTTP请求内容
            String buffer = null;
            while ((buffer = br.readLine()) != null) {
                // 在页面中显示读取到的请求参数
                sb.append(buffer);
            }
            responseMsg = sb.toString().trim();
            map=JSONUtils.json2Map(responseMsg);
            System.out.println("SplicingCallback:"+map);
            Method method = bean.getClass().getMethod(
                    "splicingCallback",Map.class);
            method.invoke(bean, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("拼接响应报文:【" + JSONUtils.obj2Json(responseMsg.toString().trim()).toString()+"】");
    }
}
