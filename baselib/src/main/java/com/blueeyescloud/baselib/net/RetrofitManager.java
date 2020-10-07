package com.blueeyescloud.baselib.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitManager {
    private static final int TIME_OUT = 5;

    private Retrofit mRetrofit;

    private RetrofitManager(){
        OkHttpClient client = newOkHttpClient();
        mRetrofit = newRetrofitInstance(client);
    }

    public static RetrofitManager getInstance(){
        return SingletonHolder.sINSTANCE;
//        if(sINSTANCE == null){
//            sINSTANCE = new RetrofitManager();
//        }
//        return sINSTANCE;
    }

//    private static RetrofitManager sINSTANCE;

    public static class SingletonHolder{
        private static RetrofitManager sINSTANCE = new RetrofitManager();
    }

    private OkHttpClient newOkHttpClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        return okHttpClient;
    }

    public static OkHttpClient newOkHttpsClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();

        return okHttpClient;
    }

    private Retrofit newRetrofitInstance(OkHttpClient client){
        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl("http://rap2api.taobao.org/")
//                    .baseUrl("http://192.168.1.102:8999/athena/anon/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        }
        return mRetrofit;
    }

    public <T> T create(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }
}
