package com.blueeyescloud.baselib.utils.gson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用的时候在字段上面添加@Ignore
 * 但在需要new Gson().toJson()的时候，不能再用new Gson()方式，必须使用以下方式生成string串
 * //        GsonBuilder gsonBuilder = new GsonBuilder();
 * //        gsonBuilder.addSerializationExclusionStrategy(new IgnoreStrategy());
 * //        String string = gsonBuilder.create().toJson(userBean);
 * 且这个@Igore只会影响字段在序列化的时候。反序列化（从string里如果有这个被标示了ignore的字段，还是会把正确的值赋值到字段里
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ignore {
}