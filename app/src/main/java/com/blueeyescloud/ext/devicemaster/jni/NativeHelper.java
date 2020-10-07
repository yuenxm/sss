package com.blueeyescloud.ext.devicemaster.jni;

import android.content.Context;
import android.util.Log;

import com.blueeyescloud.baselib.utils.SystemUtils;

public class NativeHelper {
    public static class SingletonHolder{
        private static NativeHelper sINSTANCE = new NativeHelper();
    }

    private NativeHelper(){

    }

    public static NativeHelper getInstance(){
        return SingletonHolder.sINSTANCE;
    }

//    public void initLibMario(Context context){
//        int platform = 1;
//        String innerCachePath = context.getApplicationContext().getCacheDir().getAbsolutePath();
//        int res =  marioInit(platform, innerCachePath);
//        if(res == 0) {
//            Log.d("tag_ss", "marioInit success");
//        }else {
//            Log.d("tag_ss", "marioInit failed " + res);
//        }
//    }

//    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }
//
//    /**
//     * A native method that is implemented by the 'native-lib' native library,
//     * which is packaged with this application.
//     */
//    public native String stringFromJNI();
//    public native int marioInit(int platorm, String cacheDir);
//    public native int setAttrsBurst(String attrsJson);
//    public native int setGps(double longitude, double latitude);
//    public native String getAttr(String attr);
//    public native int checkAttr(String attr, String value);
//    public native int clearApp(String app);
//
//    public static void callJava(Context context){
//        Log.i("tag_ss", "-----------call Java -------");
//        Log.i("tag_ss", "wifiname = " + SystemUtils.getWifiName(context));
//        Log.i("tag_ss", "wifif mac = " + SystemUtils.getWifiMac());
//        Log.i("tag_ss", "version = " + SystemUtils.getSystemVersion());
//        Log.i("tag_ss", "imei = " + SystemUtils.getIMEI());
//        Log.i("tag_ss", "imsi = " + SystemUtils.getIMSI(context));
//        Log.i("tag_ss", "phoneNumber = " + SystemUtils.getPhoneNumber(context));
//    }
//
//    public static void callNative(){
//        Log.e("tag_ss", "-----------call Native -------");
//        Log.e("tag_ss", "wifiname = " + NativeHelper.getInstance().getAttr("wifiname"));
//        Log.e("tag_ss", "wifif mac = " + NativeHelper.getInstance().getAttr("wifimac"));
//        Log.e("tag_ss", "version = " + NativeHelper.getInstance().getAttr("wifiname"));
//        Log.e("tag_ss", "imei = " + NativeHelper.getInstance().getAttr("imei"));
//        Log.e("tag_ss", "imsi = " + NativeHelper.getInstance().getAttr("imsi"));
//        Log.e("tag_ss", "phoneNumber = " + NativeHelper.getInstance().getAttr("phonenum"));
//    }

}
