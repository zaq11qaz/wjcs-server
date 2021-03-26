package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.TimeZoneEntity;
import com.huihe.eg.user.mybatis.dao.TimeZoneMapper;
import com.huihe.eg.user.service.dao.TimeZoneService;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
public class TimeZoneServiceImpl extends BaseFrameworkServiceImpl<TimeZoneEntity, Long> implements TimeZoneService {

    @Resource
    private TimeZoneMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
    /**
     * 时区转换
     * @param time 时间字符串
     * @param pattern 格式 "yyyy-MM-dd HH:mm"
     * @param nowTimeZone eg:+8，0，+9，-1 等等
     * @param targetTimeZone 同nowTimeZone
     * @return
     */
    public static String timeZoneTransfer(String time, String pattern, String nowTimeZone, String targetTimeZone) {
        if(StringUtils.isBlank(time)){
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT" + nowTimeZone));
        Date date;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {

            return "";
        }
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT" + targetTimeZone));
        return simpleDateFormat.format(date);
    }
    /*public static void main(String[] args) {
        testTimeZoneTransfer();
    }*/
    public static  void testTimeZoneTransfer(){
        String result = timeZoneTransfer("2018-07-03 15:43", "yyyy-MM-dd HH:mm", "+8", "0");
        System.out.println(result);

        result = timeZoneTransfer("2018-07-03 15:43", "yyyy-MM-dd HH:mm", "+9", "0");
        System.out.println(result);

        result = timeZoneTransfer("2018-07-03 15:43", "yyyy-MM-dd HH:mm", "-1", "0");
        System.out.println(result);

        result = timeZoneTransfer("2018-07-03 15:43", "yyyy-MM-dd HH:mm", "+8", "+9");
        System.out.println(result);

        result = timeZoneTransfer("2018-07-03 15:43", "yyyy-MM-dd HH:mm", "+0", "+8");
        System.out.println(result);

        result =timeZoneTransfer("2018-07-03 15:43", "yyyy-MM-dd HH:mm", "+0", "0");
        System.out.println(result);
    }
}