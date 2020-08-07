package com.xxty.utils.utils;

import java.lang.reflect.Field;

/**
 * @Author: llun
 * @DateTime: 2020/8/7 11:26
 * @Description: TODO
 */
public class BeanCheck {
    /**
     * 判断对象中属性值是否全为空
     *
     * @param object
     * @return
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        boolean flag = true;
        //遍历属性
        try {
            for (Field  f : fields) {
                // 设置属性是可以访问的(私有的也可以)
                f.setAccessible(true);
                // 得到此属性的值
                Object val=f.get(object);
                //只要有1个属性不为空,那么就不是所有的属性值都为空
                if(val!=null) {
                    flag = false;
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
