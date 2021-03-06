package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.aop.WebLog;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.MasterInfoMapper;
import com.huihe.eg.user.service.dao.UserClassCardService;
import com.huihe.eg.user.service.dao.UserCouponService;
import com.huihe.eg.user.service.dao.UserService;
import com.huihe.eg.user.service.dao.pay.WxPayService;
import com.huihe.eg.user.service.web.boot.MasterInfoSearchRepository;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="用户可用接口说明",description="用户可用接口说明",tags = {"用户"})
@RestController
@RequestMapping("user")
public class UserController extends BaseFrameworkController<UserEntity, Long> {

    @Resource
    private UserService service;
    @Resource
    private UserCouponService userCouponService;
    @Resource
    private UserClassCardService userClassCardService;
    @Resource
    private WxPayService wxPayService;
    @Resource
    private MasterInfoSearchRepository masterInfoSearchRepository;
    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 登录
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(
            value = "登录",
            httpMethod = "POST",
            notes = "登录"
    )
    @WebLog(description = "用户登录")
    @PostMapping({"login"})
    public ResultParam login(@RequestBody UserEntity entity, HttpServletRequest request, HttpServletResponse response) {
        this.init();
        return service.login(entity, request, response);
    }
    /**
     * code换密钥
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(
            value = "code换密钥",
            httpMethod = "POST",
            notes = "code换密钥"
    )
    @WebLog(description = "用户登录")
    @GetMapping({"getSessionKey"})
    public ResultParam getSessionKey( UserEntity entity, HttpServletRequest request, HttpServletResponse response) {
        this.init();
        return service.getSessionKey(entity, request, response);
    }

    /**
     * 注册
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(
            value = "注册",
            httpMethod = "POST",
            notes = "注册"
    )
    @PostMapping({"register"})
    public ResultParam register(@RequestBody  UserEntity entity, HttpServletRequest request, HttpServletResponse response) {
        this.init();
        return service.register(entity, request, response);
    }

    /**
     * 生成小程序二维码
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(
            value = "生成小程序二维码",
            httpMethod = "GET",
            notes = "生成小程序二维码"
    )
    @PostMapping({"generateQRCode"})
    public ResultParam generateQRCode(@RequestBody  GenerateQRCodeEntity entity, HttpServletRequest request, HttpServletResponse response) {
        this.init();
        return service.generateQRCode(entity, request, response);
    }

    /**
     * 清空签名
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "清空签名",
            httpMethod = "POST",
            notes = "清空签名")
    @PostMapping({"removeSign"})
    public ResultParam removeSign(@RequestBody  UserEntity entity, HttpServletRequest request, HttpServletResponse response){
        this.init();
        return  service.removeSign(entity);
    }

    @RabbitListener(queues = "updateUserOnlineState")
    public void updateUserOnlineState(Map<String,Object> map){
        service.updateUserOnlineState(map);
    }

    /**
     * 开关
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "开关",
            httpMethod = "POST",
            notes = "开关")
    @PostMapping({"ret"})
    public ResultParam ret( HttpServletRequest request, HttpServletResponse response){
        this.init();
        return ResultUtil.success(false);
    }
    /**
     * 领取劵验证
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(
            value = "领取劵验证",
            httpMethod = "POST",
            notes = "领取劵验证"
    )
    @PostMapping({"registerGentlemen"})
    public ResultParam registerGentlemen(@RequestBody  UserEntity entity, HttpServletRequest request, HttpServletResponse response) {
        this.init();
        ResultParam resultParam=null;
        Map<String,Object> map=new HashMap<>();
        try {
            if(entity.getType()!=null&& "get_live_tickets".equalsIgnoreCase(entity.getType())){
                if(entity.getUser_id()!=null&&entity.getUser_id()!=0){
                    UserClassCardEntity userClassCardEntity=new UserClassCardEntity();
                    userClassCardEntity.setType("curriculum");
                    userClassCardEntity.setUser_id(entity.getUser_id());
                    userClassCardEntity.setPageSize(1);
                    userClassCardService.AddClass(userClassCardEntity,request,response);
                }
            }
            UserEntity entity1=new UserEntity();
            entity1.setPageSize(1);
            entity1.setLogin_name(entity.getLogin_name());
            List<UserEntity> userEntities=service.queryListPage(entity1,request,response);
            if(userEntities!=null&&userEntities.size()>0){
                resultParam= service.login(entity, request, response);
                if(resultParam!=null&&resultParam.getCode()==0){
                    map = JSONUtils.obj2Map(resultParam.getData(),null);
                    map.put("is_receive","true");
                    if(map.containsKey("user_id")){
                        if (entity.getType() != null && "get_live_tickets".equalsIgnoreCase(entity.getType())) {
                            UserClassCardEntity userClassCardEntity=new UserClassCardEntity();
                            userClassCardEntity.setType("curriculum");
                            userClassCardEntity.setUser_id(userEntities.get(0).getUser_id());
                            userClassCardEntity.setPageSize(1);
                            userClassCardService.AddClass(userClassCardEntity,request,response);
                        }else{
                            UserCouponEntity userCouponEntity=new UserCouponEntity();
                            userCouponEntity.setUser_id(Long.parseLong(map.get("user_id").toString()));
                            userCouponEntity.setPageSize(1);
                            List<UserCouponEntity> userCouponEntities=userCouponService.queryListPage(userCouponEntity,request,response);
                            if(userCouponEntities!=null&&userCouponEntities.size()>0){
                                map.put("is_receive","false");
                            }
                        }
                    }
                    return ResultUtil.success(map);
                }
            }else{
                resultParam= service.register(entity, request, response);
                if(resultParam!=null&&resultParam.getCode()==0){
                    map = JSONUtils.obj2Map(resultParam.getData(),null);
                    map.put("is_receive","true");
                    if(map.containsKey("userInfoEntity")){
                        UserInfoEntity infoEntity=JSONUtils.json2Obj(map.get("userInfoEntity").toString(),UserInfoEntity.class);
                        if (entity.getType() != null && "get_live_tickets".equalsIgnoreCase(entity.getType())) {
                            UserClassCardEntity userClassCardEntity=new UserClassCardEntity();
                            userClassCardEntity.setType("curriculum");
                            userClassCardEntity.setUser_id(infoEntity.getUser_id());
                            userClassCardEntity.setPageSize(1);
                            userClassCardService.AddClass(userClassCardEntity,request,response);
                        }else{
                            UserCouponEntity userCouponEntity=new UserCouponEntity();
                            userCouponEntity.setUser_id(infoEntity.getUser_id());
                            userCouponEntity.setPageSize(1);
                            List<UserCouponEntity> userCouponEntities=userCouponService.queryListPage(userCouponEntity,request,response);
                            if(userCouponEntities!=null&&userCouponEntities.size()>0){
                                map.put("is_receive","false");
                            }
                        }
                    }
                    return ResultUtil.success(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("is_receive","false");
        return  ResultUtil.success(map);
    }
    /**
     * 验证安全密码
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(
            value = "验证安全密码",
            httpMethod = "GET",
            notes = "验证安全密码"
    )
    @GetMapping({"verificationSecurityPass"})
    public ResultParam verificationSecurityPass(UserEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.verificationSecurityPass(param,request,response);
    }
    /**
     * 查询是否设置过安全密码
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(
            value = "查询是否设置过安全密码",
            httpMethod = "GET",
            notes = "查询是否设置过安全密码"
    )
    @GetMapping({"securityPassIsNull"})
    public ResultParam securityPassIsNull(UserEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.securityPassIsNull(param,request,response);
    }
    /**
     * 第三方登录
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(
            value = "第三方登录",
            httpMethod = "POST",
            notes = "第三方登录"
    )
    @PostMapping({"openIdLoginOrRegist"})
    public ResultParam openIdLoginOrRegist(@RequestBody UserEntity param,HttpServletRequest request, HttpServletResponse response){
        return service.openIdLoginOrRegist(param,request,response);
    }
    /**
     * 第三方登录验证
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(
            value = "第三方登录验证",
            httpMethod = "POST",
            notes = "第三方登录验证"
    )
    @PostMapping({"openIdLoginVerification"})
    public ResultParam openIdLoginVerification(@RequestBody UserEntity param,HttpServletRequest request, HttpServletResponse response){
        return service.openIdLoginVerification(param,request,response);
    }
    /**
     * wxcode验证
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(
            value = "wxcode验证",
            httpMethod = "POST",
            notes = "wxcode验证"
    )
    @PostMapping({"openIdVerification"})
    public ResultParam openIdVerification(@RequestBody UserEntity param,HttpServletRequest request, HttpServletResponse response){
        return service.openIdVerification(param,request,response);
    }
    /**
     * wxopenid验证
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(
            value = "wxopenid验证",
            httpMethod = "POST",
            notes = "wxopenid验证"
    )
    @PostMapping({"openIdAppVerification"})
    public ResultParam openIdAppVerification(@RequestBody UserEntity param,HttpServletRequest request, HttpServletResponse response){
        return service.openIdAppVerification(param,request,response);
    }
    /**
     * appleid验证
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(
            value = "appleid验证",
            httpMethod = "POST",
            notes = "appleid验证"
    )
    @PostMapping({"appleIdLoginOrRegist"})
    public ResultParam appleIdLoginOrRegist(@RequestBody UserEntity param,HttpServletRequest request, HttpServletResponse response){
        return service.appleIdLoginOrRegist(param,request,response);
    }

    /**
     * 条件查询user
     * @author : zwy
     * 2020-07-30 11:55
     * @since JDK1.8
     */
    @ApiOperation(
            value = "条件查询user",
            httpMethod = "GET",
            notes = "条件查询user"
    )
    @GetMapping({"queryByMessage"})
    public ResultParam queryByMessage( UserEntity param,HttpServletRequest request, HttpServletResponse response){
        return service.queryByMessage(param,request,response);
    }

