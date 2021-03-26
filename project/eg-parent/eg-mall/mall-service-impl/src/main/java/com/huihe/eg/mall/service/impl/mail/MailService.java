package com.huihe.eg.mall.service.impl.mail;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.DataException;
import com.cy.framework.util.CommonUtils;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.mall.model.MailParam;
import com.huihe.eg.mall.model.UserLoginEntity;
import com.huihe.eg.mall.mybatis.dao.UserLoginMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/1/18 15:04
 */
@Service
public class MailService {
    @Resource
    private MailParam param;
    @Resource
    private UserLoginMapper userMapper;
    @Resource
    private RedisService redisService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ResultParam sendMail(String mail,String type){
        if (StringUtil.isEmpty(mail)) {
            throw new DataException(UserEum.user_10019.getCode(), UserEum.user_10019.getDesc());
        }
        if(type!=null&&type.equalsIgnoreCase("register")){
            UserLoginEntity entity=new UserLoginEntity();
            entity.setLogin_name(mail);
            entity.setPageSize(1);
            List<UserLoginEntity> userEntities=userMapper.query(entity);
            if(userEntities!=null&&userEntities.size()>0){
                return ResultUtil.error(UserEum.user_10016.getCode(), UserEum.user_10016.getDesc());
            }
        }
        if (!isValidEmail(mail)) {
            throw new DataException(UserEum.user_10017.getCode(), UserEum.user_10017.getDesc());
        }
        String code = redisService.getStr("mail:" + mail);
        if (StringUtil.isNotEmpty(code)) {
            throw new DataException(UserEum.user_10009.getCode(), UserEum.user_10009.getDesc());
        }
        code = String.valueOf(CommonUtils.getRandNum(1, 9999));
        String content = param.getContent().replace("{code}", code);
        logger.info("response after request result is :"+content);
        MailSendRequest sendRequest = new MailSendRequest(mail,param.getTitle(),content,param.getHost(),param.getAccount(),param.getPassword(),param.getPersonal());
        sendRequest.run();
        redisService.set("mail:" + mail, code, param.getSeconds());
        return ResultUtil.success();
    }

    /**
     * 验证
     *
     * @param mobile
     * @param vali_code
     */
    public ResultParam validate(String mobile, String vali_code) {
        String code = redisService.getStr("mail:" + mobile);
        if (StringUtil.isEmpty(code)) {
            return ResultUtil.error(UserEum.user_10010.getCode(), UserEum.user_10010.getDesc());
        }
        if (!code.equals(vali_code)) {
            return ResultUtil.error(UserEum.user_10011.getCode(), UserEum.user_10011.getDesc());
        }
        return ResultUtil.success(true);
    }


    /**
     * 验证邮箱号码是否正确
     * @param email
     * @return
     */
    public  boolean isValidEmail(String email) {
        String reg = "\\w+@(\\w+\\.){1,3}\\w+";
        Pattern pattern = Pattern.compile(reg);
        boolean flag = false;
        if (email != null) {
            Matcher matcher = pattern.matcher(email);
            flag = matcher.matches();
        }
        return flag;
    }
}
