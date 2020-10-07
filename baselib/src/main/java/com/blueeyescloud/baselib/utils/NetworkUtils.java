package com.blueeyescloud.baselib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
    public static final int TYPE_NONE = -1;
    public static final int TYPE_MOBILE = ConnectivityManager.TYPE_MOBILE;
    public static final int TYPE_WIFI = ConnectivityManager.TYPE_WIFI;
    public static final int TYPE_LAN = TYPE_WIFI + 1;//有线连接

    /**
     * 获取网络类型，简单的区分wifi，移动数据网，有线连接，无网络络
     *
     * @return
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null) {
            if (!info.isAvailable() || !info.isConnected()) {
                return TYPE_NONE;
            } else {
                if (info.getType() == ConnectivityManager.TYPE_WIFI && info.isConnected()) {
                    return TYPE_WIFI;
                } else if (info.getType() == ConnectivityManager.TYPE_MOBILE && info.isConnected
                        ()) {
                    return TYPE_MOBILE;
                } else {
                    return TYPE_LAN;
                }
            }
        }
        return TYPE_NONE;
    }

    public static boolean isWifiState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static boolean isMobileState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    public static boolean isConnected(Context context) {
        if (context == null){
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null){
            return false;
        }
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable() && info.isConnected()) {
            return true;
        }
        return false;
    }
}
