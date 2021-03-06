package com.huihe.eg.authorization.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.authorization.model.ApiUrlEntity;
import com.huihe.eg.authorization.model.ManagerIdentityEntity;
import com.huihe.eg.authorization.model.ManagerUserEntity;
import com.huihe.eg.authorization.model.MenuEntity;
import com.huihe.eg.authorization.mybatis.dao.ManagerIdentityMapper;
import com.huihe.eg.authorization.mybatis.dao.ManagerUserMapper;
import com.huihe.eg.authorization.service.dao.ApiUrlService;
import com.huihe.eg.authorization.service.dao.ManagerUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.authorization.service.dao.MenuService;
import com.huihe.eg.cloud.feign.UserApiService;
import com.huihe.eg.comm.AuthorizationEum;
import com.huihe.eg.comm.TokenUtils;
import com.huihe.eg.comm.UserEum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.DateTimeException;
import java.util.HashMap;
import java.util.List;

@Service
public class ManagerUserServiceImpl extends BaseFrameworkServiceImpl<ManagerUserEntity, Long> implements ManagerUserService {

    @Resource
    private ManagerUserMapper mapper;
    @Resource
    private RedisService redisService;
    @Resource
    private MenuService menuService;
    @Resource
    private ApiUrlService apiUrlService;
    @Resource
    private ManagerIdentityMapper managerIdentityMapper;
    @Resource
    private UserApiService userApiService;

    @Override
    public void init() {
        setMapper(mapper);
    }

    //?????????????????????
    @Override
    public String queryManager(Long id) {
        ManagerUserEntity managerUserEntity = mapper.selectByPrimaryKey(id);
        return JSONUtils.obj2Json(managerUserEntity).toString();
    }

