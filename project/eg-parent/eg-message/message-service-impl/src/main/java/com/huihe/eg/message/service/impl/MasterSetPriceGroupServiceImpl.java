package com.huihe.eg.message.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.http.HttpsClientRequest;
import com.cy.framework.util.json.JSONUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.message.model.MasterSetPriceGroupEntity;
import com.huihe.eg.message.mybatis.dao.MasterSetPriceGroupMapper;
import com.huihe.eg.message.service.dao.MasterSetPriceGroupService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.message.service.impl.tim.TimConfig;
import com.tencentyun.TLSSigAPIv2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class MasterSetPriceGroupServiceImpl extends BaseFrameworkServiceImpl<MasterSetPriceGroupEntity, Long> implements MasterSetPriceGroupService {

    @Resource
    private MasterSetPriceGroupMapper mapper;
    @Resource
    private TimConfig timConfig;
    public void init() {
        setMapper(mapper);
    }

    @Transactional
    @Override
    public Long insertMasterGroup(Long id, String title, Long user_id, String face_url, HttpServletRequest request, HttpServletResponse response) {
        try {

            MasterSetPriceGroupEntity masterSetPriceGroupEntity = new MasterSetPriceGroupEntity();

            Random random = new Random();
            int group_id = 10000000 + random.nextInt(89999999);

            Map<String, Object> map = new HashMap<>();
                map.put("Owner_Account", user_id.toString());
                map.put("Type", "Public");
                map.put("FaceUrl", face_url);
                map.put("Name", title);
                map.put("MaxMemberCount", 500);
                map.put("ApplyJoinOption", "FreeAccess");
                map.put("GroupId", group_id+"");

                net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map);//map转为json

                UUID uuid = UUID.randomUUID();
                TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());//生成密钥api
                String adminSign = tlsSigAPIv2.genSig(timConfig.getAdminName(), timConfig.getExpire());//TIM签名
                String url = timConfig.getUrl() + "/group_open_http_svc/create_group?sdkappid=" + timConfig.getSdkAppId() + "&identifier=" +
                        timConfig.getAdminName() + "&usersig=" + adminSign + "&random=" + uuid + "&contenttype=json";
                url = url.replaceAll("\r|\n", "");//替换url中\r\n
                String str = HttpsClientRequest.post(url, json.toString(), null, null);//客户端请求对象 api
                Map<String, Object> resultMap = JSONUtils.obj2Map(str, null);
                logger.info(JSONUtils.obj2Json(resultMap).toString());
                if (resultMap != null && resultMap.containsKey("ActionStatus") && "OK".equals(resultMap.get("ActionStatus"))) {//结果
                    masterSetPriceGroupEntity.setGroup_id((long)group_id);
                    masterSetPriceGroupEntity.setMaster_set_price_id(id);
                    int i = mapper.insertSelective(masterSetPriceGroupEntity);

                    return (long)group_id;
                }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return null;
    }

    @Override
    public String insertEnjoyGroup(Long id, Long group_id, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<>();
        map.put("GroupId", group_id);
        HashMap<String, String> map1 = Maps.newHashMap();
        map1.put("Member_Account",id.toString());
        map.put("MemberList", Lists.newArrayList(map1));
        map.put("Silence", 1);

        net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map);//map转为json

        UUID uuid = UUID.randomUUID();
        TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());//生成密钥api
        String adminSign = tlsSigAPIv2.genSig(timConfig.getAdminName(), timConfig.getExpire());//TIM签名
        String url = timConfig.getUrl() + "/group_open_http_svc/add_group_member?sdkappid=" + timConfig.getSdkAppId() + "&identifier=" +
                timConfig.getAdminName() + "&usersig=" + adminSign + "&random=" + uuid + "&contenttype=json";
        url = url.replaceAll("\r|\n", "");//替换url中\r\n
        String str = HttpsClientRequest.post(url, json.toString(), null, null);//客户端请求对象 api
        Map<String, Object> resultMap = JSONUtils.obj2Map(str, null);
//        logger.info(JSONUtils.obj2Json(resultMap).toString());
        if (resultMap != null && resultMap.containsKey("ActionStatus") && "OK".equals(resultMap.get("ActionStatus"))) {//结果
            return "success";
        }

        return "Failure";
    }


}