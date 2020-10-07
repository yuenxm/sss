package com.blueeyescloud.baselib.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.Disposable;

public class BaseViewModel extends AndroidViewModel {
    //管理RxJava请求
    protected RxManager rxManager = new RxManager();
    //通知是否显示加载圈
    public MutableLiveData<Boolean> loadingLiveData = new MutableLiveData();
    //当ViewModel处理数据出现错误情况需要通知给界面
    public MutableLiveData<ErrorInfo> errorLiveData = new MutableLiveData<>();
    //是否显示无数据
    public MutableLiveData<Boolean> emptyDataLiveData = new MutableLiveData<>();
    //处理中的dialog
    public MutableLiveData<Boolean> progressDialogLiveData = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public void addDisposable(Disposable disposable){
        rxManager.addDisposable(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        rxManager.dispose();
    }
}
