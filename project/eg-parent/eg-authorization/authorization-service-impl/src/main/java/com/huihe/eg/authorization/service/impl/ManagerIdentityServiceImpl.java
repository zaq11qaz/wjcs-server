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
import com.huihe.eg.comm.UserEum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.DateTimeException;
import java.util.List;
import java.util.Map;

@Service
public class ManagerIdentityServiceImpl extends BaseFrameworkServiceImpl<ManagerIdentityEntity, Long> implements ManagerIdentityService {

    @Resource
    private ManagerIdentityMapper mapper;
    @Resource
    private ManagerIdentityUrlService managerIdentityUrlService;
    @Resource
    private ManagerUserService managerUserService;
    @Resource
    private ManagerIdentityUrlMapper managerIdentityUrlMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    @Transactional
    public ResultParam insert(ManagerIdentityEntity managerIdentityEntity, HttpServletRequest request, HttpServletResponse response) {
        try {
            int sort = mapper.selectMaxSort();
            managerIdentityEntity.setSort(sort);
            int i = mapper.insertSelective(managerIdentityEntity);
            if (i>0) {
                Integer[] permIds = {};
                if (managerIdentityEntity.getPermIds() != null) {
                    permIds = managerIdentityEntity.getPermIds();
                }
                if (managerIdentityEntity.getId() != null && managerIdentityEntity.getId() != 0) {
                    managerIdentityUrlMapper.deleteByIdentityId(managerIdentityEntity.getId());
                    super.update(managerIdentityEntity, request, response);
                } else {
                    super.insert(managerIdentityEntity, request, response);
                }
                for (Integer deptId : permIds) {
                    ManagerIdentityUrlEntity managerIdentityUrlEntity = new ManagerIdentityUrlEntity();
                    managerIdentityUrlEntity.setIdentity_id(mapper.query(managerIdentityEntity).get(0).getId());
                    managerIdentityUrlEntity.setUrl_id((long) deptId);
                    managerIdentityUrlService.insert(managerIdentityUrlEntity, request, response);
                }
                return ResultUtil.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
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
    @Transactional
    public synchronized ResultParam updateOrder(ManagerIdentityEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (param.getChange_id()!=null && param.getChange_id()!=0){
                ManagerIdentityEntity managerIdentityEntity = mapper.selectByPrimaryKey(param.getId());
                ManagerIdentityEntity managerIdentityEntity1 = mapper.selectByPrimaryKey(param.getChange_id());
                managerIdentityEntity.setSort(managerIdentityEntity1.getSort()+managerIdentityEntity.getSort());
                managerIdentityEntity1.setSort(managerIdentityEntity.getSort() - managerIdentityEntity1.getSort());
                managerIdentityEntity.setSort(managerIdentityEntity.getSort() - managerIdentityEntity1.getSort());
                int i1 = mapper.updateByPrimaryKeySelective(managerIdentityEntity);
                int i11 = mapper.updateByPrimaryKeySelective(managerIdentityEntity1);
                if (i1 > 0 && i11>0){
                    return ResultUtil.success();
                }
            }else if (param.getBack_id()!=null && param.getBack_id()!=0){
                int i = mapper.updateSort(param);
                int i1 = mapper.updatesortMiddle(param);
                ManagerIdentityEntity managerIdentityEntity = mapper.selectByPrimaryKey(param.getId());
                managerIdentityEntity.setSort(param.getBack_id().intValue()+1);
                int i11 = mapper.updateByPrimaryKeySelective(managerIdentityEntity);
                return ResultUtil.success();
            }
            throw new DateTimeException("????????????");
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

}