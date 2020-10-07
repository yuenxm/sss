package com.blueeyescloud.baselib.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blueeyescloud.baselib.R;

/**
 * Created by caill on 2016/12/11.
 */
public class ToastUtils {
    private static String oldMsg;
    protected static Toast toast = null;

    public static void showToast(Context context, String s) {
        showToast(context, s, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String s, int duration) {
        if(toast != null && toast.getDuration() != duration) {
            toast.cancel();
            toast = null;
        }
        if (toast == null) {
            oldMsg = s;
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.layout_device_toast, null);
            toast = new Toast(context.getApplicationContext());
            TextView textView = view.findViewById(R.id.message);
            textView.setText(s);
            toast.setView(view);
            toast.show();
        } else {
            if (s != null && s.equals(oldMsg)) {
                toast.show();
            } else {
                oldMsg = s;
                TextView textView = toast.getView().findViewById(R.id.message);
                textView.setText(s);
                toast.show();
            }
        }
    }

    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }

    public static void cleanup() {
        if (toast != null) {
            toast = null;
            oldMsg = null;
        }
    }

    public static void cancel(){
        if(toast != null){
            toast.cancel();
        }
    }

    public static void show(Context context, int resId) {
        show(context, context.getString(resId));
    }

    public static void show(Context context, String s) {
        showToast(context, s);
    }

    private static void setToastBackgroud(Context context, Toast toast) {
        //   toast.getView().setBackgroundResource(R.drawable.bg_toast);
    }
}
