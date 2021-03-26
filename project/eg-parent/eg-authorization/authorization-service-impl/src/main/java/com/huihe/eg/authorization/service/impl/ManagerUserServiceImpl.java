package com.huihe.eg.authorization.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.cy.framework.util.safe.MD5Util;
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
import com.huihe.eg.comm.TokenUtils;
import com.huihe.eg.comm.UserEum;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ManagerUserServiceImpl extends BaseFrameworkServiceImpl<ManagerUserEntity, Long> implements ManagerUserService {

    @Resource
    private RedisService redisService;
    @Resource
    private MenuService menuService;
    @Resource
    private ManagerUserMapper mapper;
    @Resource
    private ApiUrlService apiUrlService;
    @Resource
    private ManagerIdentityMapper managerIdentityMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    //查询管理员信息
    @Override
    public String queryManager(Long id) {
        ManagerUserEntity managerUserEntity = mapper.selectByPrimaryKey(id);
        return JSONUtils.obj2Json(managerUserEntity).toString();
    }

    @Override
    public ResultParam login(ManagerUserEntity entity, HttpServletRequest request, HttpServletResponse response) {
        entity.setPageSize(1);
        List<ManagerUserEntity> query = mapper.queryListPage(entity);
        try {

            if (query != null && query.size() > 0) {
                //登录刷新token
                HashMap<String, Object> map = new HashMap<>();
                String managerToken = "managerToken" + TokenUtils.createJWT(query.get(0).getId().toString(), "manager", "qmore", -1);
                redisService.set(managerToken, query.get(0).getId().toString(), 86400);
                map.put("managerToken", managerToken);
                map.put("managerInfo", JSON.parse(redisService.getStr(query.get(0).getId().toString() + "managerInfo")));

                return ResultUtil.success(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

                    //查询身份信息
                    ManagerIdentityEntity managerIdentityEntity = new ManagerIdentityEntity();
                    managerIdentityEntity.setType(managerUserEntity.getType());
                    managerIdentityEntity = managerIdentityMapper.query(managerIdentityEntity).get(0);

                    //查询菜单信息
//                    List<MenuEntity> menuEntities = menuServ
//                    ice.queryMenuChildList(managerUserEntity.getId(), request, response);
                    List<MenuEntity> menuEntities = menuService.queryRoleMenu(managerIdentityEntity.getId(), request, response);
                    managerUserEntity.setMenus(menuEntities);

                    //查询菜单拥有权限信息
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
        redisService.remove(param.getId() + "managerInfo");
        return "";
    }

    @Override
    public Long queryManagerId(String type, HttpServletRequest request, HttpServletResponse response) {
        try {
            Long id = mapper.queryManagerId(type);
            int i = mapper.updateStudentCount(id);
            return id;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0L;
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
        managerUserEntity.setUpdate_user(managerUserEntity.getAdd_user());
        return super.insert(managerUserEntity, request, response);
    }
}