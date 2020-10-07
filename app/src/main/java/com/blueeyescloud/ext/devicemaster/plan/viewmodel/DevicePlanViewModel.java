package com.blueeyescloud.ext.devicemaster.plan.viewmodel;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blueeyescloud.baselib.mvvm.BaseViewModel;
import com.blueeyescloud.baselib.mvvm.PublicMutableLiveData;
import com.blueeyescloud.baselib.net.RetrofitManager;
import com.blueeyescloud.baselib.utils.LogUtils;
import com.blueeyescloud.baselib.utils.SystemUtils;
import com.blueeyescloud.ext.devicemaster.app.DeviceMasterApp;
import com.blueeyescloud.ext.devicemaster.entity.AttributeEntity;
import com.blueeyescloud.ext.devicemaster.entity.MasterPlanEntity;
import com.blueeyescloud.ext.devicemaster.plan.PlanConstants;
import com.blueeyescloud.ext.devicemaster.plan.util.ReflectUtils;
import com.blueeyescloud.ext.devicemaster.entity.ResultInfo;
import com.blueeyescloud.ext.http.ApiService;
import com.blueeyescloud.ext.http.RetrofitException;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DevicePlanViewModel extends BaseViewModel {
    private static final String TAG = "DevicePlanViewModel";

    //页面跳转会带过来的信息
    public MutableLiveData<MasterPlanEntity> deviceInfoLiveData = new MutableLiveData<>(); //方案对象
    public int sourceType; //页面来源
    public String deviceModel; //设备型号

    public MutableLiveData<AttributeEntity> attributeLiveData = new MutableLiveData<>();

    public MutableLiveData<ResultInfo> savePlanResultLiveData = new MutableLiveData<>();

    public MutableLiveData<ResultInfo> enablePlanResultLiveData = new PublicMutableLiveData<>();

    public DevicePlanViewModel(@NonNull Application application) {
        super(application);
    }

//    public MutableLiveData<MasterPlanEntity> getMasterPlanLiveData(){
//        if(deviceInfoLiveData == null){
//            deviceInfoLiveData = new MutableLiveData<>();
//            MasterPlanEntity masterPlanEntity = new MasterPlanEntity();
//            masterPlanEntity.setAttribute(new AttributeEntity());
//            deviceInfoLiveData.setValue(masterPlanEntity);
//        }
//        return deviceInfoLiveData;
//    }

    public void loadData(int sourceType, String deviceModel, MasterPlanEntity masterPlanEntity){
        this.sourceType = sourceType;
        this.deviceModel = deviceModel;
        if(sourceType == PlanConstants.SourceType.HOME_DEVICE_INFO){
            //首页进入，查看本机的设备信息，需要读取当前本机的信息
            //各个属性首选是通过java方式调用，如果java方式获取不到属性值，再调用so方法获取
            MasterPlanEntity deviceInfo = buildLocalMasterPlanEntity();
            deviceInfoLiveData.setValue(deviceInfo);
        }else {
            //非直接读取本地设备信息，需要发请求获取或者从外部跳转带入entity数据
            if (masterPlanEntity != null) {
                //带入方案具体内容，则显示具体内容
                deviceInfoLiveData.setValue(masterPlanEntity);
            } else {
                //否则，请求服务端数据
                queryDeviceChangeInfo(deviceModel);
            }
        }
    }

//    private void internalLoadData(boolean isFromLocal){
//        if(isFromLocal){
//            //获取本地设备信息，各个属性首选是通过java方式调用，如果java方式获取不到属性值，再调用so方法获取
//            DeviceInfoEntity deviceInfoEntity = buildLocalDeviceInfo();
//            deviceInfoLiveData.setValue(deviceInfoEntity);
//        }else{
//            //需要请求网络数据
//            loadRemoteData();
//        }
//    }

    private MasterPlanEntity buildLocalMasterPlanEntity(){
        MasterPlanEntity masterPlanEntity = new MasterPlanEntity();
        masterPlanEntity.setId("1111");
        masterPlanEntity.setName("方案2334355");

        AttributeEntity attribute = new AttributeEntity();
        attribute.setManufacturer(SystemUtils.getManufacture());
        attribute.setBrand(SystemUtils.getBrand());
        attribute.setModel(SystemUtils.getModel());
        attribute.setAndroidid("213535345");
        attribute.setImei("3235");
        attribute.setPhonenum("133039332");
//        attribute.setSerial((NativeHelper.getInstance().getAttr("serial"));
//        attribute.setPhonenum(NativeHelper.getInstance().getAttr("phonenum"));
//        attribute.setAndroidid(NativeHelper.getInstance().getAttr("androidid"));
//        attribute.setImsi(NativeHelper.getInstance().getAttr("imsi"));
//        attribute.setImei(NativeHelper.getInstance().getAttr("imei"));
//        attribute.setIccid(NativeHelper.getInstance().getAttr("seetIccid"));
//        attribute.setWifiname(NativeHelper.getInstance().getAttr("wifiname"));
//        attribute.setWifimac(NativeHelper.getInstance().getAttr("wifimac"));

        masterPlanEntity.setAttribute(attribute);
        return masterPlanEntity;
    }

    //TODO SystemUtils有些方法需要权限才可以，不知道是否一定能取到。而且里面的一些方法实现没完成
    //TODO 需要对比和jni方式的效率
    private AttributeEntity buildLocalDeviceInfoByJava(Context context){
        AttributeEntity deviceInfoEntity = new AttributeEntity();
        deviceInfoEntity.setManufacturer(SystemUtils.getManufacture());
        deviceInfoEntity.setBrand(SystemUtils.getBrand());
        deviceInfoEntity.setModel(SystemUtils.getModel());
        deviceInfoEntity.setSerial(SystemUtils.getSerialNo(context));
        deviceInfoEntity.setPhonenum(SystemUtils.getPhoneNumber(context));
        deviceInfoEntity.setAndroidid(SystemUtils.getAndroidId(context));
        deviceInfoEntity.setImsi(SystemUtils.getIMSI(context));
        deviceInfoEntity.setImei(SystemUtils.getIMEI());
        deviceInfoEntity.setIccid(SystemUtils.getICCID());
        deviceInfoEntity.setWifiname(SystemUtils.getWifiName(context));
        deviceInfoEntity.setWifimac(SystemUtils.getWifiMac());
        return deviceInfoEntity;
    }

    private void queryDeviceChangeInfo(final String deviceModel){
        loadingLiveData.setValue(true);
        Disposable disposable = RetrofitManager.getInstance()
                .create(ApiService.class)
                .queryDeviceChangeInfo2(DeviceMasterApp.getResourceId(), deviceModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AttributeEntity>() {
                    @Override
                    public void accept(AttributeEntity attributeEntity) throws Exception {
                        loadingLiveData.setValue(false);
                        MasterPlanEntity masterPlanEntity = new MasterPlanEntity();
                        masterPlanEntity.setAttribute(attributeEntity);
                        deviceInfoLiveData.setValue(masterPlanEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errorLiveData.setValue(RetrofitException.wrapperToErrorInfo(throwable));
                        Log.e("tag_ss", "thorw ext= " + throwable.getMessage());
                        throwable.getMessage();
                    }
                });
        addDisposable(disposable);

        //for testing
    //    deviceInfoLiveData.setValue(buildLocalMasterPlanEntity());
    }

    public boolean checkValid(String attr, String value){
        return true; //todo for testing
//        return NativeHelper.getInstance().checkAttr(attr, value); //TODO 暂时注释掉
    }

    public void addPlan(){
        progressDialogLiveData.setValue(true);
//        String attributionJson = new Gson().toJson(deviceInfoLiveData.getValue().getAttribute());
        Disposable disposable = RetrofitManager.getInstance()
                .create(ApiService.class)
                .addDevicePlan(DeviceMasterApp.getResourceId(),
                        deviceInfoLiveData.getValue().getName(),
                        deviceInfoLiveData.getValue().getAttribute())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        progressDialogLiveData.setValue(false);
                        if("SUCCESS".equals(s)){
                            ResultInfo resultInfo = new ResultInfo(PlanConstants.PlanOperationResult.ADD_SUCCESS, "success");
                            savePlanResultLiveData.setValue(resultInfo);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        progressDialogLiveData.setValue(false);
                        ResultInfo resultInfo = new ResultInfo(PlanConstants.PlanOperationResult.ADD_FAIL, throwable.getMessage());
                        savePlanResultLiveData.setValue(resultInfo);
                    }
                });
        addDisposable(disposable);
    }

    public void savePlan(){
        String planId = deviceInfoLiveData.getValue().getId();
//        String attributionJson = new Gson().toJson(deviceInfoLiveData.getValue().getAttribute());
        Disposable disposable = RetrofitManager.getInstance()
                .create(ApiService.class)
                .updateDevicePlan(planId, deviceInfoLiveData.getValue().getAttribute())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if("SUCCESS".equals(s)){
                            ResultInfo resultInfo = new ResultInfo(PlanConstants.PlanOperationResult.UPDATE_SUCCESS, "success");
                            savePlanResultLiveData.setValue(resultInfo);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ResultInfo resultInfo = new ResultInfo(PlanConstants.PlanOperationResult.UPDATE_FAIL, throwable.getMessage());
                        savePlanResultLiveData.setValue(resultInfo);
                    }
                });
        addDisposable(disposable);
    }

    public void enablePlanInfoAsNewDevice(){
////        String attributionJson = new Gson().toJson(deviceInfoLiveData.getValue().getAttribute());
//        Disposable disposable = RetrofitManager.getInstance()
//                .create(ApiService.class)
//                .enablePlanInfo(DeviceMasterApp.getResourceId(), deviceInfoLiveData.getValue().getAttribute())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<String>>() {
//                    @Override
//                    public void accept(List<String> strings) throws Exception {
//                        //返回属性列表不为空，则需要调用so去修改属性值
//                        setLocalAttributes(strings);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        ResultInfo resultInfo = new ResultInfo(CleanConstants.StepStatusType.ENABLE_NEW_DEVICE_FAIL, throwable.getMessage());
//                        enablePlanResultLiveData.setValue(resultInfo);
//                    }
//                });
//
//        addDisposable(disposable);

        Log.e("tag_ss", "enable plan to remote");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ResultInfo resultInfo = new ResultInfo(ResultInfo.SUCCESS, "打撒");
                enablePlanResultLiveData.setValue(resultInfo);
            }
        }, 3000);
    }

    private void setLocalAttributes(final List<String> strings){
        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                int res = 0;//标示成功
                if(strings != null && strings.size() > 0){
                    Map<String, String> map = new HashMap<>();
                    for(String str : strings) {
                        String value = (String) ReflectUtils.getFiledValue(str, deviceInfoLiveData.getValue(), AttributeEntity.class);
                        if(value != null) {
                            map.put(str, value);
                        }
                    }
                    LogUtils.d(TAG, "set attrs burst string = " + (new Gson().toJson(map)));
                    //todo 下面代码需要恢复
                    res = 0;//NativeHelper.getInstance().setAttrsBurst(new Gson().toJson(map)); //todo for testing
                    if(res == 0) {
                        LogUtils.d(TAG, "setAttrsBurst success");
                        emitter.onNext(res);
                        emitter.onComplete();
                    }else {
                        LogUtils.e(TAG, "setAttrsBurst failed " + res);
                        emitter.onError(new Exception("执行失败:应用" + "清理失败")); //TODO 提示语
                    }
                }else{
                    emitter.onNext(res);
                    emitter.onComplete();
                }
            }
        });

        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        ResultInfo resultInfo = new ResultInfo(ResultInfo.SUCCESS, null);
                        enablePlanResultLiveData.setValue(resultInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ResultInfo resultInfo = new ResultInfo(ResultInfo.FAIL, throwable.getMessage());
                        enablePlanResultLiveData.setValue(resultInfo);
                    }
                });
        addDisposable(disposable);
    }

    public void deletePlan(){
        String planId = deviceInfoLiveData.getValue().getId();
        progressDialogLiveData.setValue(true);
        Disposable disposable =
                RetrofitManager.getInstance()
                .create(ApiService.class)
                .deleteDevicePlan(planId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        progressDialogLiveData.setValue(false);
                        if("SUCCESS".equals(s)){
                            ResultInfo resultInfo = new ResultInfo(PlanConstants.PlanOperationResult.DELETE_SUCCESS, "success");
                            savePlanResultLiveData.setValue(resultInfo);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        progressDialogLiveData.setValue(false);
                        ResultInfo resultInfo = new ResultInfo(PlanConstants.PlanOperationResult.DELETE_SUCCESS, throwable.getMessage());
                        savePlanResultLiveData.setValue(resultInfo);
                    }
                });

        addDisposable(disposable);
    }
}
