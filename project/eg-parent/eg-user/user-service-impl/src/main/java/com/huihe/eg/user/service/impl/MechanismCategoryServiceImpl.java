package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.user.model.MechanismCategoryEntity;
import com.huihe.eg.user.mybatis.dao.MechanismCategoryMapper;
import com.huihe.eg.user.service.dao.MechanismCategoryService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MechanismCategoryServiceImpl extends BaseFrameworkServiceImpl<MechanismCategoryEntity, Long> implements MechanismCategoryService {

    @Resource
    private MechanismCategoryMapper mapper;

    public void init() {
        setMapper(mapper);
    }


    @Override
    public synchronized ResultParam insert(MechanismCategoryEntity param, HttpServletRequest request, HttpServletResponse response) {
        MechanismCategoryEntity mechanismCategoryEntity = new MechanismCategoryEntity();
        mechanismCategoryEntity.setName(param.getName());
        mechanismCategoryEntity.setType(param.getType());
        mechanismCategoryEntity.setPageSize(1);
        List<MechanismCategoryEntity> mechanismCategoryEntities = mapper.queryListPage(mechanismCategoryEntity);
        if (mechanismCategoryEntities != null && mechanismCategoryEntities.size() > 0) {
            param.setId(mechanismCategoryEntities.get(0).getId());
            return super.update(param, request, response);
        }
        return super.insert(param, request, response);
    }

    @Override
    public List<MechanismCategoryEntity> queryListPageChild(MechanismCategoryEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MechanismCategoryEntity> list = Lists.newArrayList();
        try {
            if (param.getStatus() == null) {
                param.setStatus(2);
            }
//            MechanismCategoryEntity mechanismCategoryEntity = new MechanismCategoryEntity();
//            mechanismCategoryEntity.setType(1);
//            mechanismCategoryEntity.setStatus(2);
            list = mapper.query(param);
            if (list != null && list.size() > 0) {
                for (MechanismCategoryEntity categoryEntity : list) {
                    MechanismCategoryEntity mechanismCategoryEntity1 = new MechanismCategoryEntity();
                    mechanismCategoryEntity1.setParent_id(categoryEntity.getId());
                    mechanismCategoryEntity1.setStatus(2);
                    HashMap<String, Object> map = Maps.newHashMap();
                    map.put("childList", mapper.query(mechanismCategoryEntity1));
                    categoryEntity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<MechanismCategoryEntity> querySubjects(MechanismCategoryEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MechanismCategoryEntity> list = Lists.newArrayList();
        try {
            MechanismCategoryEntity mechanismCategoryEntity = new MechanismCategoryEntity();
            mechanismCategoryEntity.setType(1);
            mechanismCategoryEntity.setStatus(2);
            mechanismCategoryEntity.setName("????????????");
            mechanismCategoryEntity.setPageSize(1);
            list = mapper.queryListPage(mechanismCategoryEntity);
            if (list != null && list.size() > 0) {
                for (MechanismCategoryEntity categoryEntity : list) {
                    MechanismCategoryEntity mechanismCategoryEntity1 = new MechanismCategoryEntity();
                    mechanismCategoryEntity1.setParent_id(categoryEntity.getId());
                    mechanismCategoryEntity1.setStatus(2);
                    HashMap<String, Object> map = Maps.newHashMap();
                    map.put("childList", mapper.query(mechanismCategoryEntity1));
                    categoryEntity.setMap(map);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}