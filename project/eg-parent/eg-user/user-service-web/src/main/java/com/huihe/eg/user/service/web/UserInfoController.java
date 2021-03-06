package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.CommonUtils;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.cy.framework.util.safe.MD5Util;
import com.google.common.collect.Lists;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.UserInfoMapper;
import com.huihe.eg.user.mybatis.dao.UserMapper;
import com.huihe.eg.user.service.dao.CountryLanguageService;
import com.huihe.eg.user.service.dao.SettledAnchorService;
import com.huihe.eg.user.service.dao.UserInfoService;
import com.huihe.eg.user.service.impl.mail.MailService;
import com.huihe.eg.user.service.impl.sms.SmsService;
import com.huihe.eg.user.service.web.boot.UserInfoSearchRepository;
import org.elasticsearch.client.transport.TransportClient;
import io.swagger.annotations.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户基本信息
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value = "用户基本信息可用接口说明", description = "用户基本信息可用接口说明", tags = {"用户基本信息"})
@RestController
@RequestMapping("userInfo")
public class UserInfoController extends BaseFrameworkController<UserInfoEntity, Long> {

    @Resource
    private UserInfoService service;
    @Resource
    private SmsService smsService;
    @Resource
    private UserInfoSearchRepository userInfoSearchRepository;
    @Resource
    private CountryLanguageService countryLanguageService;
    @Resource
    private SettledAnchorService settledAnchorService;
    @Resource
    private MailService mailService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserInfoMapper userInfoMapper;


    @Override
    public void init() {
        setBaseService(service);
    }

    private TransportClient client;

