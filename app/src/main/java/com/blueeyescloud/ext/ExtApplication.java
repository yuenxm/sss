package com.blueeyescloud.ext;

import com.blueeyescloud.baselib.base.BaseApplication;
import com.blueeyescloud.ext.devicemaster.app.DeviceMasterApp;
import com.blueeyescloud.ext.devicemaster.jni.NativeHelper;

public class ExtApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        DeviceMasterApp deviceMasterApp = new DeviceMasterApp();
        deviceMasterApp.onAppCreated();
    }
}
