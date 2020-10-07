package com.blueeyescloud.ext.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blueeyescloud.baselib.mvvm.BaseViewModel;
import com.blueeyescloud.baselib.net.RetrofitManager;
import com.blueeyescloud.baselib.utils.LogUtils;
import com.blueeyescloud.ext.R;
import com.blueeyescloud.ext.devicemaster.app.DeviceMasterApp;
import com.blueeyescloud.ext.http.ApiService;
import com.blueeyescloud.ext.http.RetrofitException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeServiceViewModel extends BaseViewModel {
    private static final String TAG = "HomeServiceViewModel";

    MutableLiveData<List<ServiceResEntity>> serviceListLiveData = new MutableLiveData<>();

    public HomeServiceViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadData(){
        loadingLiveData.setValue(true);
        String resourceId = DeviceMasterApp.getResourceId();
        final List<ServiceResEntity> serviceResEntityList = getServiceList(); //第一阶段使用本地数据，后面需要服务端去获取
        addDisposable(RetrofitManager.getInstance()
                .create(ApiService.class)
                .queryExpandEnabledInfo2(resourceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Map<String,Boolean>>() {
                    @Override
                    public void accept(Map<String,Boolean> mapBaseHttpResponse) throws Exception {
                        loadingLiveData.setValue(false);
                        if(serviceResEntityList != null && serviceResEntityList.size() > 0) {
                            for (ServiceResEntity entity : serviceResEntityList) {
                                Boolean enabled = mapBaseHttpResponse.get(entity.getKeyName());
                                entity.setGranted(enabled != null ? enabled : false);
                            }
                            serviceListLiveData.setValue(serviceResEntityList);
                        }else{
                            emptyDataLiveData.setValue(true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.i(TAG, "queryExpandEnabledInfo error = " + throwable.getMessage());
                        throwable.printStackTrace();
                        errorLiveData.setValue(RetrofitException.wrapperToErrorInfo(throwable));
                    }
                }));
    }

    private List<ServiceResEntity> getServiceList(){
        List<ServiceResEntity> serviceResEntityList  = new ArrayList<>();
        ServiceResEntity serviceResEntity = new ServiceResEntity();
        serviceResEntity.setKeyName("master");
        serviceResEntity.setName("设备大师");
        serviceResEntity.setDescription("一键新机、设置手机参数");
        serviceResEntity.setLimitedFree(true);
        serviceResEntity.setIcon(R.drawable.ic_ext_home_device_master);
        serviceResEntity.setUri("ext://blueeyescloud.com/devicemaster");
        serviceResEntityList.add(serviceResEntity);

        ServiceResEntity serviceResEntity2 = new ServiceResEntity();
        serviceResEntity2.setKeyName("gps");
        serviceResEntity2.setName("虚拟定位");
        serviceResEntity2.setDescription("设置云机实时地理位置");
        serviceResEntity2.setLimitedFree(true);
        serviceResEntity2.setIcon(R.drawable.ic_ext_home_gps);
        serviceResEntity2.setUri("ext://blueeyescloud.com/virtualgps");

        serviceResEntityList.add(serviceResEntity2);
        return serviceResEntityList;
    }
}