    @Override
    public ResultParam login(ManagerUserEntity entity, HttpServletRequest request, HttpServletResponse response) {
        entity.setPageSize(1);
        if (StringUtil.isNotEmpty(entity.getLogin_name()) && StringUtil.isNotEmpty(entity.getPwd())) {
            List<ManagerUserEntity> query = mapper.queryListPage(entity);
            try {
                if (query != null && query.size() > 0) {

                    switch (query.get(0).getStatus()) {
                        case 1:
                            return ResultUtil.error(AuthorizationEum.authorization_300019.getCode(), AuthorizationEum.authorization_300019.getDesc());
                        case 3:
                            return ResultUtil.error(AuthorizationEum.authorization_300020.getCode(), AuthorizationEum.authorization_300020.getDesc());
                        case 4:
                            return ResultUtil.error(AuthorizationEum.authorization_300021.getCode(), AuthorizationEum.authorization_300021.getDesc());
                        default:
                            break;
                    }

                    //????????????token
                    HashMap<String, Object> map = new HashMap<>();
                    String managerToken = "managerToken" + TokenUtils.createJWT(query.get(0).getId().toString(), "manager", "qmore", -1);
                    redisService.set(managerToken, query.get(0).getId() + "managerToken", 86400);
                    redisService.set(query.get(0).getId() + "managerToken", managerToken, 86400);
                    map.put("managerToken", managerToken);
                    map.put("managerInfo", JSON.parse(redisService.getStr(query.get(0).getId().toString() + "managerInfo")));

                    return ResultUtil.success(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResultUtil.error(ResultEnum.SYSTEM_ERROR.getCode(), ResultEnum.SYSTEM_ERROR.getDesc());
    }

    @Override
    public void queryManagerInfoInsertRedis(ManagerUserEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<ManagerUserEntity> managerUserEntities = mapper.query(param);
        try {
            for (ManagerUserEntity managerUserEntity : managerUserEntities) {
                String ManagerInfo = redisService.getStr(managerUserEntity.getId() + "managerInfo");
                if (null == ManagerInfo || ManagerInfo.equals("")) {

                    //??????????????????
                    ManagerIdentityEntity managerIdentityEntity = new ManagerIdentityEntity();
                    managerIdentityEntity.setType(managerUserEntity.getType());
                    managerIdentityEntity = managerIdentityMapper.query(managerIdentityEntity).get(0);

                    //??????????????????
//                    List<MenuEntity> menuEntities = menuServ
//                    ice.queryMenuChildList(managerUserEntity.getId(), request, response);
                    List<MenuEntity> menuEntities = menuService.queryRoleMenu(managerIdentityEntity.getId(), request, response);
                    managerUserEntity.setMenus(menuEntities);

                    //??????????????????????????????
                    List<ApiUrlEntity> list = apiUrlService.queryRoleIdentity(managerIdentityEntity.getId(), request, response);
                    managerUserEntity.setApiUrlEntities(list);
                    redisService.set(managerUserEntity.getId() + "managerInfo", JSONObject.toJSONString(managerUserEntity));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserInfoServiceImpl   queryUserinfoInsertRedis");
        }
    }

    @Override
    public Object logOut(ManagerUserEntity param, HttpServletRequest request, HttpServletResponse response) {
        String token = request.getParameter("token");
        redisService.remove(token);
        return "";
    }

    @Override
    public Long queryManagerId(String type, HttpServletRequest request, HttpServletResponse response) {
        try {
            Long id = mapper.queryManagerId(type);
            int i = mapper.updateStudentCount(id);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Override
    public ResultParam updateLevel(ManagerUserEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            ManagerUserEntity managerUserEntity = mapper.selectByPrimaryKey(param.getId());
            ManagerIdentityEntity managerIdentityEntity = new ManagerIdentityEntity();
            managerIdentityEntity.setType(managerUserEntity.getType());
            //todo
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public synchronized ResultParam insertActivityManager(ManagerUserEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            param.setName(param.getMobile());
            param.setUpdate_user("admin");
            param.setAdd_user("admin");
            param.setShort_name("manager");
            param.setType("manager");
            param.setLogin_name(param.getMobile());
            ManagerUserEntity managerUserEntity = new ManagerUserEntity();
            managerUserEntity.setLogin_name(param.getMobile());
            managerUserEntity.setPageSize(1);
            List<ManagerUserEntity> managerUserEntities = mapper.queryListPage(managerUserEntity);
            if (managerUserEntities != null && managerUserEntities.size() > 0) {
                managerUserEntity = managerUserEntities.get(0);
                param.setId(managerUserEntity.getId());
                managerUserEntity.setPwd(param.getPwd());
                int i = mapper.updateByPrimaryKeySelective(managerUserEntity);
            } else {
                int i = mapper.insertSelective(param);
            }
            int i = userApiService.updateUserManagerId(param.getId(), param.getUser_id());
            if (i == 0) {
                throw new DateTimeException("??????????????????");
            }
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public ResultParam insert(ManagerUserEntity managerUserEntity, HttpServletRequest request, HttpServletResponse response) {
        ManagerUserEntity managerUserEntity1 = new ManagerUserEntity();
        managerUserEntity1.setLogin_name(managerUserEntity.getLogin_name());
        managerUserEntity1.setPageSize(1);
        /*
        StringBuilder builder = new StringBuilder(managerUserEntity.getLogin_name());
        builder.append(managerUserEntity.getPwd());
        String str = MD5Util.GetMD5Code(builder.toString());
        managerUserEntity.setPwd(str);
         */

        List<ManagerUserEntity> query = mapper.queryListPage(managerUserEntity1);
        if (query != null && query.size() > 0) {
            if (managerUserEntity.getId() != null && managerUserEntity.getId() != 0) {
                managerUserEntity.setId(query.get(0).getId());
                return super.update(managerUserEntity, request, response);
            }
            return ResultUtil.error(UserEum.user_10000.getCode(), UserEum.user_10000.getDesc());
        }

        String access_token = request.getHeader("token");
        if (StringUtil.isNotEmpty(access_token)) {
            access_token = access_token.replaceAll("userTocken", "");
            String str = redisService.getStr(access_token);
            ManagerUserEntity managerUserEntity2 = mapper.selectByPrimaryKey(Long.valueOf(str));
            ManagerIdentityEntity managerIdentityEntity = new ManagerIdentityEntity();
            managerIdentityEntity.setType(managerUserEntity2.getType());
            managerIdentityEntity.setPageSize(1);
            managerIdentityEntity = managerIdentityMapper.queryListPage(managerIdentityEntity).get(0);
            ManagerIdentityEntity managerIdentityEntity1 = new ManagerIdentityEntity();
            managerIdentityEntity1.setType(managerUserEntity.getType());
            managerIdentityEntity1.setPageSize(1);
            managerIdentityEntity1 = managerIdentityMapper.queryListPage(managerIdentityEntity1).get(0);
            if (managerIdentityEntity.getLevels() > managerIdentityEntity1.getLevels()) {
                return ResultUtil.error(AuthorizationEum.authorization_30004.getCode(), AuthorizationEum.authorization_30004.getDesc());
            }
        }

        managerUserEntity.setUpdate_user(managerUserEntity.getAdd_user());
        return super.insert(managerUserEntity, request, response);
    }
}