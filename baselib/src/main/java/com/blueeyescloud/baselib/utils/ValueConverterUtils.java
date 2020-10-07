package com.blueeyescloud.baselib.utils;

import android.text.TextUtils;

/**
 * 从string的数值转成对应的数值类型
 * Created by caill on 2016/12/17.
 */
public class ValueConverterUtils {

    public static long getLong(String value){
        return getLong(value, 0);
    }

    public static long getLong(String value, long defaultValue){
        return !TextUtils.isEmpty(value) ? Long.valueOf(value) : defaultValue;
    }

    public static int getInteger(String value){
        return getInteger(value, 0);
    }

    public static int getInteger(String value, int defaultValue){
        return !TextUtils.isEmpty(value) ? Integer.valueOf(value) : defaultValue;
    }

    public static double getDouble(String value){
        return getDouble(value, 0);
    }

    public static double getDouble(String value, double defaultValue){
        return !TextUtils.isEmpty(value) ? Double.valueOf(value) : defaultValue;
    }

    public static float getFloat(String value){
        return getFloat(value, 0);
    }

    public static float getFloat(String value, float defaultValue){
        return !TextUtils.isEmpty(value) ? Float.valueOf(value) : defaultValue;
    }

    public static boolean getBoolean(String value){
        return getBoolean(value, false);
    }

    public static boolean getBoolean(String value, boolean defaultValue){
        return !TextUtils.isEmpty(value) ? Boolean.valueOf(value) : defaultValue;
    }
}
