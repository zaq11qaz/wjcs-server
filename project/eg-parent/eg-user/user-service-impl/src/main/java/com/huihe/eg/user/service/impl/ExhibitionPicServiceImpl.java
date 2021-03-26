package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.ExhibitionPicEntity;
import com.huihe.eg.user.model.MasterInfoEntity;
import com.huihe.eg.user.mybatis.dao.ExhibitionPicMapper;
import com.huihe.eg.user.service.dao.ExhibitionPicService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExhibitionPicServiceImpl extends BaseFrameworkServiceImpl<ExhibitionPicEntity, Long> implements ExhibitionPicService {

    @Resource
    private ExhibitionPicMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public List<ExhibitionPicEntity> query(ExhibitionPicEntity exhibitionPicEntity, HttpServletRequest request, HttpServletResponse response) {
        List<ExhibitionPicEntity> query = super.query(exhibitionPicEntity, request, response);
        for (ExhibitionPicEntity picEntity : query) {
            picEntity.setViewing_times(picEntity.getViewing_times()+1);
            mapper.updateByPrimaryKeySelective(picEntity);
        }
        return query;
    }

    @Override
    public ResultParam updateClickCount(ExhibitionPicEntity param, HttpServletRequest request, HttpServletResponse response) {
        ExhibitionPicEntity exhibitionPicEntity = mapper.selectByPrimaryKey(param.getId());
        exhibitionPicEntity.setClick_times(exhibitionPicEntity.getClick_times()+1);
        mapper.updateByPrimaryKeySelective(exhibitionPicEntity);
        return ResultUtil.success();
    }
}