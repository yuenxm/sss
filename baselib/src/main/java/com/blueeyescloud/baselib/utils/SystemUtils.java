package com.blueeyescloud.baselib.utils;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * 系统工具类
 */
public class SystemUtils {

    /**
     * 获取厂商名
     * @return
     */
    public static String getManufacture() {
        return Build.MANUFACTURER;
    }

    /**
     * 获得品牌名
     * @return
     */
    public static String getBrand() {
        return Build.BRAND;
    }

    /**
     * 返回型号
     * @return
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 获取序列号
     * @return
     */
    public static String getSerialNo(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) { //Android 8.0以下
            return Build.SERIAL;
        } else {
            //Android 8.0-Android 10(不包括）：需要申请READ_PHONE_STATE权限，如果用户拒绝了权限，
            // 会抛出java.lang.SecurityException异常。
            // Android 10及以上，需要申请READ_PRIVILEGED_PHONE_STATE权限：分为以下两种情况
            //targetSdkVersion<29：没有申请权限的情况，调用Build.getSerial()方法时抛出java.lang.SecurityException异常；申请了权限，通过Build.getSerial()方法获取到的设备序列号为“unknown”
            //targetSdkVersion=29：无论是否申请了权限，调用Build.getSerial()方法时都会直接抛出java.lang.SecurityException异常
            String serialNo = null;
            try {
                serialNo = Build.getSerial();
            } catch (Exception ex) {

            }
            if (serialNo == null || Build.UNKNOWN.equals(serialNo)) {
                //TODO 调用so库里到方法
                return "TODO";
            } else {
                return serialNo;
            }
        }
    }

    /**
     * 获取当前手机系统版本号
     * @return
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getPhoneNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "TODO";
        }
        String number = tm.getLine1Number();
        return number;
    }

    public static String getAndroidId(Context context){
        return Settings.Secure.getString(context.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static String getIMSI(Context context){
        TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Service.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return tm.getDeviceId();
        }else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
            return tm.getImei();
        } else {
            //android 10以上调用so方法
            return "TODO";
        }
    }

    public static String getIMEI(){
        return "";
    }

    public static String getICCID(){
        return "";
    }

    /**
     * 获取wifi名称，即SSID
     * @param context
     * @return
     */
    public static String getWifiName(Context context){
        WifiManager wifiMgr = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int wifiState = wifiMgr.getWifiState();
        WifiInfo info = wifiMgr.getConnectionInfo();
        String wifiId = info != null ? info.getSSID() : null;
        return wifiId;
    }

    public static String getWifiMac(){
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) {
                    continue;
                }
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
