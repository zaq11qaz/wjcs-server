package com.huihe.eg.news.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.news.model.CuriosityEntity;
import com.huihe.eg.news.model.CuriosityViewpointEntity;
import com.huihe.eg.news.service.dao.CuriosityViewpointService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="好奇观点可用接口说明",description="好奇观点可用接口说明",tags = {"好奇观点"})
@RestController
@RequestMapping("curiosityViewpoint")
public class CuriosityViewpointController extends BaseFrameworkController<CuriosityViewpointEntity, Long> {

    @Resource
    private CuriosityViewpointService service;
    @Override
    public void init() {
        setBaseService(service);
    }
    /**
     * 精彩回应
     * @Description:
     * @author zwx
     * @Date : 2019年1月8日
     */
    @ApiOperation(value = "精彩回应", httpMethod = "GET", notes = "精彩回应")
    @GetMapping({"queryHotListPage"})
    public ResultParam queryHotListPage(CuriosityViewpointEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryHotListPage(param,request,response));

    }
    /**
     * 查看收藏的回应
     * zwx
     * 2019年1月15日
     */
    @ApiOperation(value = "查看收藏的回应", httpMethod = "GET", notes = "查看收藏的回应")
    @GetMapping({"queryCollectCuriosityListPage"})
    public ResultParam queryCollectCuriosityListPage(CuriosityViewpointEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryCollectCuriosityListPage(param,request,response));
    }
    /**
     * 相关推荐
     * zwx
     * 2019年3月27日16:30:42
     */
    @ApiOperation(value = "相关推荐", httpMethod = "GET", notes = "相关推荐")
    @GetMapping({"queryRecommendationsListPage"})
    public ResultParam queryRecommendationsListPage(CuriosityViewpointEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryRecommendationsListPage(param,request,response));
    }
    /**
     * 相关推荐
     * zwx
     * 2019年3月27日16:30:42
     */
    @ApiOperation(value = "分享", httpMethod = "POST", notes = "分享")
    @PostMapping({"queryShareListPage"})
    public ResultParam queryShareListPage(CuriosityViewpointEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<>();
        map.put("details",service.query(param,request,response));
        CuriosityViewpointEntity curiosityViewpointEntity=new CuriosityViewpointEntity();
        map.put("Recommend",service.queryListPage(curiosityViewpointEntity,request,response));
        return ResultUtil.success(map);
    }

    /**
     * 查询发布数量
     * @param user_id
     * @return
     */
    @RequestMapping("queryAddViewpointCount")
    @ResponseBody
    public Integer queryAddViewpointCount(@RequestBody Long user_id) {
        return service.queryAddViewpointCount(user_id);
    }

    /**
     * 根据id查询观点
     */
    @RequestMapping("queryViewpointById")
    @ResponseBody
    @ApiOperation(value = "根据id查询观点", httpMethod = "GET", notes = "根据id查询观点")
    public String queryViewpointById(Long id){
        this.init();
        return service.queryViewpointById(id);
    }

}