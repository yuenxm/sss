package com.blueeyescloud.ext.devicemaster.plan.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.blueeyescloud.ext.R;
import com.blueeyescloud.ext.databinding.DialogAddPlanConfirmBinding;
import com.blueeyescloud.ext.devicemaster.plan.util.PlanUiUtils;
import com.blueeyescloud.ext.devicemaster.plan.viewmodel.DevicePlanViewModel;
import com.blueeyescloud.ext.view.NoMultiClickListener;

public class AddPlanConfirmDialog extends Dialog{
    private DevicePlanViewModel viewModel;

    public AddPlanConfirmDialog(DevicePlanViewModel viewModel, @NonNull Context context) {
        super(context);
        this.viewModel = viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogAddPlanConfirmBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_add_plan_confirm, null, false);
        setContentView(binding.getRoot());
        binding.setClickListener(mNoMultiClickListener);
        binding.setViewModel(viewModel);
        viewModel.deviceInfoLiveData.getValue().setName(PlanUiUtils.getRandomPlanName());
    }

    private NoMultiClickListener mNoMultiClickListener = new NoMultiClickListener() {
        @Override
        public void onNewClick(View v) {
            switch (v.getId()){
                case R.id.btn_cancel:
                    dismiss();
                    break;
                case R.id.btn_save:
                    if(mOnClickListener != null){
                        mOnClickListener.onConfirm();
                    }
                    break;
            }
        }
    };

    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener listener){
        this.mOnClickListener = listener;
    }

    public interface OnClickListener{
        void onConfirm();
    }
}
