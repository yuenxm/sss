package com.blueeyescloud.baselib.mvvm;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Activity里有MutableLiveData，Fragment监听，如果多次退出和new fragment，
 * 注意到每次重新进入一个fragment，之前MutableLiveData由于为非空，会自动触发。
 * 但该fragment是新的，预期不应该立刻响应监听。使用PublicMutableLiveData可防止
 * 出现这种多次调用的情况。
 * @param <T>
 */
public class PublicMutableLiveData<T> extends MutableLiveData<T> {

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, observer);
        hook(observer);
    }

    private void hook(Observer<? super T> observer) {
        Class<LiveData> liveDataClass = LiveData.class;
        try {
            Field mObservers = liveDataClass.getDeclaredField("mObservers");
            mObservers.setAccessible(true);
            Object mObserversObject = mObservers.get(this);
            if(mObserversObject == null){
                return;
            }
            Class<?> mObserversClass = mObserversObject.getClass();
            Method methodGet = mObserversClass.getDeclaredMethod("get", Object.class);
            methodGet.setAccessible(true);
            Object entry = methodGet.invoke(mObserversObject, observer);
            if(!(entry instanceof Map.Entry)){
                return;
            }
            Object lifecycleBoundObserver = ((Map.Entry) entry).getValue();

            Class<?> observerWrapper = lifecycleBoundObserver.getClass().getSuperclass();
            if(observerWrapper == null){
                return;
            }
            Field mLastVersionField = observerWrapper.getDeclaredField("mLastVersion");
            mLastVersionField.setAccessible(true);

            Method versionMethod = liveDataClass.getDeclaredMethod("getVersion");
            versionMethod.setAccessible(true);
            Object version = versionMethod.invoke(this);

            mLastVersionField.set(lifecycleBoundObserver, version);

            mObservers.setAccessible(false);
            methodGet.setAccessible(false);
            mLastVersionField.setAccessible(false);
            versionMethod.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}