    /**
     * 查询当日注册统计
     * @author : zwy
     * 2020-08-20 12:02
     * @since JDK1.8
     */
    @ApiOperation(
            value = "查询当日注册统计",
            httpMethod = "GET",
            notes = "查询当日注册统计"
    )
    @GetMapping({"queryRegistDayTotal"})
    public ResultParam queryRegistDayTotal(UserEntity param,HttpServletRequest request, HttpServletResponse response){
        return service.queryRegistDayTotal(param,request,response);
    }

    /**
     * 查询注册统计
     * @author : zwy
     * 2020-08-20 12:02
     * @since JDK1.8
     */
    @ApiOperation(
            value = "查询注册统计",
            httpMethod = "GET",
            notes = "查询注册统计"
    )
    @GetMapping({"queryRegistCount"})
    public ResultParam queryRegistCount(UserEntity param,HttpServletRequest request, HttpServletResponse response){
        return service.queryRegistCount(param,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/9 17:21
     * @ Description：根据帐号查询id集合
     * @since: JDk1.8
     */
    @ApiOperation(
            value = "根据帐号查询id集合",
            httpMethod = "GET",
            notes = "根据帐号查询id集合"
    )
    @GetMapping("queryByLoginName")
    public Map<String,Object> queryByLoginName(@RequestParam("login_name")String login_name ,HttpServletRequest request, HttpServletResponse response){
        return service.queryByLoginName(login_name,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/9 17:21
     * @ Description：设置用户
     * @since: JDk1.8
     */
    @ApiOperation(
            value = "设置用户",
            httpMethod = "POST",
            notes = "设置用户"
    )
    @PostMapping("setUserSign")
    public ResultParam setUserSign(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpServletResponse response){
        return service.setUserSign(map,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/10 13:45
     * @ Description：机构添加老师
     * @ since: JDk1.8
     */
    @ApiOperation(
            value = "机构添加老师",
            httpMethod = "POST",
            notes = "机构添加老师"
    )
    @PostMapping("insertMechanismMaster")
    public ResultParam insertMechanismMaster(@RequestBody UserEntity userEntity,HttpServletRequest request, HttpServletResponse response){
        ResultParam resultParam = service.insertMechanismMaster(userEntity, request, response);
        try {
        if (resultParam.getCode()==0){
            MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
            masterInfoEntity.setUser_id(userEntity.getUser_id());
            masterInfoEntity.setType("teach_paypal");
            masterInfoEntity.setPageSize(1);
            masterInfoEntity = masterInfoMapper.queryListPage(masterInfoEntity).get(0);
            masterInfoSearchRepository.save(masterInfoEntity);
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultParam;
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/22 9:44
     * @ Description：更新机构老师状态
     * @ since: JDk1.8
     */
    @ApiOperation(
            value = "更新机构老师状态",
            httpMethod = "POST",
            notes = "更新机构老师状态"
    )
    @PostMapping("updateMechanismMaster")
    public ResultParam updateMechanismMaster(@RequestBody UserEntity userEntity,HttpServletRequest request, HttpServletResponse response){
        ResultParam resultParam = service.updateMechanismMaster(userEntity, request, response);
        try {
            if (resultParam.getCode()==0){
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(userEntity.getUser_id());
                masterInfoEntity.setType("teach_paypal");
                masterInfoEntity.setPageSize(1);
                masterInfoEntity = masterInfoMapper.queryListPage(masterInfoEntity).get(0);
                masterInfoSearchRepository.save(masterInfoEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultParam;
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/22 9:45
     * @ Description：根据帐号查询用户
     * @ since: JDk1.8
     */
    @ApiOperation(
            value = "根据帐号查询用户",
            httpMethod = "POST",
            notes = "根据帐号查询用户"
    )
    @PostMapping("queryUserInfoByLoginName")
    public ResultParam queryUserInfoByLoginName(@RequestBody UserEntity userEntity,HttpServletRequest request, HttpServletResponse response){
        return service.queryUserInfoByLoginName(userEntity,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/22 9:45
     * @ Description：H5领取优惠券
     * @ since: JDk1.8
     */
    @ApiOperation(
            value = "H5领取优惠券",
            httpMethod = "POST",
            notes = "H5领取优惠券"
    )
    @PostMapping("insertH5GetCoupon")
    public ResultParam insertH5GetCoupon(@RequestBody UserEntity userEntity,HttpServletRequest request, HttpServletResponse response){
        return service.insertH5GetCoupon(userEntity,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/22 9:45
     * @ Description：查询md5
     * @ since: JDk1.8
     */
    @ApiOperation(
            value = "查询md5",
            httpMethod = "POST",
            notes = "查询md5"
    )
    @PostMapping("queryMd5")
    public ResultParam queryMd5(@RequestBody UserEntity userEntity,HttpServletRequest request, HttpServletResponse response){
        return service.queryMd5(userEntity,request,response);
    }

}