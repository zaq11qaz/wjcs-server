package com.huihe.eg.message.service.web;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.huihe.eg.comm.NewsEum;
import com.huihe.eg.message.model.NoteUserEntity;
import com.huihe.eg.message.mybatis.dao.NoteUserMapper;
import com.huihe.eg.message.service.dao.NoteUserService;
import com.huihe.eg.message.service.web.boot.NoteSearchRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 境友圈记录列表
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="境友圈记录列表可用接口说明",description="境友圈记录列表可用接口说明",tags = {"境友圈记录列表"})
@RestController
@RequestMapping("noteUser")
public class NoteUserController extends BaseFrameworkController<NoteUserEntity, Long> {

    @Resource
    private NoteUserService service;
    @Resource
    private NoteSearchRepository noteSearchRepository;
    @Resource
    private NoteUserMapper noteUserMapper;
    @Resource
    private RedisService redisService;


    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 添加对象
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @PostMapping({"insert"})
    @Override
    public ResultParam insert(@RequestBody @ApiParam(name = "添加对象",value = "添加对象",required = true) NoteUserEntity param, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam=null;
        try {
           /* if(param.getContent()!=null&&param.getContent()!=""){
                SensitiveFilterService filter = SensitiveFilterService.getInstance();
                String hou = filter.replaceSensitiveWord(param.getContent(), 1, "*");
                param.setContent(hou);
            }*/
            param.setStatus(3);
            resultParam=service.insert(param,request,response);
            if(resultParam!=null&&resultParam.getCode()==0) {
                noteSearchRepository.save(param);
                return ResultUtil.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(NewsEum.news_80008.getCode(),NewsEum.news_80008.getDesc());
    }

    /**
     * 搜索动态
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "搜索动态", httpMethod = "GET", notes = "搜索动态")
    @GetMapping({"querySearch"})
    public ResultParam querySearch(String queryString) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(queryString);
        Iterable<NoteUserEntity> searchResult = noteSearchRepository.search(builder);
        List<NoteUserEntity> curiosities= Lists.newArrayList(searchResult);
        return ResultUtil.success(curiosities);
    }


    @RabbitListener(queues = "insertNoteToEs")
    public void insertNoteToEs() throws Exception{
        try {
            NoteUserEntity entity=new NoteUserEntity();
            List<NoteUserEntity> curiosityEntities=noteUserMapper.query(entity);
            for (NoteUserEntity curiosityEntity1:curiosityEntities){
                noteSearchRepository.save(curiosityEntity1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 手动新增全部动态es数据
     * @author zwx
     * @date 2019年11月26日11:23:04
     * @since JDK1.8
     */
    @ApiOperation(value = "手动新增全部动态es数据", httpMethod = "GET", notes = "手动新增全部动态es数据")
    @GetMapping({"insertNoteToEs1"})
    public void insertNoteToEs1() {
        NoteUserEntity entity=new NoteUserEntity();
        List<NoteUserEntity> curiosityEntities=noteUserMapper.query(entity);
        for (NoteUserEntity curiosityEntity1:curiosityEntities){
            noteSearchRepository.save(curiosityEntity1);
        }
    }
    /**
     * 修改对象
     * @author zwx
     * @date 2019年11月26日11:23:04
     * @since JDK1.8
     */
    @PostMapping({"update"})
    @Override
    public ResultParam update(@RequestBody @ApiParam(name = "修改对象",value = "修改对象",required = true) NoteUserEntity param, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam=null;
        try {
            resultParam=service.update(param,request,response);
            if(resultParam.getCode()==0) {
                NoteUserEntity noteUserEntity=new NoteUserEntity();
                noteUserEntity.setId(param.getId());
                noteUserEntity.setPageSize(1);
                List<NoteUserEntity> curiosityEntities=noteUserMapper.queryListPage(noteUserEntity);
                if(curiosityEntities!=null&&curiosityEntities.size()>0){
                    noteSearchRepository.save(curiosityEntities.get(0));
                    return  ResultUtil.success();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  ResultUtil.error(10008,"失败");
    }
    @RequestMapping("queryNoteAddCount")
    @ResponseBody
    public Integer queryNoteAddCount(@RequestBody Long user_id) {
        return service.queryNoteAddCount(user_id);
    }

    @RequestMapping("queryNoteInfo")
    @ResponseBody
    public String queryNoteInfo(@RequestBody Long note_id,HttpServletRequest request, HttpServletResponse response){
        return service.queryNoteInfo(note_id,request,response);
    }
    /**
     * 分页查询收藏类别
     * @author zwx
     * @date 2019年11月26日11:23:04
     * @since JDK1.8
     */
    @ApiOperation(value = "分页查询收藏类别", httpMethod = "GET", notes = "分页查询收藏类别")
    @GetMapping({"queryCollectListPage"})
    public ResultParam queryCollectListPage(NoteUserEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryCollectListPage(param,request,response));
    }

    /**
     * 查询新消息列表
     * @author zwx
     * @date 2019年11月26日11:23:04
     * @since JDK1.8
     */
    @ApiOperation(value = "查询新消息列表", httpMethod = "GET", notes = "查询新消息列表")
    @GetMapping({"queryNoteNews"})
    public ResultParam queryNoteNews(NoteUserEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map=new HashMap<>();
//        String string=redisService.getStr(param.getOper_id()+"notenews");
//        List<Object> objects=null;
        try {
                    /*
            objects=JSONUtils.json2List(string, Object.class);
            if(objects==null){
                objects=new ArrayList<>();
            }
            map.put("notenews", objects);
            if(param.getIs_read()!=null&&param.getIs_read()){
                redisService.del(param.getOper_id()+"notenews");
            }

         */
            if (param.getStatus()==null){
                param.setStatus(1);
            }
            param.setUser_id(null);
            map.put("notenews", service.queryListPage(param, request, response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }
    /**
     * 世界圈分享
     * @author zwx
     * @date 2019年11月26日11:23:04
     * @since JDK1.8
     */
    @ApiOperation(value = "世界圈分享", httpMethod = "GET", notes = "世界圈分享")
    @GetMapping({"queryNoteShare"})
    public ResultParam queryNoteShare(NoteUserEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map=new HashMap<>();
        try {
            List<NoteUserEntity> noteUserEntities=service.query(param,request,response);
            map.put("noteinfo",noteUserEntities);
            NoteUserEntity noteUserEntity=new NoteUserEntity();
            noteUserEntity.setPageSize(10);
            noteUserEntity.setSortName("like_count");
            List<NoteUserEntity> noteUserEntities1=service.queryListPage(noteUserEntity,request,response);
            map.put("notelist",noteUserEntities1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }
}