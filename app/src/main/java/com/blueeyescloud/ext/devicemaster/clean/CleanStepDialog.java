package com.blueeyescloud.ext.devicemaster.clean;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.blueeyescloud.ext.R;
import com.blueeyescloud.ext.databinding.DialogDeviceStepsBinding;

public class CleanStepDialog extends Dialog {
    private DialogDeviceStepsBinding binding;
    private DialogParams mDialogParams;

    public CleanStepDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public CleanStepDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.dialog_device_steps, null, false);
        setContentView(binding.getRoot());
        binding.setParams(mDialogParams);
    }

    public void setTitle(String title){
        binding.tvTitle.setText(title);
        if(!TextUtils.isEmpty(title)){
            binding.tvTitle.setVisibility(View.VISIBLE);
        }
    }

    public void setContent(String content){
        binding.tvContent.setText(content);
        if(!TextUtils.isEmpty(content)){
            binding.tvTitle.setVisibility(View.VISIBLE);
        }
    }

    public void enableProgressbar(boolean enableProgressbar){
        binding.progressBar.setVisibility(enableProgressbar ? View.VISIBLE : View.GONE);
    }

    public static class DialogParams{
        boolean showLoading;
        String title;
        String content;

        public boolean isShowLoading() {
            return showLoading;
        }

        public void setShowLoading(boolean showLoading) {
            this.showLoading = showLoading;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public void setDialogParams(DialogParams params){
        mDialogParams = params;
        if(binding != null){
            binding.setParams(params);
        }
    }
}
