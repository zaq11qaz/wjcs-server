package com.huihe.eg.authorization.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.authorization.model.LogEntity;
import com.huihe.eg.authorization.mybatis.dao.LogMapper;
import com.huihe.eg.authorization.service.dao.LogService;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class LogServiceImpl extends BaseFrameworkServiceImpl<LogEntity, Integer> implements LogService {

    @Resource
    private LogMapper mapper;
    @Resource
    private RestTemplate restTemplate;
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insertLog(String requestMethod, String requestURI, String body, String userID) {
        try {
        LinkedHashMap param = JSONObject.parseObject(body, LinkedHashMap.class, Feature.OrderedField);

        String[] list = requestURI.split("/");
        LogEntity logEntity = new LogEntity();
        logEntity.setMethod(requestMethod);
        logEntity.setParam(body);
        logEntity.setRequestURI(requestURI);
        logEntity.setUser_id(Long.valueOf(userID));
        if (requestURI.contains("update")||requestURI.contains("Audit")){
            //发送Post数据并返回数据
            Object object = this.restTemplate.getForObject("http://EG-"+list[list.length-3]+"/"+list[list.length-2]+"/queryListPage?pageSize=1&id="+param.get("id")
                    , Object.class);
            Map<String,Object> paramMap = JSONObject.parseObject(JSONObject.toJSONString(object), Map.class);
            String str = JSON.toJSONString(paramMap.get("data"));
            logEntity.setOld_param(str);
        }
        mapper.insertSelective(logEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.success();

    }
}