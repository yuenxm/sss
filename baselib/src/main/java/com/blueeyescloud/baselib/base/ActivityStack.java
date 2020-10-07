package com.blueeyescloud.baselib.base;

import android.app.Activity;

import java.util.Stack;

/**
 * Activity栈管理
 */
public class ActivityStack {
    private Stack<Activity> activityStack = new Stack<>();

    private ActivityStack(){

    }

    public static ActivityStack getInstance(){
        return Holder.sINSTANCE;
    }

    public static class Holder{
        private static final ActivityStack sINSTANCE = new ActivityStack();
    }

    /**
     * 添加指定的Activity到栈顶
     * @param activity
     */
    public void pushActivity(Activity activity){
        activityStack.add(activity);
    }

    /**
     * 移除指定的Activity
     * @param activity
     */
    public void popActivity(Activity activity){
        if(activity != null){
            activityStack.remove(activity);
        }
    }

    /**
     * 当前的activity
     * @return
     */
    public Activity currentActivity(){
        if(activityStack != null  && activityStack.size() > 0) {
            Activity activity = activityStack.lastElement();
            return activity;
        }
        return null;
    }

    public int size(){
        if (activityStack != null){
            return activityStack.size();
        }
        return 0;
    }

    public boolean isBottomActivity(Activity activity){
        //判断栈内第一个位置，或者第一个非finishing状态的窗体是否为指定的activity
        if(size() > 0){
            if(activityStack.get(0) == activity){
                return true;
            }else{
                for(int i = 0; i < size(); i++){
                    Activity item = activityStack.get(i);
                    if(item != null && !item.isFinishing()){
                        return item == activity;
                    }
                }
            }
            return false;
        }else{
            return true;
        }
    }

    public boolean hasActivityClass(String className) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (className.equals(activity.getClass().getName())) {
                return true;
            }
        }
        return false;
    }

    public void popAndFinishActivity(Activity activity) {
        if (activity != null) {
            if(!activity.isFinishing()) {
                activity.finish();
            }
            activityStack.remove(activity);
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void popAndFinishActivity(){
        if(activityStack.size() <= 0){
            return;
        }
        Activity activity = activityStack.lastElement();
        if(activity != null){
            if(!activity.isFinishing()) {
                activity.finish();
            }
            activityStack.remove(activity);
        }
    }

    /**
     * 弹出堆栈中的几个Activity
     */
    public void popAndFinishActivity(int deep){
        if(deep < activityStack.size()){
            while(deep-- > 0){
                popAndFinishActivity();
            }
        }
    }

    public void popAllActivityExceptOne(Class<?> cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popAndFinishActivity(activity);
        }
    }

    public void popActivitiesExcept(Class<?>[] cls) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            boolean needPop = true;
            for (Class c : cls) {
                if (activity.getClass().equals(c)) {
                    needPop = false;
                    break;
                }
            }
            if (needPop) {
                popAndFinishActivity(activity);
            }
        }
    }

    public void clearActivities() {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            popAndFinishActivity(activity);
        }
    }

    public void clearActivitiesExceptOne(Activity activity) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activityVar = activityStack.get(i);
            if(activityVar != activity) {
                popAndFinishActivity(activityVar);
            }
        }
    }

}
