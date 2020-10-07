package com.blueeyescloud.baselib.mvvm;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxManager {
    //管理RxJava请求
    private CompositeDisposable compositeDisposable;

    //添加RxJava发出到请求
    public void addDisposable(Disposable disposable){
        if(compositeDisposable == null || compositeDisposable.isDisposed()){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void dispose(){
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
    }
}
