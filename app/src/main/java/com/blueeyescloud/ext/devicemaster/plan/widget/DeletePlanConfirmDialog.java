package com.blueeyescloud.ext.devicemaster.plan.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.blueeyescloud.ext.R;
import com.blueeyescloud.ext.view.NoMultiClickListener;

public class DeletePlanConfirmDialog extends Dialog {
    private OnClickListener mOnClickListener;

    public DeletePlanConfirmDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete_plan_confirm);

        findViewById(R.id.btn_cancel).setOnClickListener(new NoMultiClickListener(){
            @Override
            public void onNewClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.btn_confirm).setOnClickListener(new NoMultiClickListener(){
            @Override
            public void onNewClick(View v) {
                if(mOnClickListener != null){
                    mOnClickListener.onConfirm();
                }
                dismiss();
            }
        });
    }

    public void setOnClickListener(OnClickListener listener){
        mOnClickListener = listener;
    }

    public interface OnClickListener{
        void onConfirm();
    }
}
