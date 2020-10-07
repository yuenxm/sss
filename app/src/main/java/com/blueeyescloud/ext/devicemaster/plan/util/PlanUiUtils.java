package com.blueeyescloud.ext.devicemaster.plan.util;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.blueeyescloud.ext.R;
import com.blueeyescloud.ext.devicemaster.plan.PlanConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import retrofit2.http.PUT;

public class PlanUiUtils {
    /**
     * 根据来源类型，返回对应的页面的标题栏名称
     * @param sourceType
     * @return
     */
    public static String getTitlebarName(Context context, int sourceType){
        if(context == null){
            return "";
        }
        switch (sourceType){
            case PlanConstants.SourceType.ONE_CLICK_NEW_PHONE:
            case PlanConstants.SourceType.SELF_SELECTED_PHONE:
            case PlanConstants.SourceType.CHANGE_HISTORY_LIST_ITEM:
                return context.getString(R.string.device_plan_title_param_setting);
            case PlanConstants.SourceType.MY_PLAN_LIST_ITEM:
                return context.getString(R.string.device_plan_title_manage_plan);
            case PlanConstants.SourceType.HOME_DEVICE_INFO:
                return context.getString(R.string.device_plan_title_plan_detail);
            default:
                return "";
        }
    }


    public static Set<String> UnEditableAttrsSet; //初始化记录哪些属性是会有不能修改的场景
    public static final String MANUFACTURER = "manufacturer";
    public static final String BRAND = "brand";
    public static final String MODEL = "model";
    public static final String DEFAULT = "";

    static{
        UnEditableAttrsSet = new HashSet<>();
        UnEditableAttrsSet.add(MANUFACTURER);
        UnEditableAttrsSet.add(BRAND);
        UnEditableAttrsSet.add(MODEL);
    }
    public static boolean getGeneralEditable(int sourceType, String attr){
        switch (sourceType){
            case PlanConstants.SourceType.ONE_CLICK_NEW_PHONE:
                return true;
            case PlanConstants.SourceType.SELF_SELECTED_PHONE:
                return UnEditableAttrsSet.contains(attr) ? false : true;
            case PlanConstants.SourceType.CHANGE_HISTORY_LIST_ITEM:
                return true;
            case PlanConstants.SourceType.MY_PLAN_LIST_ITEM:
                return UnEditableAttrsSet.contains(attr) ? false : true;
            case PlanConstants.SourceType.HOME_DEVICE_INFO:
                return false;
        }
        return false;
    }

    /**
     * 根据页面来源，显示按钮名称
     * @param context
     * @param sourceType
     * @return
     */
    public static String getPlanActionButtonText(Context context, int sourceType){
        if(context == null){
            return "";
        }
        switch (sourceType){
            case PlanConstants.SourceType.ONE_CLICK_NEW_PHONE:
            case PlanConstants.SourceType.SELF_SELECTED_PHONE:
            case PlanConstants.SourceType.CHANGE_HISTORY_LIST_ITEM:
                return context.getString(R.string.device_plan_btn_add_new_plan);
            case PlanConstants.SourceType.MY_PLAN_LIST_ITEM:
                return context.getString(R.string.device_plan_btn_save_plan);
            default:
                return "";
        }
    }

    /**
     * 根据页面来源，决定是否显示底部按钮布局
     * @param sourceType
     * @return
     */
    public static int getPlanButtonLayoutVisibility(int sourceType){
        return (sourceType == PlanConstants.SourceType.HOME_DEVICE_INFO) ? View.GONE : View.VISIBLE;
    }

    /**
     * 根据页面来源，决定是否显示方案名称
     * @param sourceType
     * @return
     */
    public static int getPlanNameLayoutVisibility(int sourceType){
        return (sourceType == PlanConstants.SourceType.MY_PLAN_LIST_ITEM) ? View.VISIBLE : View.GONE;
    }

    /**
     * 根据页面来源，决定是删除按钮显示与否
     * @param sourceType
     * @return
     */
    public static int getDeleteButtonVisibility(int sourceType){
        return (sourceType == PlanConstants.SourceType.MY_PLAN_LIST_ITEM) ? View.VISIBLE : View.GONE;
    }

    public static boolean isAddPlan(int sourceType){
        if(sourceType == PlanConstants.SourceType.MY_PLAN_LIST_ITEM){
            return false;
        }
        return true;
    }

    public static String getRandomPlanName(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        String time = sdf.format(new Date());
        return "方案" + time;
    }


    /**
     * 根据页面来源，决定是否显示跳过清理
     * @param sourceType
     * @return
     */
    public static int getCleanSkipLayoutVisibility(int sourceType){
        return (sourceType == PlanConstants.SourceType.GENERATE_NEW_DEVICE_CLEAN) ? View.VISIBLE : View.INVISIBLE;
    }
}
