package com.blueeyescloud.baselib.mvvm.adapter;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding binding;

    public ViewDataBinding getViewDataBinding(){
        return binding;
    }

    public ViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
