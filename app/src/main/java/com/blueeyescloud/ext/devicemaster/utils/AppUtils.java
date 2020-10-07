package com.blueeyescloud.ext.devicemaster.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.Method;

public class AppUtils {
    private static final String TAG = "AppUtils";

    public static void restartApp(Activity activity){
        //回到应用首页
        final Intent intent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public static void reboot1(){
        try {
            //获得ServiceManager类
            Class<?> ServiceManager = Class.forName("android.os.ServiceManager");
            //获得ServiceManager的getService方法
            Method getService = ServiceManager.getMethod("getService", java.lang.String.class);
            //调用getService获取RemoteService
            Object oRemoteService = getService.invoke(null, Context.POWER_SERVICE);
            //获得IPowerManager.Stub类
            Class<?> cStub = Class.forName("android.os.IPowerManager$Stub");
            //获得asInterface方法
            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
            //调用asInterface方法获取IPowerManager对象
            Object oIPowerManager = asInterface.invoke(null, oRemoteService);
            //获得shutdown()方法
            Method shutdown = oIPowerManager.getClass().getMethod("shutdown",boolean.class,boolean.class);
            //调用shutdown()方法
            shutdown.invoke(oIPowerManager,false,true);
        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
        }
    }

    public static void reboot(Context context){
        Intent intent2 = new Intent(Intent.ACTION_REBOOT);
        intent2.putExtra("nowait", 1);
        intent2.putExtra("interval", 1);
        intent2.putExtra("window", 0);
        context.sendBroadcast(intent2);
    }

//    public static void shutdown(){
//        Intent intent = new Intent(Intent.ACTION_REQUEST_SHUTDOWN);
//        intent.putExtra(Intent.EXTRA_KEY_CONFIRM, false);
//        //其中false换成true,会弹出是否关机的确认窗口
//        startActivity(intent);
//    }
}
