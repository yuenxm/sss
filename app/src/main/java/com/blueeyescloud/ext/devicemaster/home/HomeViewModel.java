package com.blueeyescloud.ext.devicemaster.home;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.blueeyescloud.baselib.mvvm.BaseViewModel;
import com.blueeyescloud.baselib.utils.SystemUtils;

public class HomeViewModel extends BaseViewModel {

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public DeviceInfoBrief getDeviceInfoBrief(Context context){
        DeviceInfoBrief deviceInfoBrief = new DeviceInfoBrief();
        deviceInfoBrief.setBrand(SystemUtils.getBrand());
        deviceInfoBrief.setModel(SystemUtils.getModel());
        String serial = SystemUtils.getSerialNo(context);
        //TODO NEEDTO CHECK，要恢复下面调用
//        if("UNKNOWN".equals(serial) || TextUtils.isEmpty(serial)){
//            serial = NativeHelper.getInstance().getAttr("serial");
//        }
        deviceInfoBrief.setSerialNo(SystemUtils.getSerialNo(context));//TODO NEEDTO CHECK
        return deviceInfoBrief;
    }
}
