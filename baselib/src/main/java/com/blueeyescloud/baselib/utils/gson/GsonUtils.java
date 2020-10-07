package com.blueeyescloud.baselib.utils.gson;

import com.google.gson.GsonBuilder;

public class GsonUtils {

    /**
     * entity类里带有@Ignore标示的字段，不会被序列化在json string里
     * @param entity
     * @param <T>
     */
    public static<T> String toJsonExcludeIgnoreField(T entity){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.addSerializationExclusionStrategy(new IgnoreStrategy());
        return gsonBuilder.create().toJson(entity);
    }
}
