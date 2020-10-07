package com.blueeyescloud.ext.devicemaster.plan.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blueeyescloud.baselib.mvvm.BaseViewModel;
import com.blueeyescloud.baselib.net.RetrofitManager;
import com.blueeyescloud.baselib.utils.LogUtils;
import com.blueeyescloud.ext.http.ApiService;
import com.blueeyescloud.ext.http.RetrofitException;

import java.net.ConnectException;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SelfSelectViewModel extends BaseViewModel {
    public MutableLiveData<Map<String, List<String>>> modelMapLiveData = new MutableLiveData<>();

    public SelfSelectViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadData(){
        loadingLiveData.setValue(true);
        Disposable disposable = RetrofitManager.getInstance()
                .create(ApiService.class)
                .queryDeviceModels2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Map<String, List<String>>>() {
                    @Override
                    public void accept(Map<String, List<String>> stringStringMap) throws Exception {
                        loadingLiveData.setValue(false);
                        modelMapLiveData.setValue(stringStringMap);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        errorLiveData.setValue(RetrofitException.wrapperToErrorInfo(throwable));
                    }
                });
        addDisposable(disposable);
    }
}
