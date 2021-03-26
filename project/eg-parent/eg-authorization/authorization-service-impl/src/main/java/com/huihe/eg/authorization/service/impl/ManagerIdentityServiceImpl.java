package com.huihe.eg.authorization.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Maps;
import com.huihe.eg.authorization.model.ManagerIdentityEntity;
import com.huihe.eg.authorization.model.ManagerIdentityUrlEntity;
import com.huihe.eg.authorization.model.ManagerUserEntity;
import com.huihe.eg.authorization.mybatis.dao.ManagerIdentityMapper;
import com.huihe.eg.authorization.mybatis.dao.ManagerIdentityUrlMapper;
import com.huihe.eg.authorization.service.dao.ManagerIdentityService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.authorization.service.dao.ManagerIdentityUrlService;
import com.huihe.eg.authorization.service.dao.ManagerUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ManagerIdentityServiceImpl extends BaseFrameworkServiceImpl<ManagerIdentityEntity, Long> implements ManagerIdentityService {
    @Resource
    private ManagerIdentityUrlService managerIdentityUrlService;
    @Resource
    private ManagerIdentityMapper mapper;
    @Resource
    private ManagerUserService managerUserService;
    @Resource
    private ManagerIdentityUrlMapper managerIdentityUrlMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public Map<String, Object> queryRoleList(ManagerIdentityEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<ManagerIdentityEntity> managerUserEntities = mapper.queryListPage(param);
            Integer total = mapper.queryListPageCount(param);
            if (total>0) {
                for (ManagerIdentityEntity managerIdentityEntity : managerUserEntities) {
                    ManagerUserEntity managerUserEntity = new ManagerUserEntity();
                    managerUserEntity.setType(managerIdentityEntity.getType());
                    Integer integer = managerUserService.queryListPageCount(managerUserEntity, request, response);
                    managerIdentityEntity.setNumber_people(integer);
                }
            }
            map.put("rows", managerUserEntities);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("ManagerUserServiceImpl   queryRoleList");
        }
        return map;
    }


    @Override
    public ResultParam insert(ManagerIdentityEntity managerIdentityEntity, HttpServletRequest request, HttpServletResponse response) {
        try {
            Integer[] permIds = {};
            if (managerIdentityEntity.getPermIds()!=null) {
                permIds = managerIdentityEntity.getPermIds();
            }
            if (managerIdentityEntity.getId() != null && managerIdentityEntity.getId() != 0) {
                managerIdentityUrlMapper.deleteByIdentityId(managerIdentityEntity.getId());
                super.update(managerIdentityEntity, request, response);
            }else {
                super.insert(managerIdentityEntity, request, response);
            }
            for (Integer deptId : permIds) {
                ManagerIdentityUrlEntity managerIdentityUrlEntity = new ManagerIdentityUrlEntity();
                managerIdentityUrlEntity.setIdentity_id(mapper.query(managerIdentityEntity).get(0).getId());
                managerIdentityUrlEntity.setUrl_id((long) deptId);
                managerIdentityUrlService.insert(managerIdentityUrlEntity, request, response);
            }
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
    }
}