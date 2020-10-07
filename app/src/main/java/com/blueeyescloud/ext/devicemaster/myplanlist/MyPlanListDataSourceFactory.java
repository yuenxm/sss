package com.blueeyescloud.ext.devicemaster.myplanlist;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.blueeyescloud.ext.devicemaster.entity.MasterPlanEntity;

public class MyPlanListDataSourceFactory extends DataSource.Factory<Integer, MasterPlanEntity> {
    MutableLiveData<MyPlanListDataSource> mDataSourceLiveData = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource<Integer, MasterPlanEntity> create() {
        MyPlanListDataSource dataSource = new MyPlanListDataSource();
        mDataSourceLiveData.postValue(dataSource);
        return dataSource;
    }
}
