package com.blueeyescloud.ext.devicemaster.myplanlist;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PositionalDataSource;

import com.blueeyescloud.baselib.mvvm.ErrorInfo;
import com.blueeyescloud.baselib.net.RetrofitManager;
import com.blueeyescloud.baselib.utils.LogUtils;
import com.blueeyescloud.ext.devicemaster.app.DeviceMasterApp;
import com.blueeyescloud.ext.devicemaster.entity.MasterPlanEntity;
import com.blueeyescloud.ext.devicemaster.entity.PageResEntity;
import com.blueeyescloud.ext.http.ApiService;
import com.blueeyescloud.ext.http.RetrofitException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyPlanListDataSource extends PageKeyedDataSource<Integer, MasterPlanEntity> {
    public static final int FIRST_PAGE = 1;
    public static final int PAGE_SIZE = 10;

    MutableLiveData<Boolean> loadingState = new MutableLiveData<>();
    MutableLiveData<ErrorInfo> errorState = new MutableLiveData<>();
    MutableLiveData<Boolean> emptyState = new MutableLiveData<>();

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, MasterPlanEntity> callback) {
        Log.e("tag_ss", "loadInitial page = 1");
        loadingState.postValue(true);
        RetrofitManager.getInstance()
                .create(ApiService.class)
                .queryDevicePlans2(DeviceMasterApp.getResourceId(), FIRST_PAGE, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PageResEntity<MasterPlanEntity>>() {
                    @Override
                    public void accept(PageResEntity<MasterPlanEntity> pageResEntity) throws Exception {
                        loadingState.postValue(false);
                        if(pageResEntity == null || pageResEntity.getResult() == null || pageResEntity.getResult().size() == 0) {
                            emptyState.postValue(true);
                        }else{
                            callback.onResult(pageResEntity.getResult(), null, FIRST_PAGE + 1);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("tag_ss", "error = " + throwable.getMessage());
                        errorState.postValue(RetrofitException.wrapperToErrorInfo(throwable));
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MasterPlanEntity> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, MasterPlanEntity> callback) {
        LogUtils.e("tag_ss", "loadAfter page = " + params.key);
        RetrofitManager.getInstance()
                .create(ApiService.class)
                .queryDevicePlans2(DeviceMasterApp.getResourceId(), params.key, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PageResEntity<MasterPlanEntity>>() {
                    @Override
                    public void accept(PageResEntity<MasterPlanEntity> pageResEntity) throws Exception {
                        if(pageResEntity != null){
                            int previousTotalReceived = PAGE_SIZE * params.key;
                            int nowTotalReceived = previousTotalReceived + pageResEntity.getResult().size();
                            Integer nextKey = nowTotalReceived >= pageResEntity.getTotalCount() ? null : params.key + 1;
                            callback.onResult(pageResEntity.getResult(), nextKey);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("tag_ss", "error222 = " + throwable.getMessage());
                        throwable.printStackTrace();
                    }
                });
    }

    public void refresh(){
        invalidate();
    }
}
