package com.huihe.eg.user.service.web;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.UserEntity;
import com.huihe.eg.user.mybatis.dao.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.UUID;

/**
 * @ Author     ：zwy
 * @ Date       ：2020/10/9 17:48
 * @ Description：扫码登录
 * @since: JDk1.8
 */
@Api(value = "扫码登录", description = "扫码登录", tags = {"扫码登录"})
@RestController
@RequestMapping("qrCodeLogin")
public class QRCodeLogin {
    @Resource
    private RedisService redisService;
    @Resource
    private UserMapper userMapper;

    /**
     * 前段获得二维码字符
     *
     * @author : zwy
     * 2020-08-04 08:34
     * @since JDK1.8
     */
    @ApiOperation(value = "获得二维码字符串")
    @GetMapping("getQrCode")
    public String getQrCode() {
        String uuid = null;
        try {
            uuid = UUID.randomUUID().toString();
            redisService.set(uuid, "", 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uuid;
    }

    /**
     * 绑定用户信息
     *
     * @author : zwy
     * 2020-08-04 08:34
     * @since JDK1.8
     */
    @ApiOperation(value = "绑定用户信息")
    @GetMapping("bindingUserToken")
    public ResultParam bindingUserToken(Long user_id, String token) {
        try {
            //未过期
            if (redisService.getStr(token) != null) {
                redisService.set(token, user_id.toString(), 60);
                return ResultUtil.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10035.getCode(), UserEum.user_10035.getDesc());
    }

    /**
     * 轮询回调接口
     *
     * @author : zwy
     * 2020-08-04 08:35
     * @since JDK1.8
     */
    @ApiOperation(value = "查询是否过期及扫码完成")
    @GetMapping("getBack")
    public ResultParam getBack(String token) {
        try {
            String str = redisService.getStr(token);
            //未过期
            if (str != null&&!"".equals(str)) {
                HashMap<String, Object> map = new HashMap<>();
                UserEntity userEntity = userMapper.selectByPrimaryKey(Long.valueOf(str));
                map.put("user",userEntity);
                map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(str+"userinfo")));
                return ResultUtil.success(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("".equals(redisService.getStr(token))){
            return ResultUtil.error(UserEum.user_10036.getCode(), UserEum.user_10036.getDesc());
        }
        return ResultUtil.error(UserEum.user_10035.getCode(), UserEum.user_10035.getDesc());
    }

}
