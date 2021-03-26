package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.aop.WebLog;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.user.model.OverseasIdentityEntity;
import com.huihe.eg.user.model.UserInfoEntity;
import com.huihe.eg.user.mybatis.dao.OverseasIdentityMapper;
import com.huihe.eg.user.service.dao.OverseasIdentityService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.huihe.eg.user.service.dao.UserInfoService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OverseasIdentityServiceImpl extends BaseFrameworkServiceImpl<OverseasIdentityEntity, Long> implements OverseasIdentityService {

    @Resource
    private OverseasIdentityMapper mapper;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private AmqpTemplate rabbitTemplate;
    @Resource
    private RedisService redisService;
    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(OverseasIdentityEntity overseasIdentityEntity, HttpServletRequest request, HttpServletResponse response) {
        OverseasIdentityEntity entity = new OverseasIdentityEntity();
        entity.setUser_id(overseasIdentityEntity.getUser_id());
        entity.setIdentity_type(overseasIdentityEntity.getIdentity_type());
        List<OverseasIdentityEntity> list = super.query(entity, request, response);
        if (list != null && list.size() > 0){
            overseasIdentityEntity.setId(list.get(0).getId());
            overseasIdentityEntity.setStatus(2);
            overseasIdentityEntity.setRefuse_contect("");
            return super.update(overseasIdentityEntity,request,response);
        }
        return  super.insert(overseasIdentityEntity, request, response);
    }

    @Override
    public Map<String, Object> queryOverseasAuthListPage(OverseasIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> objectMap = new HashMap<>();
        List<OverseasIdentityEntity> list = mapper.queryOverseasAuthListPage(identityEntity);
        Integer integer=mapper.queryOverseasAuthListPageCount(identityEntity);
        for (OverseasIdentityEntity entity : list){
            Map<String,Object> map = new HashMap<>();
            map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(identityEntity.getUser_id() + "userinfo")));
            entity.setMap(map);
        }
        objectMap.put("OverseasAuthListPageInfo",list);
        objectMap.put("OverseasAuthListPageCount",integer);
        return objectMap;
    }
    @Override
    public List<OverseasIdentityEntity> queryListPage(OverseasIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response) {
        List<OverseasIdentityEntity> list = super.queryListPage(identityEntity, request, response);
        for (OverseasIdentityEntity entity : list){
            Map<String,Object> map = new HashMap<>();
            map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(identityEntity.getUser_id() + "userinfo")));
            entity.setMap(map);
        }
        return list;
    }

    @Override
    public ResultParam overseasAudit(OverseasIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<>();
        try {
            int ret = mapper.updateByPrimaryKeySelective(identityEntity);
            if(ret > 0){
                identityEntity = mapper.selectByPrimaryKey(identityEntity.getId());
                UserInfoEntity infoEntity=new UserInfoEntity();
                infoEntity.setUser_id(identityEntity.getUser_id());
                List<OverseasIdentityEntity> overseasIdentityEntities=mapper.query(identityEntity);
                map = JSONUtils.generatorMap("修改成功",true);
                PushMessageParam pushMessageParam = new PushMessageParam();
                pushMessageParam.setTarget_id(identityEntity.getUser_id());
                pushMessageParam.setType("authentication");
                pushMessageParam.setOpera_type("abroadAuthentication");
                pushMessageParam.setTitle("境外认证通知");
                pushMessageParam.setType_id(identityEntity.getId());
                if (identityEntity.getStatus() == 1){
                    if(overseasIdentityEntities!=null&&overseasIdentityEntities.size()>0){
                        if("overseas_student".equalsIgnoreCase(overseasIdentityEntities.get(0).getIdentity_type())){
                            infoEntity.setOverseas_identity_name("留学生");
                        }else if("overseas_chinese_learners".equalsIgnoreCase(overseasIdentityEntities.get(0).getIdentity_type())){
                            infoEntity.setOverseas_identity_name("中文爱好者");
                        }else if("student".equalsIgnoreCase(overseasIdentityEntities.get(0).getIdentity_type())){
                            infoEntity.setOverseas_identity_name("学生");
                        }else if("overseas_government_personnel".equalsIgnoreCase(overseasIdentityEntities.get(0).getIdentity_type())){
                            infoEntity.setOverseas_identity_name("政府境外工作者");
                        }else if("overseas_staff".equalsIgnoreCase(overseasIdentityEntities.get(0).getIdentity_type())){
                            infoEntity.setOverseas_identity_name("境外工作者");
                        }else if("ethnic_chinese".equalsIgnoreCase(overseasIdentityEntities.get(0).getIdentity_type())){
                            infoEntity.setOverseas_identity_name("华裔");
                        }else if("overseas_chinese".equalsIgnoreCase(overseasIdentityEntities.get(0).getIdentity_type())){
                            infoEntity.setOverseas_identity_name("华侨");
                        }else if("overseas_merchant".equalsIgnoreCase(overseasIdentityEntities.get(0).getIdentity_type())){
                            infoEntity.setOverseas_identity_name("境外商人");
                        }else if("overseas_nationality".equalsIgnoreCase(overseasIdentityEntities.get(0).getIdentity_type())){
                            infoEntity.setHometown(overseasIdentityEntities.get(0).getIn_country());
                        } else if("language_teacher_concurrent".equalsIgnoreCase(overseasIdentityEntities.get(0).getIdentity_type())){
                            infoEntity.setLanguages(overseasIdentityEntities.get(0).getLanguage());
                            infoEntity.setOverseas_identity_name("兼职语言老师");
//                            infoEntity.setCountry("专职助学师");
                        }else if("language_teacher".equalsIgnoreCase(overseasIdentityEntities.get(0).getIdentity_type())){
                            infoEntity.setLanguages(overseasIdentityEntities.get(0).getLanguage());
                            infoEntity.setOverseas_identity_name("全职语言老师");
//                            infoEntity.setCountry("助学师");
                        }
                        userInfoService.update(infoEntity,request,response);
                    }
                    pushMessageParam.setContent("境外认证审核已通过");

                }else {
                    pushMessageParam.setContent("境外认证审核被拒绝");
                }
                /**
                 * 境外认证审核推送
                 */
                pushMessageParam.setIs_teach_paypal(identityEntity.getIs_teach_paypal());
                rabbitTemplate.convertAndSend("updateAuthMessage",pushMessageParam);
            }else {
                map = JSONUtils.generatorMap("修改失败",false);
            }
        } catch (AmqpException e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    @Override
    public Map<String, Object> queryOverseasAuditCount(OverseasIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;
        try {
            map = new HashMap<>();
            Integer total = mapper.queryListPageCount(identityEntity);
            map.put("total", total);

            identityEntity.setStatus(1);
            Integer agreeCount = mapper.queryListPageCount(identityEntity);
            map.put("agreeCount", agreeCount);

            identityEntity.setStatus(2);
            Integer waitCount = mapper.queryListPageCount(identityEntity);
            map.put("waitCount", waitCount);

            identityEntity.setStatus(3);
            Integer refuseCount = mapper.queryListPageCount(identityEntity);
            map.put("refuseCount", refuseCount);

        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryByMessage(OverseasIdentityEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<OverseasIdentityEntity> list = mapper.queryByMessage(param);
            Integer total = mapper.queryByMessageCount(param);
            for (OverseasIdentityEntity identityEntity : list) {
                Map<String, Object> map1 = Maps.newHashMap();
                map1.put("userinfo", JSONUtils.obj2Json(redisService.getStr(identityEntity.getUser_id() + "userinfo")));
                identityEntity.setMap(map1);
            }
            map.put("rows", list);
            map.put("total", total);

        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

}