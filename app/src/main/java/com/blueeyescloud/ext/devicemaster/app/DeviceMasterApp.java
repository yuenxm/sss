package com.blueeyescloud.ext.devicemaster.app;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class DeviceMasterApp {
    private static final String RESOURCE_ID_FILE = "/system/vendor/etc/res.conf";
    private static String sResourceId;

    public void onAppCreated(){
        //初始化Native调用
//        NativeHelper.getInstance().initLibMario(this);

        //获取resourceId
        getResourceId();//TODO 看下执行的时间，读写文件，是否需要异步去执行
    }


    //TODO
    public static String getResourceId(){
        if(sResourceId == null) {
            Yaml yaml = new Yaml();
            File file = new File(RESOURCE_ID_FILE);
            try {
                Map<String, Object> map = yaml.load(new FileInputStream(file));
                sResourceId = (String)map.get("resource_id");
                return sResourceId;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "";
            }
        }else{
            return sResourceId;
        }
    }
}
