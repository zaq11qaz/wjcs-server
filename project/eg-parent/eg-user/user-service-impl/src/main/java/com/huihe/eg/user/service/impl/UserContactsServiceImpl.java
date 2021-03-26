package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.UserContactsEntity;
import com.huihe.eg.user.model.UserInfoEntity;
import com.huihe.eg.user.mybatis.dao.UserContactsMapper;
import com.huihe.eg.user.mybatis.dao.UserInfoMapper;
import com.huihe.eg.user.service.dao.UserContactsService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.huihe.eg.user.service.impl.sms.SmsService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserContactsServiceImpl extends BaseFrameworkServiceImpl<UserContactsEntity, Long> implements UserContactsService {

    @Resource
    private UserContactsMapper mapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private SmsService smsService;
    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(UserContactsEntity userContactsEntity, HttpServletRequest request, HttpServletResponse response) {
        try {
            if(userContactsEntity.getContacts()!=null&& !userContactsEntity.getContacts().equals("")){//
                String[] arr = userContactsEntity.getContacts().split(","); // 用,分割
                List<String> list = Arrays.asList(arr);
                if(list.size() > 0){
                    for (String string:list){
                        String user_name="";
                        Map<String,Object> map = JSONUtils.obj2Map(string,null);
                        UserContactsEntity userContactsEntity1=new UserContactsEntity();
                        if(map != null){
                            for(String key : map.keySet()){
                                user_name = key;
                            }
                        }
                        if(!user_name.equals("") &&map.get(user_name).toString()!=null&& !map.get(user_name).toString().equals("")){
                            userContactsEntity1.setMobile(map.get(user_name).toString());
                            userContactsEntity1.setUser_id(userContactsEntity.getUser_id());
                            List<UserContactsEntity> list1 = super.query(userContactsEntity1, request, response);
                            if (list1 != null && list1.size() > 0){
                                userContactsEntity1.setUser_name(user_name);
                                userContactsEntity1.setId(list1.get(0).getId());
                                super.update(userContactsEntity1, request, response);
                            }else {
                                userContactsEntity1.setUser_name(user_name);
                                super.insert(userContactsEntity1,request,response);
                            }
                        }
                    }
                }
            }else{
                UserContactsEntity contactsEntity = new UserContactsEntity();
                contactsEntity.setUser_id(userContactsEntity.getUser_id());
                contactsEntity.setMobile(userContactsEntity.getMobile());
                List<UserContactsEntity> list = super.query(contactsEntity, request, response);
                if (list != null && list.size() > 0){
                    return super.update(userContactsEntity, request, response);
                }else {
                    return  super.insert(userContactsEntity, request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserContactsServiceImpl     insert ");
        }
        return ResultUtil.success();
    }
    @Override
    public List<UserContactsEntity> query(UserContactsEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserContactsEntity> userContactsEntities=null;
        try {
            userContactsEntities=mapper.query(param);
            if(userContactsEntities!=null&&userContactsEntities.size()>0){
                for (UserContactsEntity entity:userContactsEntities){
                    if(entity.getMobile()!=null&& !entity.getMobile().equals("")){
                        UserInfoEntity infoEntity=new UserInfoEntity();
                        infoEntity.setMobile(entity.getMobile());
                        List<UserInfoEntity> list=userInfoMapper.queryListPage(infoEntity);
                        entity.setIs_resgiter(list != null && list.size() > 0);
                    }else{
                        entity.setIs_resgiter(false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserContactsServiceImpl          query");
        }
        return  userContactsEntities;
    }

    /**
     * 发送邀请
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam sendInvitation(UserContactsEntity param, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam = new ResultParam();
        Map<String,Object> mapResult=new HashMap<>();
        try {
            List<Long> longs=new ArrayList<>();
            if(param.getContacts()!=null&& !param.getContacts().equals("")){
                String[] arr = param.getContacts().split(","); // 用,分割
                List<String> list = Arrays.asList(arr);
                if(list.size() > 0){
                    for (String string:list){
                        String user_name="";
                        Map<String,Object> map = JSONUtils.obj2Map(string,null);
                        for(String key : map.keySet()){
                            user_name = key;
                        }
                        UserInfoEntity infoEntity=new UserInfoEntity();
                        infoEntity.setMobile(map.get(user_name).toString());
                        infoEntity.setPageSize(1);
                        List<UserInfoEntity> userInfoEntities=userInfoMapper.queryListPage(infoEntity);//查询是否注册
                        if(userInfoEntities!=null&&userInfoEntities.size()>0){
                            UserContactsEntity userContactsEntity=new UserContactsEntity();
                            userContactsEntity.setUser_id(param.getUser_id());
                            userContactsEntity.setMobile(map.get(user_name).toString());
                            userContactsEntity.setPageSize(1);
                            List<UserContactsEntity> userContactsEntities=mapper.queryListPage(userContactsEntity);
                            if(userContactsEntities!=null&&userContactsEntities.size()>0){
                                userContactsEntity.setId(userContactsEntities.get(0).getId());
                                userContactsEntity.setIs_send(true);
                                int res=mapper.updateByPrimaryKeySelective(userContactsEntity);
                                longs.add(userInfoEntities.get(0).getUser_id());
                                if(res<=0){
                                    return ResultUtil.error(UserEum.user_10024.getCode(), UserEum.user_10024.getDesc());
                                }
                            }else{
                                return ResultUtil.error(UserEum.user_10025.getCode(), UserEum.user_10025.getDesc());
                            }
                        }else{
                            resultParam=smsService.sendInvitation(map.get(user_name).toString(),user_name);
                            if(resultParam.getCode()==0){
                                UserContactsEntity userContactsEntity=new UserContactsEntity();
                                userContactsEntity.setUser_id(param.getUser_id());
                                userContactsEntity.setMobile(map.get(user_name).toString());
                                userContactsEntity.setPageSize(1);
                                List<UserContactsEntity> userContactsEntities=mapper.queryListPage(userContactsEntity);
                                if(userContactsEntities!=null&&userContactsEntities.size()>0){
                                    userContactsEntity.setId(userContactsEntities.get(0).getId());
                                    userContactsEntity.setIs_send(true);
                                    int res=mapper.updateByPrimaryKeySelective(userContactsEntity);
                                    if(res<=0){
                                        return ResultUtil.error(UserEum.user_10024.getCode(), UserEum.user_10024.getDesc());
                                    }
                                }else {
                                    return ResultUtil.error(UserEum.user_10025.getCode(), UserEum.user_10025.getDesc());
                                }
                            }else{
                                return ResultUtil.error(UserEum.user_10026.getCode(), UserEum.user_10026.getDesc());
                            }
                        }

                    }
                }
            }else{
                if (StringUtil.isEmpty(param.getMobile())){
                    return ResultUtil.error(UserEum.user_10006.getCode(), UserEum.user_10006.getDesc());
                }
                if (StringUtil.isEmpty(param.getUser_name())){
                    return ResultUtil.error(UserEum.user_100001.getCode(), UserEum.user_100001.getDesc());
                }
                UserInfoEntity infoEntity=new UserInfoEntity();
                infoEntity.setMobile(param.getMobile());
                infoEntity.setPageSize(1);
                List<UserInfoEntity> userInfoEntities=userInfoMapper.queryListPage(infoEntity);
                if(userInfoEntities!=null&&userInfoEntities.size()>0){
                    UserContactsEntity userContactsEntity=new UserContactsEntity();
                    userContactsEntity.setUser_id(param.getUser_id());
                    userContactsEntity.setMobile(param.getMobile());
                    userContactsEntity.setPageSize(1);
                    List<UserContactsEntity> userContactsEntities=mapper.queryListPage(userContactsEntity);
                    if(userContactsEntities!=null&&userContactsEntities.size()>0){
                        userContactsEntity.setId(userContactsEntities.get(0).getId());
                        userContactsEntity.setIs_send(true);
                        int res=mapper.updateByPrimaryKeySelective(userContactsEntity);
                        longs.add(userInfoEntities.get(0).getUser_id());
                        if(res<=0){
                            return ResultUtil.error(UserEum.user_10024.getCode(), UserEum.user_10024.getDesc());
                        }
                    }else{
                        return ResultUtil.error(UserEum.user_10025.getCode(), UserEum.user_10025.getDesc());
                    }
                }else{
                    resultParam=smsService.sendInvitation(param.getMobile(),param.getUser_name());
                    if(resultParam.getCode()==0){
                        UserContactsEntity userContactsEntity=new UserContactsEntity();
                        userContactsEntity.setUser_id(param.getUser_id());
                        userContactsEntity.setMobile(param.getMobile());
                        userContactsEntity.setPageSize(1);
                        List<UserContactsEntity> userContactsEntities=mapper.queryListPage(userContactsEntity);
                        if(userContactsEntities!=null&&userContactsEntities.size()>0){
                            userContactsEntity.setId(userContactsEntities.get(0).getId());
                            userContactsEntity.setIs_send(true);
                            int ret=mapper.updateByPrimaryKeySelective(userContactsEntity);
                            if(ret<=0){
                                return ResultUtil.error(UserEum.user_10024.getCode(), UserEum.user_10024.getDesc());
                            }
                        }else {
                            return ResultUtil.error(UserEum.user_10025.getCode(), UserEum.user_10025.getDesc());
                        }
                    }else{
                        return ResultUtil.error(UserEum.user_10026.getCode(), UserEum.user_10026.getDesc());
                    }
                }
            }
            mapResult.put("registerId",longs);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserContactsServiceImpl          sendInvitation");
        }
        return ResultUtil.success(mapResult);
    }
}