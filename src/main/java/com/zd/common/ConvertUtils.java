package com.zd.common;

import java.beans.BeanInfo;
import java.util.Map;
import java.util.*;
import java.beans.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;

/**
 * Created by zhangxiusheng on 17/3/31.
 */
public class ConvertUtils {

    /**
     * 使用reflect进行转换 ================================================================
     */
    public static Object mapToObjectByreflect( Map<String, Object> map, Class<?> beanClass ) throws Exception {
        if (map == null) return null;

        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    /**
     */
    public static Map<String, Object> objectToMapByreflect( Object obj ) throws Exception {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }

        return map;
    }


    /**
     * 使用Introspector进行转换================================================================
     */
    public static Object mapToObject( Map<String, Object> map, Class<?> beanClass ) throws Exception {
        if (map == null) return null;

        Object obj = beanClass.newInstance();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
                setter.invoke(obj, map.get(property.getName()));
            }
        }

        return obj;
    }

    /**
     */
    public static Map<String, Object> objectToMap( Object obj ) throws Exception {
        if (obj == null) return null;

        Map<String, Object> map = new HashMap<String, Object>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            map.put(key, value);
        }
        return map;
    }

    //----==================================================================
    //----==================================================================
    //----下面两种方法不靠谱,测试下来不管用
    //----==================================================================
    //----==================================================================
    public static Object mapToObject_01( Map<String, Object> map, Class<?> beanClass ) throws Exception {
        if (map == null) return null;
        Object obj = beanClass.newInstance();
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        return obj;
    }

    public static Map<?, ?> objectToMap_01( Object obj ) {
        if (obj == null) return null;
        return new org.apache.commons.beanutils.BeanMap(obj);
    }

}
