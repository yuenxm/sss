package com.blueeyescloud.ext.devicemaster.clean;

public class CleanConstants {

    /**
     * 步骤类型
     */
    public static class StepStatusType{
        public static final int NONE = -1;
        public static final int CLEAN_APPS_START = 0;//开始清理
        public static final int CLEAN_APPS_SUCCESS = 1; //清理成功
        public static final int CLEAN_APPS_FAIL = 2; //清理失败
//        public static final int ENABLE_NEW_DEVICE_START = 3; //改机设置开始
        public static final int ENABLE_NEW_DEVICE_SUCCESS = 4; //改机成功
        public static final int ENABLE_NEW_DEVICE_FAIL = 5; //改机失败
//        public static final int ACTION_DONE = 6;//相关动作都做完
    }

    public static class CleanStepDialogStyle{
        public static final int TYPE_LOADING = 0;
        public static final int TYPE_COUNTDOWN = 1;
        public static final int TYPE_TIP = 2;
    }
}
