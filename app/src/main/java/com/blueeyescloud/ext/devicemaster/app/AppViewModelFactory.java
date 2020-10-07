package com.blueeyescloud.ext.devicemaster.app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;

public class AppViewModelFactory implements ViewModelProvider.Factory{
    Application mApplication;

    private static AppViewModelFactory sInstance;

    public static AppViewModelFactory getInstance(Application application){
        if(sInstance == null){
            sInstance = new AppViewModelFactory(application);
        }
        return sInstance;
    }

    private AppViewModelFactory(){

    }

    private AppViewModelFactory(Application application){
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
//            return modelClass.newInstance();
            return modelClass.getConstructor(Application.class).newInstance(mApplication);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
