package com.huihe.eg.authorization.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.authorization.model.ApiUrlEntity;
import com.huihe.eg.authorization.model.ManagerIdentityEntity;
import com.huihe.eg.authorization.service.dao.ApiUrlService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(value="后台接口api可用接口说明",description="后台接口api可用接口说明",tags = {"后台接口api"})
@RestController
@RequestMapping("apiUrl")
public class ApiUrlController extends BaseFrameworkController<ApiUrlEntity, Long> {

    @Resource
    private ApiUrlService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 是否需要授权登录/是否拥有权限
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "authorization", method = RequestMethod.GET)
    public ResultParam authorization(String url, String method, String token, String account, String password, HttpServletRequest request, HttpServletResponse response) {
        ApiUrlEntity entity = new ApiUrlEntity();
        entity.setUrl(url);
        entity.setMethod(method);
        entity.setToken(token);
        return ResultUtil.success(service.authorization(entity, account, password, request, response));
    }


    /**
    * @Description:查询菜单树
    * @Param:
    * @return:
    * @Author: zwy
    * @Date: 2020/8/8
    */
    @RequestMapping(value = "queryApiTree", method = RequestMethod.GET)
    public ResultParam queryApiTree(ApiUrlEntity apiUrlEntity, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryApiTree(apiUrlEntity, request, response));
    }

    /**
    * @Description:根据身份id查询所拥有按钮信息
    * @Param:
    * @return:
    * @Author: zwy
    * @Date: 2020/8/8
    */
    @GetMapping("/queryRoleIdentity")
    public ResultParam queryRoleIdentity( Long id, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryRoleIdentity(id, request, response));
    }
}