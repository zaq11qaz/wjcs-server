package com.huihe.eg.news.service.web;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.huihe.eg.news.model.CuriosityEntity;
import com.huihe.eg.news.mybatis.dao.CuriosityMapper;
import com.huihe.eg.news.service.dao.CuriosityService;
import com.huihe.eg.news.service.web.boot.CuriositySearchRepository;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elasticsearch.client.Client;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Api(value="好奇、主题可用接口说明",description="好奇、主题可用接口说明",tags = {"好奇、主题"})
@RestController
@RequestMapping("curiosity")
public class CuriosityController extends BaseFrameworkController<CuriosityEntity, Long> {

    @Resource
    private CuriosityService service;
    @Resource
    private CuriosityMapper curiosityMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private CuriositySearchRepository curiositySearchRepository;
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public void init() {
        setBaseService(service);
    }



    /**
     * 查询关注内容（关注）
     * @param param oper_id
     * @return List<CuriosityEntity>
     *     2019年1月15日
     *     zwx
     */
    @ApiOperation(value = "查询好奇内容(关注)", httpMethod = "GET", notes = "查询好奇内容(关注)")
    @GetMapping({"queryCuriosityListPage"})
    public ResultParam queryCuriosityListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){

        return ResultUtil.success(service.queryCuriosityListPage(param,request,response));
    }
    /**
     * 查询推荐内容
     * @param param oper_id
     * @return List<CuriosityEntity>
     *     2019年1月16日
     *     zwx
     */
    @ApiOperation(value = "查询推荐内容", httpMethod = "GET", notes = "查询推荐内容")
    @GetMapping({"queryRecommendCuriosityListPage"})
    public ResultParam queryRecommendCuriosityListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){

        return ResultUtil.success(service.queryRecommendCuriosityListPage(param,request,response));
    }

    /**
     * 热门
     * zwx
     * 2019年1月16日
     *
     */
    @ApiOperation(value = "热门好奇", httpMethod = "GET", notes = "热门好奇")
    @GetMapping({"queryHotRankingListPage"})
    public ResultParam queryHotRankingListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){

        return ResultUtil.success(service.queryHotRankingListPage(param,request,response));
    }
    /**
     * 月榜
     * zwx
     * 2019年1月16日
     *
     */
    @ApiOperation(value = "月榜", httpMethod = "GET", notes = "月榜")
    @GetMapping({"queryMonthRankingListPage"})
    public ResultParam queryMonthRankingListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryMonthRankingListPage(param,request,response));
    }
    /**
     * 周榜
     * zwx
     * 2019年1月16日
     *
     */
    @ApiOperation(value = "周榜", httpMethod = "GET", notes = "周榜")
    @GetMapping({"queryWeekRankingListPage"})
    public ResultParam queryWeekRankingListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryWeekRankingListPage(param,request,response));
    }

    /**
     * 猜你好奇
     * zwx
     * 2019年1月16日
     *
     */
    @ApiOperation(value = "猜你好奇", httpMethod = "GET", notes = "猜你好奇")
    @GetMapping({"queryGuessListPage"})
    public ResultParam queryGuessListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryGuessListPage(param,request,response));

    }
    /**
     * 好奇首页
     * zwx
     * 2019年1月16日
     *
     */
    @ApiOperation(value = "好奇首页", httpMethod = "GET", notes = "好奇首页")
    @GetMapping({"queryHomeListPage"})
    public ResultParam  queryHomeListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryHomeListPage(param,request,response));

    }
    /**
     * 周榜
     * zwx
     * 2019年1月16日
     *
     */
    @ApiOperation(value = "内容审核", httpMethod = "GET", notes = "内容审核")
    @GetMapping({"queryAuditListPage"})
    public ResultParam  queryAuditListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryAuditListPage(param,request,response));

    }
    /**
     * 分类下好奇
     * @Description: 分类下好奇
     * @author zwx
     * @Date : 2019年2月21日15:02:42
     */
    @ApiOperation(value = "分类下好奇", httpMethod = "GET", notes = "分类下好奇")
    @GetMapping({"queryCuriostyByClassfiyListPage"})
    public ResultParam queryCuriostyByClassfiyListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryCuriostyByClassfiyListPage(param,request,response));
    }
    @ApiOperation(
            value = "添加",
            httpMethod = "POST",
            notes = "添加"
    )
    @PostMapping({"insert"})
    @Override
    public ResultParam insert(@RequestBody @ApiParam(name = "添加对象",value = "添加对象",required = true) CuriosityEntity param, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam=null;
        try {
            resultParam=service.insert(param,request,response);
            if(resultParam!=null&&resultParam.getCode()==0) {
                curiositySearchRepository.save(param);
                return ResultUtil.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(1008,"失败");
    }
    @PostMapping({"update"})
    @Override
    public ResultParam update(@RequestBody @ApiParam(name = "修改对象",value = "修改对象",required = true) CuriosityEntity param, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam=null;
        try {
            resultParam=service.update(param,request,response);
            if(resultParam.getCode()==0) {
                CuriosityEntity curiosityEntity=new CuriosityEntity();
                curiosityEntity.setId(param.getId());
                curiosityEntity.setPageSize(1);
                List<CuriosityEntity> curiosityEntities=curiosityMapper.queryListPage(curiosityEntity);
                if(curiosityEntities!=null&&curiosityEntities.size()>0){
                    curiositySearchRepository.save(curiosityEntities.get(0));
                    return  ResultUtil.success();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  ResultUtil.error(10007,"失败");
    }
        /**
         * 搜索
         * @param queryString
         * @return
         * 2019年3月6日15:03:40
         * zwx
         */
    @ApiOperation(value = "搜索", httpMethod = "GET", notes = "搜索")
    @GetMapping({"querySearch"})
    public ResultParam querySearch(String queryString) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(queryString);
        Iterable<CuriosityEntity> searchResult = curiositySearchRepository.search(builder);
        List<CuriosityEntity> curiosities= Lists.newArrayList(searchResult);
        return ResultUtil.success(curiosities);

    }

    @RabbitListener(queues = "insertESCuriosity")
    public void insertESCuriosity() throws Exception{
        try {
            CuriosityEntity curiosityEntity=new CuriosityEntity();
            List<CuriosityEntity> curiosityEntities=curiosityMapper.query(curiosityEntity);
            for (CuriosityEntity entity : curiosityEntities) {
                curiositySearchRepository.save(curiosityEntity);
            }
            System.out.println("insertESCuriosity 插入完了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有
     * @throws Exception
     */
    @ApiOperation(value = "查询所有", httpMethod = "GET", notes = "查询所有")
    @GetMapping({"searchAll"})
    public List<Map<String, Object>> searchAll() throws Exception {
        //这一步是最关键的
        Client client = elasticsearchTemplate.getClient();
        // @Document(indexName = "product", type = "book")
        SearchRequestBuilder srb = client.prepareSearch("news").setTypes("curiosity");
        SearchResponse sr = srb.setQuery(QueryBuilders.matchAllQuery()).execute().actionGet(); // 查询所有
        SearchHits hits = sr.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            Map<String, Object> source = hit.getSource();
            list.add(source);
            System.out.println(hit.getSourceAsString());
        }
        return list;
    }
    @RequestMapping("queryAddCount")
    @ResponseBody
    public Integer queryAddCount(@RequestBody Long user_id) {
        return service.queryAddCount(user_id);
    }

}