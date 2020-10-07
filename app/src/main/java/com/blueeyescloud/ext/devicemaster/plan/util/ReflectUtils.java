package com.blueeyescloud.ext.devicemaster.plan.util;

import java.lang.reflect.Field;

public class ReflectUtils {

    public static<T> Object getFiledValue(String fieldName, T object, Class clz){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class clazz = null;
        try {
            clazz = loader.loadClass(clz.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
