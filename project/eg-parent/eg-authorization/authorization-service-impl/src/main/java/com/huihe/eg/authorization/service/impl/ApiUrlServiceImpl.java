package com.huihe.eg.authorization.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.service.impl.DataException;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.google.common.collect.Lists;
import com.huihe.eg.authorization.model.ApiUrlEntity;
import com.huihe.eg.authorization.model.ManagerUserEntity;
import com.huihe.eg.authorization.mybatis.dao.ApiUrlMapper;
import com.huihe.eg.authorization.mybatis.dao.ManagerUserMapper;
import com.huihe.eg.authorization.service.dao.ApiUrlService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.authorization.service.impl.algorithm.ApiUrlEntityAlgorithm;
import com.huihe.eg.comm.AuthorizationEum;
import com.huihe.eg.comm.TokenUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.huihe.eg.authorization.service.impl.algorithm.ApiUrlEntityAlgorithm.tree;

@Service
public class ApiUrlServiceImpl extends BaseFrameworkServiceImpl<ApiUrlEntity, Long> implements ApiUrlService {

    @Resource
    private ApiUrlMapper mapper;
    @Resource
    private ApiUrlEntityAlgorithm apiUrlEntityAlgorithm;
    @Resource
    private ManagerUserMapper managerUserMapper;
    @Resource
    private RedisService redisService;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public Object authorization(ApiUrlEntity entity, String account, String pwd, HttpServletRequest request, HttpServletResponse response) {
        if (true){
            return null;
        }

        ManagerUserEntity managerUserEntity = null;

        if (entity.getUrl().contains("delete")){
            throw new DataException(AuthorizationEum.authorization_30004.getCode(), AuthorizationEum.authorization_30004.getDesc());
        }

        entity.setPageSize(1);
        List<ApiUrlEntity> list = mapper.queryListPage(entity);
        String str = "";

        /**
         * ???????????? ???????????????????????? ??????????????????
         */
        if (list == null || list.size() <= 0) {
            str = redisService.getStr(entity.getToken());
            /**
             * ??????????????? ?????????????????? token??????
             */
            if (StringUtil.isNotEmpty(str)) {
                return null;
            } else {
                throw new DataException(AuthorizationEum.authorization_30003.getCode(), AuthorizationEum.authorization_30003.getDesc());
            }
        }

        /**
         * ?????????
         */
        if (!list.get(0).getOpend()) {
            throw new DataException(AuthorizationEum.authorization_30000.getCode(), AuthorizationEum.authorization_30000.getDesc());
        }

        /**
         * ?????????????????????????????? ?????????
         */
        if (list.get(0).getAuthorize_account() != null && list.get(0).getAuthorize_account()) {

            //??????redis?????? ??????
            if (!entity.getToken().contains("managerToken")) {
                throw new DataException(AuthorizationEum.authorization_30004.getCode(), AuthorizationEum.authorization_30004.getDesc());
            }
        }
        str = str.replaceAll("managerToken", "");
        managerUserEntity = managerUserMapper.selectByPrimaryKey(Long.valueOf(str));

        //admin ??????
        if ("admin".equals(managerUserEntity.getType())) {//admin ??????
            return null;
        }

        entity.setManagerUser_type(managerUserEntity.getType());//??????????????????
        Integer i = managerUserMapper.queryIdenetity(entity);//?????????????????????????????????
        if (i > 0) {//????????????
            return null;
        } else {
            throw new DataException(AuthorizationEum.authorization_30004.getCode(), AuthorizationEum.authorization_30004.getDesc());
        }

       /* if (list.get(0).getAmount().compareTo(BigDecimal.ZERO) == 1) {
            //??????
            managerUserService.deductionFee(list.get(0).getAmount(), entity.getUser_id(), request, response);
        }*/
    }

    @Override
    public List<ApiUrlEntity> queryApiTree(ApiUrlEntity apiUrlEntity, HttpServletRequest request, HttpServletResponse response) {
        return tree(mapper.query(apiUrlEntity));
    }

    @Override
    public ResultParam insert(ApiUrlEntity apiUrlEntity, HttpServletRequest request, HttpServletResponse response) {
        if (apiUrlEntity.getId() != null) {
            return super.update(apiUrlEntity, request, response);
        }
        return super.insert(apiUrlEntity, request, response);
    }

    @Override
    public List<ApiUrlEntity> queryRoleIdentity(Long id, HttpServletRequest request, HttpServletResponse response) {
        List<ApiUrlEntity> list = Lists.newArrayList();
        try {
            list = tree(mapper.queryRoleIdentity(id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}