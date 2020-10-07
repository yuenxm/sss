package com.blueeyescloud.ext.home;

import com.blueeyescloud.baselib.mvvm.adapter.BaseBindingRecyclerAdapter;
import com.blueeyescloud.ext.BR;
import com.blueeyescloud.ext.R;

import java.util.List;

public class HomServiceRecyclerAdapter extends BaseBindingRecyclerAdapter<ServiceResEntity> {

    public HomServiceRecyclerAdapter(List<ServiceResEntity> list) {
        super(list, R.layout.item_home_service, BR.serviceResEntity);
    }
}
