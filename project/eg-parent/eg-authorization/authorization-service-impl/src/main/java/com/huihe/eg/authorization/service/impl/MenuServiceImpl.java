package com.huihe.eg.authorization.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.huihe.eg.authorization.model.*;
import com.huihe.eg.authorization.mybatis.dao.ApiUrlMapper;
import com.huihe.eg.authorization.mybatis.dao.ButtonMapper;
import com.huihe.eg.authorization.mybatis.dao.MenuMapper;
import com.huihe.eg.authorization.service.dao.ApiUrlService;
import com.huihe.eg.authorization.service.dao.MenuService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.authorization.service.impl.algorithm.MenuEntityAlgorithm;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.huihe.eg.authorization.service.impl.algorithm.MenuEntityAlgorithm.findSelfAndAllChild;
import static com.huihe.eg.authorization.service.impl.algorithm.MenuEntityAlgorithm.tree;

@Service
public class MenuServiceImpl extends BaseFrameworkServiceImpl<MenuEntity, Long> implements MenuService {

    @Resource
    private MenuMapper mapper;
    @Resource
    private MenuEntityAlgorithm menuEntityAlgorithm;
    @Resource
    private ApiUrlService apiUrlService;
    @Resource
    private ApiUrlMapper apiUrlMapper;
    @Resource
    private ButtonMapper buttonMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(MenuEntity menuEntity, HttpServletRequest request, HttpServletResponse response) {
        try {
            //更新或插入
            ApiUrlEntity apiUrlEntity = new ApiUrlEntity();
            //如果id非空
            if (menuEntity.getId()!=null&&menuEntity.getId()!=0) {
                apiUrlEntity.setUpdate_user(menuEntity.getUpdate_user());
                super.update(menuEntity, request, response);
            }else {
                menuEntity.setUpdate_user(menuEntity.getAdd_user());
                long id = mapper.insertSelective(menuEntity);
                menuEntity.setId(id);
                apiUrlEntity.setAdd_user(menuEntity.getAdd_user());
            }
            //插入api
            if (menuEntity.getParent_id()!=0){
                apiUrlEntity.setTypes(1);
                ApiUrlEntity apiUrlEntity1 = new ApiUrlEntity();
                apiUrlEntity1.setMenu_id(menuEntity.getParent_id());
                apiUrlEntity.setParent_id(apiUrlService.query(apiUrlEntity1,request,response).get(0).getId());
            }else{
                apiUrlEntity.setTypes(0);
                apiUrlEntity.setParent_id(0L);
            }
            //查询父urlId

            //插入api
            apiUrlEntity.setSeq(menuEntity.getSeq());
            apiUrlEntity.setMenu_id(menuEntity.getId());
            apiUrlEntity.setUrl(menuEntity.getRouteUrl());
            apiUrlEntity.setDescr(menuEntity.getDescr());
            apiUrlEntity.setOpend(menuEntity.getOpend());
            apiUrlEntity.setAmount(menuEntity.getAmount());
            apiUrlService.insert(apiUrlEntity,request,response);
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
    }

    /**
    * @Description: 查询菜单树
    * @Param:
    * @return:
    * @Author: zwy
    * @Date: 2020/8/8
    */
    @Override
    public List<MenuEntity> queryMenuList(MenuEntity entity, HttpServletRequest request, HttpServletResponse response) {
        return tree(mapper.queryMenuWithUrl(entity));
    }

    @Override
    public List<MenuEntity> queryMenuChildList(Long id, HttpServletRequest request, HttpServletResponse response) {
        MenuEntity menuEntity = new MenuEntity();
        return findSelfAndAllChild(id.intValue(),mapper.query(menuEntity));
    }

    @Override
    public List<MenuEntity> queryRoleMenu(Long id, HttpServletRequest request, HttpServletResponse response) {
        List<MenuEntity> list = Lists.newArrayList();
        try {
            List<MenuEntity> menuEntities = mapper.queryRoleMenu(id);
            for (MenuEntity menuEntity : menuEntities) {
                List<ButtonEntity> buttonEntities = buttonMapper.queryMenuButton(menuEntity.getId());
                if (buttonEntities!=null&&buttonEntities.size()>0){
                    for (ButtonEntity buttonEntity : buttonEntities) {
                        buttonEntity.setRouteUrl(apiUrlMapper.selectByMenuId(buttonEntity.getId()));
                    }
                    menuEntity.setButtonEntities(buttonEntities);
                }
            }
            list =  tree(menuEntities);
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }
}