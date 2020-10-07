package com.blueeyescloud.ext.http;

import com.blueeyescloud.baselib.net.GsonConverterFactory;
import com.blueeyescloud.baselib.net.RetrofitManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class Api{
    private static final int TIME_OUT = 5;
    private Retrofit mRetrofit;

    private Api(){
        mRetrofit = newRetrofitInstance();
    }

    private Retrofit newRetrofitInstance(){
        if(mRetrofit == null){
            Retrofit.Builder builder = new Retrofit.Builder()
                    .client(newOkHttpInstance())
                    .baseUrl(ApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            return builder.build();
        }
        return mRetrofit;
    }

    private OkHttpClient newOkHttpInstance(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        return okHttpClient;
    }
}
