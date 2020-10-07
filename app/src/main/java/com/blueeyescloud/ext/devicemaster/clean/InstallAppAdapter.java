package com.blueeyescloud.ext.devicemaster.clean;

import android.widget.CompoundButton;

import androidx.databinding.ViewDataBinding;

import com.blueeyescloud.baselib.mvvm.adapter.BaseBindingRecyclerAdapter;
import com.blueeyescloud.ext.BR;
import com.blueeyescloud.ext.R;
import com.blueeyescloud.ext.databinding.ItemDeviceCleanAppBinding;

import java.util.List;
import java.util.Set;

public class InstallAppAdapter extends BaseBindingRecyclerAdapter<InstalledAppInfo> {
    private CleanViewModel mViewModel;

    public InstallAppAdapter(List<InstalledAppInfo> list, CleanViewModel viewModel) {
        super(list, R.layout.item_device_clean_app, BR.appInfo);
        mViewModel = viewModel;
    }

    @Override
    protected void onBindViewHolder(final InstalledAppInfo data, ViewDataBinding binding, final int position) {
        super.onBindViewHolder(data, binding, position);
        ItemDeviceCleanAppBinding viewBinding = (ItemDeviceCleanAppBinding) binding;
        viewBinding.setPosition(position);
        viewBinding.cbSelectApp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Set<Integer> set = mViewModel.selectedAppPositions.getValue();
                if(isChecked){
                    set.add(position);
                }else{
                    set.remove(position);
                }
                mViewModel.selectedAppPositions.setValue(set);
            }
        });
        viewBinding.setSelectedSets(mViewModel.selectedAppPositions.getValue());
    }
}
