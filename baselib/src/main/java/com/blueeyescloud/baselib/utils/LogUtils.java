package com.blueeyescloud.baselib.utils;

import android.util.Log;

import androidx.databinding.library.BuildConfig;

public class LogUtils {
    private static boolean DEBUG = true;//BuildConfig.DEBUG;

    public static void v(String tag, String msg){
        if(DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg){
        if(DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if(DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if(DEBUG){
            Log.d(tag, msg);
        }
    }
}
