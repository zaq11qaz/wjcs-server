package com.huihe.eg.comm.util;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.lang.reflect.Field;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/4/12 14:09
 * @ Description：
 * @ since: JDk1.8
 */
public class MapTools {


    /**
     *  对传入的对象进行数据清洗，将属性值为null和""的去掉，其他字段名和属性值存入map集合
     */
    public static Map<String,String> objectToStringMap(Object requestParameters) throws IllegalAccessException {

        Map<String, String> map = new HashMap<>();
        // 获取f对象对应类中的所有属性域
        Field[] fields = requestParameters.getClass().getDeclaredFields();
        for (Field field : fields) {
            String varName = field.getName();
            // 获取原来的访问控制权限
            boolean accessFlag = field.isAccessible();
            // 修改访问控制权限
            field.setAccessible(true);
            // 获取在对象f中属性fields[i]对应的对象中的变量
            Object o = field.get(requestParameters);
            if (o != null && StringUtils.isNotBlank(o.toString().trim())) {
                map.put(varName, o.toString().trim());
                // 恢复访问控制权限
                field.setAccessible(accessFlag);
            }
        }
        return map;
    }

}
