package com.huihe.eg.authorization.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.authorization.model.MenuEntity;
import com.huihe.eg.authorization.service.dao.MenuService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value="菜单信息可用接口说明",description="菜单信息可用接口说明",tags = {"菜单信息"})
@RestController
@RequestMapping("menu")
public class MenuController extends BaseFrameworkController<MenuEntity, Long> {

    @Resource
    private MenuService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
    * @Description:菜单树
    * @Param:
    * @return:
    * @Author: zwy
    * @Date: 2020/8/8
    */
    @RequestMapping(value = "queryMenuList", method = RequestMethod.GET)
    @ApiOperation(value = "菜单树", httpMethod = "GET", notes = "菜单树")
    public ResultParam queryMenuList(MenuEntity entity, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMenuList(entity, request, response));
    }

    /**
    * @Description:根据id查询子菜单
    * @Param:
    * @return:
    * @Author: zwy
    * @Date: 2020/8/8
    */
    @RequestMapping(value = "queryMenuChildList", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询子菜单", httpMethod = "GET", notes = "根据id查询子菜单")
    public ResultParam queryMenuChildList(@Param("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMenuChildList(id, request, response));
    }


    /**
     * @Description:根据身份id查询菜单列表
     * @Param:
     * @return:
     * @Author: zwy
     * @Date: 2020/8/8
     */
    @RequestMapping(value = "queryRoleMenu", method = RequestMethod.GET)
    @ApiOperation(value = "根据身份id查询菜单列表", httpMethod = "GET", notes = "根据身份id查询菜单列表")
    public ResultParam queryRoleMenu(@Param("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryRoleMenu(id, request, response));
    }



}