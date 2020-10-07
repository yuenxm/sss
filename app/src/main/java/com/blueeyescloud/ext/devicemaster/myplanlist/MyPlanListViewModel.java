package com.blueeyescloud.ext.devicemaster.myplanlist;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.blueeyescloud.baselib.mvvm.BaseViewModel;
import com.blueeyescloud.baselib.mvvm.ErrorInfo;
import com.blueeyescloud.ext.devicemaster.entity.MasterPlanEntity;

public class MyPlanListViewModel extends BaseViewModel {
    public LiveData<PagedList<MasterPlanEntity>> planPagedList;
    public MyPlanListDataSourceFactory dataSourceFactory;
    //基类里的loadingLiveData监听的默认行为不太适合现在的界面，重新定义一个使用
    public MutableLiveData<Boolean> listLoadingLiveData = new MutableLiveData<>();

    public MyPlanListViewModel(Application application){
        super(application);
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(MyPlanListDataSource.PAGE_SIZE)
                .setInitialLoadSizeHint(MyPlanListDataSource.PAGE_SIZE * 2) //首次加载数据的数量
                .setPrefetchDistance(3)//当距离底部还有多少条数据时开始加载下一页
                .build();

        dataSourceFactory =  new MyPlanListDataSourceFactory();
        planPagedList = new LivePagedListBuilder<>(dataSourceFactory, config).build();

        listLoadingLiveData = (MutableLiveData)Transformations.switchMap(dataSourceFactory.mDataSourceLiveData,
                new Function<MyPlanListDataSource, LiveData<Boolean>>() {
                    @Override
                    public LiveData<Boolean> apply(MyPlanListDataSource input) {
                        return input.loadingState;
                    }
                });

        errorLiveData = (MutableLiveData)Transformations.switchMap(dataSourceFactory.mDataSourceLiveData,
                new Function<MyPlanListDataSource, LiveData<ErrorInfo>>() {
                    @Override
                    public LiveData<ErrorInfo> apply(MyPlanListDataSource input) {
                        return input.errorState;
                    }
                });

        emptyDataLiveData = (MutableLiveData)Transformations.switchMap(dataSourceFactory.mDataSourceLiveData,
                new Function<MyPlanListDataSource, LiveData<Boolean>>() {
                    @Override
                    public LiveData<Boolean> apply(MyPlanListDataSource input) {
                        return input.emptyState;
                    }
                });
    }

    public void refresh(){
        Log.e("tag_ss", "refresh datasource 0000");
        MyPlanListDataSource dataSource = dataSourceFactory.mDataSourceLiveData.getValue();
        if(dataSource != null) {
            Log.e("tag_ss", "refresh datasource 1111");
            dataSource.invalidate();
        }
    }
}
