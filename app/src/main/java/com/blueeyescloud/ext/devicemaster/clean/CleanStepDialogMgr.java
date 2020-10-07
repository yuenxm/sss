package com.blueeyescloud.ext.devicemaster.clean;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.blueeyescloud.ext.devicemaster.home.HomeActivity;
import com.blueeyescloud.ext.devicemaster.utils.AppUtils;


public class CleanStepDialogMgr {
    private final static int MSG_COUNTDOWN_START = 0;
    private final static int MSG_COUNTDOWN_END = 1;
    private final static int DEFAULT_COUNTDOWN_NUM = 3;

    private Context mContext;
    private boolean mNeedReboot;
    private CleanStepDialog mCleanStepDialog;
    private int mCountNum = DEFAULT_COUNTDOWN_NUM; //倒计时

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == MSG_COUNTDOWN_START){
                mCountNum--;
                mCleanStepDialog.setTitle(String.valueOf(mCountNum));
                if(mCountNum == 0){
                    mHandler.sendEmptyMessageDelayed(MSG_COUNTDOWN_END, 0);
                }else {
                    mHandler.sendEmptyMessageDelayed(MSG_COUNTDOWN_START, 1000);
                }
            }else if(msg.what == MSG_COUNTDOWN_END) {
                if (mNeedReboot) {
                    reboot(mContext);
                } else {
                    backToHomePage();
                }
                dismissDialog();
            }
        }
    };

    /**
     * 回到设备大师首页
     */
    private void backToHomePage(){
        //回到设备大师首页
        Intent intent = new Intent(mContext, HomeActivity.class);
        mContext.startActivity(intent);
    }

    /**
     * 重启手机
     */
    private void reboot(Context context) {
        AppUtils.reboot(context);
    }

    public CleanStepDialogMgr(Context context){
        this.mContext = context;
        this.mCleanStepDialog = new CleanStepDialog(context);
    }

    /**
     * 根据类型显示dialog内容
     * @param type
     */
    public void showDialog(int type){
        if(type == CleanConstants.CleanStepDialogStyle.TYPE_LOADING){
            CleanStepDialog.DialogParams params = new CleanStepDialog.DialogParams();
            params.showLoading = true;
            params.title = "";
            params.content = "标准路径清理...";
            mCleanStepDialog.setDialogParams(params);
            mCleanStepDialog.show();
        }else if(type == CleanConstants.CleanStepDialogStyle.TYPE_COUNTDOWN){
            CleanStepDialog.DialogParams params = new CleanStepDialog.DialogParams();
            params.showLoading = false;
            params.title = "" + mCountNum;
            if(mNeedReboot) {
                params.content = "设置完成，即将重启";
            }else{
                params.content =  "设置完成，即将跳转";
            }
            mCleanStepDialog.setDialogParams(params);
            mCleanStepDialog.show();
            mHandler.sendEmptyMessageDelayed(MSG_COUNTDOWN_START, 1000);
        }else {
            CleanStepDialog.DialogParams params = new CleanStepDialog.DialogParams();
            params.showLoading = true;
            params.title = "";
            params.content = "设置中...";
            mCleanStepDialog.setDialogParams(params);
            mCleanStepDialog.show();
        }
    }

    public void setNeedReboot(boolean needReboot){
        mNeedReboot = needReboot;
    }

    public void dismissDialog(){
        mCleanStepDialog.dismiss();
    }
}
