package com.blueeyescloud.ext.devicemaster.plan;

public class PlanConstants {
    /**
     * 定义了从哪些页面跳转过来
     */
    public static class SourceType{
        public static final int ONE_CLICK_NEW_PHONE = 0; //一键新机
        public static final int SELF_SELECTED_PHONE = 1; //自选机型
        public static final int MY_PLAN_LIST_ITEM = 2; //我的设备方案列表里的某个方案
        public static final int CHANGE_HISTORY_LIST_ITEM = 3; //换机历史列表里的某个方案
        public static final int HOME_DEVICE_INFO = 4; //首页设备信息按钮
        public static final int GENERATE_NEW_DEVICE_CLEAN = 5; //生成新设备转应用清理
        public static final int HOME_APP_CLEAN = 6; //首页的一键清理
    }

    public static class Param {
        public static final String SOURCE_TYPE = "sourceType"; //来源页面
        public static final String PLAN_INFO = "planInfo"; //设备方案实体类信息
        public static final String PHONE_MODEL = "phoneModel"; //手机型号
    }

    public static class PlanOperationResult{
        public static final int ADD_SUCCESS = 0;
        public static final int ADD_FAIL =  1;
        public static final int UPDATE_SUCCESS = 2;
        public static final int UPDATE_FAIL = 3;
        public static final int DELETE_SUCCESS = 4;
        public static final int DELETE_FAIL = 5;
    }

    public static class ActivityRequestCode{
        public static final int SHOW_PLAN_DETAIL = 1;
    }

    public static class ActivityResultCode{
        public static final int NEED_UPDATE = 1;
    }
}
