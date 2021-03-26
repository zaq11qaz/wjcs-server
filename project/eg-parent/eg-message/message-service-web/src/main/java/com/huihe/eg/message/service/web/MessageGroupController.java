package com.huihe.eg.message.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.huihe.eg.message.model.MessageGroupEntity;
import com.huihe.eg.message.mybatis.dao.MessageGroupMapper;
import com.huihe.eg.message.service.dao.MessageGroupService;
import com.huihe.eg.message.service.web.boot.MessageGroupSearchRepository;
import io.swagger.annotations.Api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户群列表记录
 *
 * @author zwx
 * @date 2019年11月26日11:21:54
 * @since JDK1.8
 */
@Api(value = "用户群列表记录可用接口说明", description = "用户群列表记录可用接口说明", tags = {"用户群列表记录"})
@RestController
@RequestMapping("messageGroup")
public class MessageGroupController extends BaseFrameworkController<MessageGroupEntity, Long> {

    @Resource
    private MessageGroupService service;
    @Resource
    private MessageGroupMapper messageGroupMapper;
    @Resource
    private MessageGroupSearchRepository messageGroupSearchRepository;

    @Override
    public void init() {
        setBaseService(service);
    }

    /* 定时新增全部动态es数据
     * @return
     */
    @RabbitListener(queues = "updateGroupDuration")
    public void updateGroupDuration(Map<String, Object> map) throws Exception {
        try {
            if (map != null && map.containsKey("group_id") && map.containsKey("type")) {
                service.updateGroupDuration(Long.valueOf(map.get("group_id").toString()), map.get("type").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("queryGroupInfo")
    @ResponseBody
    public String queryGroupInfo(@RequestBody Long group_id, HttpServletRequest request, HttpServletResponse response) {
        return JSONUtils.obj2Json(service.findById(group_id, request, response)).toString();
    }

    /**
     * 更新开课状态
     *
     * @author zwx
     * @date 2019年11月26日11:23:04
     * @since JDK1.8
     */
    @ApiOperation(value = "更新开课状态", httpMethod = "POST", notes = "更新开课状态")
    @PostMapping({"updateClassBeginsStatus"})
    public ResultParam updateClassBeginsStatus(@RequestBody MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.updateClassBeginsStatus(param, request, response);
    }

    /**
    * @Description: 商品直播开播
    * @Param:
    * @return:
    * @Author: zwy
    * @Date: 2021/2/25
    */
    @ApiOperation(value = "商品直播开播", httpMethod = "POST", notes = "商品直播开播")
    @PostMapping({"updateLivingBeginsStatus"})
    public ResultParam updateLivingBeginsStatus(@RequestBody MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.updateLivingBeginsStatus(param, request, response);
    }

    /**
     * 我的直播课堂
     *
     * @author zwx
     * @date 2019年11月26日11:23:04
     * @since JDK1.8
     */
    @ApiOperation(value = "我的直播课堂", httpMethod = "GET", notes = "我的直播课堂")
    @GetMapping({"queryMyListPage"})
    public ResultParam queryMyListPage(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMyListPage(param, request, response));
    }

    @RequestMapping("queryMyClassCount")
    @ResponseBody
    public Integer queryMyClassCount(@RequestBody Long user_id, HttpServletRequest request, HttpServletResponse response) {
        MessageGroupEntity param = new MessageGroupEntity();
        param.setOwner_id(user_id);
        return service.queryMyClassCount(param, request, response);
    }

    /**
     * 班级统计
     *
     * @author: zwy
     * @date: 9:44 2020/7/8
     * @since: JDk1.8
     */
    @ApiOperation(value = "班级统计", httpMethod = "GET", notes = "班级统计")
    @GetMapping({"queryClassCount"})
    public ResultParam queryClassCount(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryClassCount(param, request, response));
    }

    /**
     * 班级课程统计
     *
     * @author: zwy
     * @date: 10:32 2020/7/8
     * @since: JDk1.8
     */
    @ApiOperation(value = "班级课程统计", httpMethod = "GET", notes = "班级课程统计")
    @GetMapping({"queryCourseCount"})
    public ResultParam queryCourseCount(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryCourseCount(param, request, response));
    }

    /**
     * 条件查询课堂
     *
     * @author: zwy
     * @date: 13:27 2020/7/10
     * @since: JDk1.8
     */
    @ApiOperation(value = "条件查询课堂", httpMethod = "GET", notes = "条件查询课堂")
    @GetMapping({"queryByMessage"})
    public ResultParam queryByMessage(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryByMessage(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/12 9:45
     * @ Description：新增全部用户群组es数据
     * @ since: JDk1.8
     */
    @ApiOperation(value = "新增全部用户群组es数据", httpMethod = "GET", notes = "新增全部用户群组es数据")
    @GetMapping({"insertEsMessageGroup"})
    @RabbitListener(queues = "insertEsMessageGroup")
    public void insertEsMessageGroup() {
        MessageGroupEntity entity = new MessageGroupEntity();
        List<MessageGroupEntity> infoEntities = messageGroupMapper.query(entity);
        try {
            for (MessageGroupEntity infoEntity : infoEntities) {
                messageGroupSearchRepository.save(infoEntity);
            }
            System.out.println("insertEsMessageGroup 插入完了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/12 9:45
     * @ Description：新增全部用户群组es数据
     * @ since: JDk1.8
     */
    @ApiOperation(value = "新增全部用户群组es数据", httpMethod = "GET", notes = "新增全部用户群组es数据")
    @GetMapping({"queryByEs"})
    public ResultParam queryByEs(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MessageGroupEntity> list;
        try {
            if (StringUtil.isNotEmpty(param.getGroup_name())){
                if (isStr2Num(param.getGroup_name())){
                    param.setGroup_id(param.getGroup_name());
                    param.setGroup_name(null);
                    param.setPageSize(1);
                    return ResultUtil.success(messageGroupMapper.queryListPage(param));
                }else {
                    //分页参数
                    PageRequest page = PageRequest.of(param.getStartRow(), param.getPageSize());

                    // 构建布尔查询
                    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                    // 关键字查询
                    boolQueryBuilder.must(QueryBuilders.matchQuery("group_name","*" + param.getGroup_name() + "*"));

                    Page<MessageGroupEntity> search = messageGroupSearchRepository.search(boolQueryBuilder, page);
                    list = Lists.newArrayList(search);
                    return ResultUtil.success(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }

    public static boolean isStr2Num(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}