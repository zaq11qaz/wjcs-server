package com.huihe.eg.authorization.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.authorization.model.ApiUrlEntity;
import com.huihe.eg.authorization.model.ButtonEntity;
import com.huihe.eg.authorization.mybatis.dao.ButtonMapper;
import com.huihe.eg.authorization.service.dao.ApiUrlService;
import com.huihe.eg.authorization.service.dao.ButtonService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;


@Service
public class ButtonServiceImpl extends BaseFrameworkServiceImpl<ButtonEntity, Long> implements ButtonService {

    @Resource
    private ApiUrlService apiUrlService;
    @Resource
    private ButtonMapper mapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(ButtonEntity buttonEntity, HttpServletRequest request, HttpServletResponse response) {
        try {

            ApiUrlEntity apiUrlEntity = new ApiUrlEntity();
            if (buttonEntity.getId()!=null&&buttonEntity.getId()!=0){
                ButtonEntity buttonEntity1 = new ButtonEntity();
                buttonEntity1.setId(buttonEntity.getId());
                buttonEntity1.setPageSize(1);
                //设置apiurlid
                apiUrlEntity.setId(mapper.queryListPage(buttonEntity1).get(0).getUrl_id());
            }
            apiUrlEntity.setTypes(2);
            apiUrlEntity.setUrl(buttonEntity.getRouteUrl());
            apiUrlEntity.setDescr(buttonEntity.getName());
            apiUrlEntity.setOpend(buttonEntity.getOpend());
            apiUrlEntity.setAmount(buttonEntity.getAmount());
            apiUrlEntity.setMenu_id(buttonEntity.getMenu_id());
            apiUrlEntity.setSeq((long)buttonEntity.getSeq());
            apiUrlEntity.setParent_id(0L);
            //执行插入
            apiUrlService.insert(apiUrlEntity, request, response);
            //获得id
            Long id = apiUrlService.query(apiUrlEntity, request, response).get(0).getId();
            buttonEntity.setUrl_id(id);
            if ( buttonEntity.getId()!=null&&buttonEntity.getId()!=0) {
                apiUrlEntity.setUpdate_user(buttonEntity.getUpdate_user());
                mapper.updateByPrimaryKey(buttonEntity);
            } else {
                mapper.insertSelective(buttonEntity);
                buttonEntity.setId( mapper.query(buttonEntity).get(0).getId());
                apiUrlEntity.setAdd_user(buttonEntity.getAdd_user());
                apiUrlEntity.setUpdate_user(buttonEntity.getUpdate_user());
            }
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
    }
}