    /**
     * 后台 用户管理 用户统计
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "后台 用户管理 用户统计")
    @GetMapping("userCount")
    public ResultParam userCount(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.userCount(param, request, response));
    }

    /**
     * 后台 用户管理 注册统计
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "后台 用户管理 注册统计")
    @GetMapping("registerCount")
    public ResultParam registerCount(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.userCount(param, request, response));
    }

    @RabbitListener(queues = "queueUpdateUserInfo")
    public void queueUpdateUserInfo(UserInfoEntity infoEntity) throws Exception {
        try {
            service.queueUpdateUser(infoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * app 遇见列表接口
     *
     * @author zwx
     * @date 2019年11月26日09:37:23
     * @since JDK1.8
     */
    @ApiOperation(value = "app 遇见列表接口")
    @PostMapping("queryUserListPage")
    public ResultParam queryUserListPage(@RequestBody UserInfoEntity entity, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryUserListPage(entity, request, response));
    }

    /**
     * 修改用户支付密码
     *
     * @author zwx
     * @date 2019年11月26日09:37:23
     * @since JDK1.8
     */
    @ApiOperation(value = "修改用户支付密码")
    @PostMapping("updateUserPayPass")
    public ResultParam updateUserPayPass(@RequestBody UserInfoEntity entity , HttpServletRequest request, HttpServletResponse response) {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUser_id(entity.getUser_id());
        userInfoEntity.setPay_pass(MD5Util.GetMD5Code(entity.getPay_pass()));
        return super.update(userInfoEntity, request, response);
    }

    /**
     * 用户详情
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(value = "用户详情")
    @GetMapping("queryUserInfoById")
    public ResultParam queryUserInfoById(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryUserInfoById(param, request, response);
    }

    @ApiOperation(
            value = "导入redis",
            httpMethod = "GET",
            notes = "导入redis"
    )
    @GetMapping({"insertRedis"})
    public void insertRedis(UserInfoEntity param) {
        try {
            service.queryUserinfoInsertRedis(param);
            System.out.println("insertRedis完了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(
            value = "删除user_id",
            httpMethod = "GET",
            notes = "删除user_id"
    )
    @GetMapping({"deleteRedisById"})
    public void deleteRedisById(UserInfoEntity param) {
        try {
            service.deleteRedisById(param);
            System.out.println(param.getUser_id()+"Redis删除完了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(
            value = "批量导入redis",
            httpMethod = "GET",
            notes = "批量导入redis"
    )
    @GetMapping({"queryUserinfoInsertRedis"})
    @RabbitListener(queues = "queryUserinfoInsertRedis")
    public void queryUserinfoInsertRedis() {
        try {
            UserInfoEntity param = new UserInfoEntity();
            service.queryUserinfoInsertRedis(param);
            System.out.println("queryUserinfoInsertRedis完了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询好友列表
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(value = "查询好友列表")
    @PostMapping("queryFriendInfo")
    public ResultParam queryFriendInfo(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryFriendInfo(param, request, response));
    }

    /**
     * TIM注册
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(
            value = "TIM注册",
            httpMethod = "POST",
            notes = "TIM注册"
    )
    @PostMapping({"registerTim"})
    public ResultParam TIMregister(@RequestBody UserInfoEntity entity, HttpServletRequest request, HttpServletResponse response) {
        this.init();
        return ResultUtil.success(service.TIMregister(entity));
    }

    /**
     * 批量导入TIM
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(
            value = "批量导入TIM",
            httpMethod = "GET",
            notes = "批量导入TIM"
    )
    @GetMapping({"batchImportUserid"})
    public void batchImportUserid(HttpServletRequest request, HttpServletResponse response) {
        this.init();
        service.batchImportUserid();
    }

    /**
     * 验证修改ce
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(
            value = "验证修改用户资料",
            httpMethod = "POST",
            notes = "验证修改用户资料"
    )
    @PostMapping({"updateUserInfoByCode"})
    public ResultParam updateUserInfoByCode(@RequestBody UserInfoEntity entity, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam = null;
        try {
            resultParam = smsService.validate(entity.getMobile(), entity.getVali_code());
            if (resultParam.getCode() != 0) {
                return resultParam;
            } else {
                resultParam = service.update(entity, request, response);
                if (resultParam.getCode() == 0) {
                    UserInfoEntity userInfoEntity = new UserInfoEntity();
                    userInfoEntity.setUser_id(entity.getUser_id());
                    userInfoEntity.setPageSize(1);
                    List<UserInfoEntity> userInfoEntities = userInfoMapper.queryListPage(userInfoEntity);
                    if (userInfoEntities != null && userInfoEntities.size() > 0) {
                        userInfoEntity = userInfoEntities.get(0);
                        userInfoSearchRepository.save(userInfoEntity);
                        return ResultUtil.success();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10021.getCode(), UserEum.user_10021.getDesc());
    }

    /**
     * 新增全部用户资料es数据
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(value = "新增全部用户资料es数据", httpMethod = "GET", notes = "新增全部用户资料es数据")
    @GetMapping({"insertEsUserInfo"})
    @RabbitListener(queues = "insertEsUserInfo")
    public void insertEsUserInfo() {
        userInfoSearchRepository.deleteAll();
        System.out.println("deleteAllEsUserInfo 删除完了");
        UserInfoEntity entity = new UserInfoEntity();
        List<UserInfoEntity> infoEntities = userInfoMapper.query(entity);
        try {
            for (UserInfoEntity infoEntity : infoEntities) {
                userInfoSearchRepository.save(infoEntity);
            }
            System.out.println("insertEsUserInfo 插入完了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 修改用户TIM资料
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(
            value = "修改用户TIM资料",
            httpMethod = "POST",
            notes = "修改用户TIM资料"
    )
    @PostMapping({"updatetimUserInfo"})
    public void updatetimUserInfo(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        service.updatetimUserInfo(param, request, response);
    }

    /**
     * 修改
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @PostMapping({"update"})
    @Override
    public ResultParam update(@RequestBody @ApiParam(name = "修改对象", value = "修改对象", required = true) UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam = null;
        try {
            resultParam = service.update(param, request, response);
            if (resultParam.getCode() == 0) {
                UserInfoEntity entity = new UserInfoEntity();
                entity.setUser_id(param.getUser_id());
                //entity.setPageSize(1);
                List<UserInfoEntity> userInfoEntities = userInfoMapper.queryListPage(entity);
                if (userInfoEntities != null && userInfoEntities.size() > 0) {
                    userInfoSearchRepository.save(userInfoEntities.get(0));
                    return ResultUtil.success();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultParam;
    }

    /**
     * 全部删除es数据
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(
            value = "全部删除es数据",
            httpMethod = "POST",
            notes = "全部删除es数据"
    )
    @PostMapping({"deleteEsAll"})
    public void deleteEsAll(HttpServletRequest request, HttpServletResponse response) {
        userInfoSearchRepository.deleteAll();
    }

    /**
     * 搜索
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(value = "搜索", httpMethod = "GET", notes = "搜索")
    @GetMapping({"querySearch"})
    public ResultParam querySearch(String queryString) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(queryString);
        Iterable<UserInfoEntity> searchResult = userInfoSearchRepository.search(builder);
        List<UserInfoEntity> userInfoEntities = Lists.newArrayList(searchResult);
        return ResultUtil.success(userInfoEntities);
    }

    /**
     * 查询总数
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(value = "查询总数", httpMethod = "GET", notes = "查询总数")
    @GetMapping({"queryListPageCount"})
    @Override
    public ResultParam queryListPageCount(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        this.init();
        return ResultUtil.success(service.queryListPageCount(param, request, response));
    }

    /**
     * 管理分配用户
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(value = "管理分配用户", httpMethod = "POST", notes = "管理分配用户")
    @PostMapping({"updateUserBatch"})
    public ResultParam updateUserBatch(@RequestBody UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        this.init();
        return ResultUtil.success(service.updateUserBatch(param, request, response));
    }

    /**
     * 分页查询最新真实用户
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(value = "分页查询最新真实用户", httpMethod = "GET", notes = "分页查询最新真实用户")
    @GetMapping({"queryNewListPage"})
    public ResultParam queryNewListPage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryNewListPage(param, request, response));
    }

    /**
     * 个人用户分享
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(value = "个人用户分享", httpMethod = "GET", notes = "个人用户分享")
    @GetMapping({"queryUserInfo"})
    public ResultParam queryUserInfo(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("userinfo", service.queryUserInfoById(param, request, response));
        UserInfoEntity infoEntity = new UserInfoEntity();
        map.put("userlist", service.queryListPage(infoEntity, request, response));
        return ResultUtil.success(map);
    }

    /**
     * 修改语言
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(value = "修改语言", httpMethod = "GET", notes = "修改语言")
    @GetMapping({"updateLanguage"})
    public ResultParam updateLanguage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        param.setIs_robot(true);
        List<UserInfoEntity> userInfoEntities = service.query(param, request, response);
        for (UserInfoEntity entity : userInfoEntities) {
            if (entity.getIs_robot() != null && entity.getIs_robot()) {
                System.out.println(entity.getUser_id());
                if (!"中文爱好者".equalsIgnoreCase(entity.getOverseas_identity_name())) {
                    CountryLanguageEntity countryLanguageEntity = new CountryLanguageEntity();
                    countryLanguageEntity.setCountry(entity.getHometown());
                    List<CountryLanguageEntity> countryLanguageEntities = countryLanguageService.query(countryLanguageEntity, request, response);
                    if (countryLanguageEntities != null && countryLanguageEntities.size() > 0) {
                        UserInfoEntity infoEntity = new UserInfoEntity();
                        infoEntity.setUser_id(entity.getUser_id());
                        infoEntity.setMother_tongue(countryLanguageEntities.get(0).getLanguage());
                        service.update(infoEntity, request, response);
                    }
                    CountryLanguageEntity countryLanguage = new CountryLanguageEntity();
                    countryLanguage.setCountry(entity.getCountry());
                    List<CountryLanguageEntity> countryLanguages = countryLanguageService.query(countryLanguage, request, response);
                    if (countryLanguages != null && countryLanguages.size() > 0) {
                        UserInfoEntity infoEntity = new UserInfoEntity();
                        infoEntity.setUser_id(entity.getUser_id());
                        String languages = "";
                        if (countryLanguageEntities != null && countryLanguageEntities.size() > 0) {
                            if (!countryLanguages.get(0).getLanguage().equalsIgnoreCase(countryLanguageEntities.get(0).getLanguage())) {
                                languages = countryLanguages.get(0).getLanguage() + "/" + countryLanguageEntities.get(0).getLanguage();
                            } else {
                                languages = countryLanguageEntities.get(0).getLanguage();
                            }
                        }
                        infoEntity.setLanguages(languages);
                        service.update(infoEntity, request, response);
                    }
                }
            }
        }
        return ResultUtil.success();
    }

    /**
     * 后台机器人用户
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(value = "后台机器人用户", httpMethod = "GET", notes = "后台机器人用户")
    @GetMapping({"queryBackstage"})
    public ResultParam queryBackstage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryBackstage(param, request, response));
    }

    /**
     * 在线用户统计
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(value = "在线用户统计", httpMethod = "GET", notes = "在线用户统计")
    @GetMapping({"queryUserOnline"})
    public ResultParam queryUserOnline(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryUserOnline(param, request, response));
    }

    /**
     * 在线用户统计
     *
     * @author zwx
     * @date 2019年11月26日09:37:47
     * @since JDK1.8
     */
    @ApiOperation(value = "会员管理-注册信息", httpMethod = "GET", notes = "会员管理-注册信息")
    @GetMapping({"queryMemberListPage"})
    public ResultParam queryMemberListPage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMemberListPage(param, request, response));
    }

    /*@ApiOperation(value = "删除TIM账号", httpMethod = "GET", notes = "删除TIM账号")
    @GetMapping({"deletetUserid"})
    public void deletetUserid(Long user_id){
        service.deletetUserid(user_id);
    }*/
    @RequestMapping("queryIsEstablishGroupClass")
    @ResponseBody
    public Boolean queryIsEstablishGroupClass(@RequestParam Long user_id, HttpServletRequest request, HttpServletResponse response) {
        UserInfoEntity infoEntity = service.findById(user_id, request, response);
        SettledAnchorEntity settledAnchorEntity = new SettledAnchorEntity();
        settledAnchorEntity.setUser_id(user_id);
        settledAnchorEntity.setStatus(1);
        List<SettledAnchorEntity> settledAnchorEntities = settledAnchorService.queryListPage(settledAnchorEntity, request, response);
        if (settledAnchorEntities != null && settledAnchorEntities.size() > 0) {
            return true;
        }
        return infoEntity.getIs_help();
    }

    /**
     * 根据条件查询userInfo
     *
     * @author: zwy
     * @date: 11:20 2020/7/10
     * @since: JDk1.8
     */
    @ApiOperation(value = "根据条件查询userInfo", httpMethod = "GET", notes = "根据条件查询userInfo")
    @RequestMapping("queryByMessage")
    public ResultParam queryByMessage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryByMessage(param, request, response));
    }

    /**
     * 查询每个助学师
     *
     * @author: zwy
     * @date: 11:20 2020/7/10
     * @since: JDk1.8
     */
    @ApiOperation(value = "查询每个助学师", httpMethod = "GET", notes = "查询每个助学师")
    @RequestMapping("queryEachMaster")
    public ResultParam queryEachMaster(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryEachMaster(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/11 16:18
     * @ Description：更新用户邀请码
     * @since: JDk1.8
     */
    @GetMapping("updateInviteCode")
    @RabbitListener(queues = "updateInviteCode")
    @ApiOperation(value = "更新用户邀请码", httpMethod = "GET", notes = "更新用户邀请码")
    public void updateInviteCode() {
        UserInfoEntity param = new UserInfoEntity();
        service.updateInviteCode(param);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/11 16:18
     * @ Description：查询助学师相关信息
     * @since: JDk1.8
     */
    @GetMapping("queryHelperInfoListPage")
    @ApiOperation(value = "查询助学师相关信息", httpMethod = "GET", notes = "查询助学师相关信息")
    public ResultParam queryHelperInfoListPage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryHelperInfoListPage(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/10/29 14:20
     * @ Description：删除userSign
     * @ since: JDk1.8
     */
    @ApiOperation(value = "删除userSign", notes = "删除userSign")
    @GetMapping("deleteUserSign")
    @ResponseBody
    public ResultParam deleteUserSign(Long id, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.deleteUserSign(id, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/11 9:24
     * @ Description：搜索好友
     * @ since: JDk1.8
     */
    @ApiOperation(value = "搜索好友", notes = "搜索好友")
    @GetMapping("queryFindFriends")
    public ResultParam queryFindFriends(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserInfoEntity> list = Lists.newArrayList();
        try {
            if (StringUtil.isNotEmpty(param.getLogin_name())) {
                // 构建布尔查询
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                //分页参数
                PageRequest page;

                if (mailService.isValidEmail(param.getLogin_name())) {
                    page = PageRequest.of(0, 1);
                    boolQueryBuilder.must(QueryBuilders.matchQuery("mail", "*" + param.getLogin_name() + "*"));

                } else if (CommonUtils.isPhone(param.getLogin_name())) {
                    page = PageRequest.of(0, 1);
                    boolQueryBuilder.must(QueryBuilders.matchQuery("mobile", "*" + param.getLogin_name() + "*"));

                } else {
                    page = PageRequest.of(param.getStartRow(), param.getPageSize());
                    // 关键字查询
                    boolQueryBuilder.must(QueryBuilders.matchQuery("nick_name", "*" + param.getLogin_name() + "*"));
                }

                Page<UserInfoEntity> search = userInfoSearchRepository.search(boolQueryBuilder, page);
                list = Lists.newArrayList(search);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(list);
    }


    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/20 15:33
     * @ Description：老师列表
     * @ since: JDk1.8
     */
    @ApiOperation(value = "老师列表")
    @GetMapping("queryMechanismMasterList")
    public ResultParam queryMechanismMasterList(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMechanismMasterList(param, request, response));
    }

    @ApiOperation(value = "教付保用户统计")
    @GetMapping("queryTeachPayUserStatistics")
    public ResultParam queryTeachPayUserStatistics(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryTeachPayUserStatistics(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/22 9:45
     * @ Description：查询md5
     * @ since: JDk1.8
     */
    @ApiOperation(
            value = "更新用户管理员id",
            httpMethod = "POST",
            notes = "更新用户管理员id"
    )
    @RequestMapping("updateUserManagerId")
    @ResponseBody
    public Integer updateUserManagerId(Long id,Long user_id,HttpServletRequest request, HttpServletResponse response){
        try {
            userInfoMapper.updateManagerId(id,user_id);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

}
