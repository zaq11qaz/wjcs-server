package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.MasterInfoEntity;
import com.huihe.eg.user.model.SpecialLecturerEntity;
import com.huihe.eg.user.model.UserInfoEntity;
import com.huihe.eg.user.mybatis.dao.MasterInfoMapper;
import com.huihe.eg.user.mybatis.dao.SpecialLecturerMapper;
import com.huihe.eg.user.mybatis.dao.UserInfoMapper;
import com.huihe.eg.user.service.dao.MasterInfoService;
import com.huihe.eg.user.service.dao.SpecialLecturerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialLecturerServiceImpl extends BaseFrameworkServiceImpl<SpecialLecturerEntity, Long> implements SpecialLecturerService {

    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Resource
    private SpecialLecturerMapper mapper;
    @Resource
    private MasterInfoService masterInfoService;
    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(SpecialLecturerEntity specialLecturerEntity, HttpServletRequest request, HttpServletResponse response) {
        //若user_id非空 更新masterInfo状态
        if (specialLecturerEntity.getUser_id() != null) {
            masterInfoMapper.updateIsSpecial(specialLecturerEntity.getUser_id());
        } else {
            return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
        }
         //若存在 更新  不存在 新增
        SpecialLecturerEntity specialLecturerEntity1 = new SpecialLecturerEntity();
        specialLecturerEntity1.setUser_id(specialLecturerEntity.getUser_id());
        specialLecturerEntity1.setPageSize(1);
        List<SpecialLecturerEntity> query = mapper.queryListPage(specialLecturerEntity1);
        if (query != null && query.size() > 0) {
            specialLecturerEntity.setId(query.get(0).getId());
            return super.update(specialLecturerEntity, request, response);
        }
        MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
        masterInfoEntity.setUser_id(specialLecturerEntity.getUser_id());
        masterInfoEntity = masterInfoMapper.queryListPage(masterInfoEntity).get(0);
        masterInfoEntity.setMechanism_id(6L);
        masterInfoEntity.setCountry_type(specialLecturerEntity.getCountry_type());
        masterInfoService.updateMechanismID(masterInfoEntity,request , response);
        UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(masterInfoEntity.getUser_id());
        userInfoEntity.setAdmin_id(6L);
        userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);
        return super.insert(specialLecturerEntity, request, response);
    }

    @Override
    public ResultParam updateCancel(SpecialLecturerEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (param.getUser_id() != null) {
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(param.getUser_id());
                masterInfoEntity.setType("live_lecturer");
                List<MasterInfoEntity> query = masterInfoMapper.query(masterInfoEntity);
                if (query != null && query.size() > 0) {
                    masterInfoEntity = query.get(0);
                    masterInfoEntity.setIs_special(false);
                    masterInfoMapper.updateByPrimaryKeySelective(masterInfoEntity);
                    return ResultUtil.success();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }
}