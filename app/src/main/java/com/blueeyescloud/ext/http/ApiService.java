package com.blueeyescloud.ext.http;


import com.blueeyescloud.ext.devicemaster.entity.AttributeEntity;
import com.blueeyescloud.ext.devicemaster.entity.MasterPlanEntity;
import com.blueeyescloud.ext.devicemaster.entity.PageResEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    String BASE_URL = "http://rap2api.taobao.org/";//"https://news-at.zhihu.com/api/4/";

    //设备方案增加
    @FormUrlEncoded
    @POST("master/plan")
    Observable<String> addDevicePlan(@Field("resourceId") String resourceId,
                                     @Field("name") String name,
                                     @Field("attributeJson") AttributeEntity attributeJson);

    //设备方案删除
    @DELETE("master/plan/{planId}")
    Observable<String> deleteDevicePlan(@Path("planId") String planId);

    //设备方案修改
    @FormUrlEncoded
    @PUT("master/plan/{planId}")
    Observable<String> updateDevicePlan(@Path("planId") String planId,
                                                          @Field("attributeJson") AttributeEntity attributeJson);

    //设备方案查询
    @GET("master/plan")
    Observable<PageResEntity> queryDevicePlans(@Query("resourceId") String resourceId,
                                               @Query("pageNo") int pageNo,
                                               @Query("pageSize") int pageSize);

    //改机信息
    @GET("master/changeInfo")
    Observable<AttributeEntity> queryDeviceChangeInfo(@Query("resourceId") String resourceId,
                                                      @Query("deviceModel") String deviceModel);

    //改机信息启用
    @FormUrlEncoded
    @POST("master/changeInfo")
    Observable<List<String>> enablePlanInfo(@Field("resourceId") String resourceId,
                                                 @Field("attributeJson") AttributeEntity attributeJson);

    //改机历史增加
    @FormUrlEncoded
    @POST("master/history")
    Observable<String> addChangeInfoHistory(@Field("resourceId") String resourceId,
                                                       @Field("attributeJson") AttributeEntity attributeJson);

//    //改机历史查询
//    @GET("master/history")
//    Observable<PageResEntity<List<ChangeHistory>>> queryChangeInfoHistory(@Query("resourceId") String resourceId,
//                                                                                           @Query("pageNo") int pageNo,
//                                                                                           @Query("pageSize") int pageSize);

    //改机历史删除
    @FormUrlEncoded
    @POST("master/history/delete")
    Observable<String> deleteChangeInfoHistory(@Field("historyIds") String[] historyIds);

    //手机型号获取
    @GET("master/deviceModel")
    Observable<Map<String, List<String>>> queryDeviceModels();

    //获取当前手机的扩展服务接口
    @GET("expand")
    Observable<Map<String,Boolean>> queryExpandEnabledInfo(@Query("resourceId") String resourceId);

    /////////////////////////////////////   MOCK ////////////////////////////////////////

    //获取当前手机的扩展服务接口
    @GET("/app/mock/267348/expand")
    Observable<Map<String,Boolean>> queryExpandEnabledInfo2(@Query("resourceId") String resourceId);

    //改机信息
    @GET("/app/mock/267348/master/changeInfo")
    Observable<AttributeEntity> queryDeviceChangeInfo2(@Query("resourceId") String resourceId,
                                                       @Query("deviceModel") String deviceModel);

    //手机型号获取
    @GET("/app/mock/267348/master/deviceModel")
    Observable<Map<String, List<String>>> queryDeviceModels2();

    //设备方案查询
    @GET("/app/mock/267348/master/plan")
    Observable<PageResEntity<MasterPlanEntity>> queryDevicePlans2(@Query("resourceId") String resourceId,
                                                                  @Query("pageNo") int pageNo,
                                                                  @Query("pageSize") int pageSize);

    //设备方案删除
    @DELETE("/app/mock/267348/master/plan/{planId}")
    Observable<String> deleteDevicePlan2(@Path("planId") String planId);

}
