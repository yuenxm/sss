package com.blueeyescloud.baselib.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class ScreenUtils {

    // 获取屏幕密度
    static public float getDensity(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）

        return density;
    }

    // dip->px
    public static int dip2px(Context context, float dipValue) {
        int dpi = context.getResources().getDisplayMetrics().densityDpi;
        if(dpi == DisplayMetrics.DENSITY_TV){
            dipValue = dipValue / 1.33f;
        } else if(context.getResources().getDisplayMetrics().widthPixels == 1920
                && context.getResources().getDisplayMetrics().heightPixels == 1080
                && dpi == 320){
            dipValue = dipValue  / 1.33f;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    // px->dip
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // 获取屏幕高度
    public static int getScreenHeight(Context context) {
        int height = 0;
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(dm);
            height = dm.heightPixels;
        } else {
            try {
                Class c = Class.forName("android.view.Display");
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, dm);
                height = dm.heightPixels;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return height;
    }

    // 获取屏幕宽度
    public static int getScreenWidth(Context context) {
        int width = 0;
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(dm);
            width = dm.widthPixels;
        } else {
            try {
                Class c = Class.forName("android.view.Display");
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, dm);
                width = dm.widthPixels;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return width;
    }

    public  static void showDisplayInfo(Activity activity){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        // 屏幕宽度（像素）
        int width = metric.widthPixels;
        // 屏幕高度（像素）
        int height = metric.heightPixels;
        // 屏幕密度（1.0 / 1.5 / 2.0）
        float density = metric.density;
        // 屏幕密度DPI（160 / 240 / 320）
        int densityDpi = metric.densityDpi;
        String info = "机顶盒型号: " + android.os.Build.MODEL + ",\nSDK版本:"
                + android.os.Build.VERSION.SDK + ",\n系统版本:"
                + android.os.Build.VERSION.RELEASE  + "\n屏幕宽度（像素）: "
                + width + "\n屏幕高度（像素）: " + height
                + "\n屏幕密度:  "
                + density
                + "\n屏幕密度DPI: " + densityDpi;
        Log.i("tag_ss_system", "System INFO = " + info);
    }
}
