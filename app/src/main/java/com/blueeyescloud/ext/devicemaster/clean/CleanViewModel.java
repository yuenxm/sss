package com.blueeyescloud.ext.devicemaster.clean;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.blueeyescloud.baselib.mvvm.BaseViewModel;
import com.blueeyescloud.ext.devicemaster.entity.ResultInfo;
import com.blueeyescloud.ext.devicemaster.utils.Cn2SpellUtils;
import com.blueeyescloud.ext.http.RetrofitException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CleanViewModel extends BaseViewModel {
    MutableLiveData<List<InstalledAppInfo>> installAppInfoLiveData = new MutableLiveData<>(); //已安装应用基本信息
    public MutableLiveData<Set<Integer>> selectedAppPositions = new MutableLiveData<>(); //选中的位置
    MutableLiveData<Boolean> needRebootObservableField = new MutableLiveData<>(); //是否需要重启
    private int sourceType;
    public MutableLiveData<ResultInfo> stepStatusLiveData = new MutableLiveData<>(); //当前操作的状态

    public CleanViewModel(Application application) {
        super(application);
        selectedAppPositions.setValue(new HashSet<Integer>());
        needRebootObservableField.setValue(false);

        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setStatus(CleanConstants.StepStatusType.NONE);
        stepStatusLiveData.setValue(resultInfo);
    }

    public void setSourceType(int type){
        sourceType = type;
    }

    public int getSourceType(){
        return sourceType;
    }

    public void  queryInstalledAppInfo(Context context){
        loadingLiveData.setValue(true);
        //获取已安装的非系统应用的基本信息
        List<InstalledAppInfo> appInfoList = getInstalledAppInfo(context);
        if(appInfoList.size() == 0){
            emptyDataLiveData.setValue(true);
        }else {
            //按拼音首字母升序排序
            Collections.sort(appInfoList, new Comparator<InstalledAppInfo>() {
                @Override
                public int compare(InstalledAppInfo o1, InstalledAppInfo o2) {
                    return Cn2SpellUtils.compareString(Cn2SpellUtils.converterToFirstSpell(o1.getAppName()).toLowerCase(),
                            Cn2SpellUtils.converterToFirstSpell(o2.getAppName()).toLowerCase());
                }
            });
            //获取已安装应用的大小
            queryInstalledAppPackageSize(appInfoList, context);
        }
    }

    /**
     * 获取已安装的非系统应用的基本信息
     * @param context
     * @return
     */
    private List<InstalledAppInfo> getInstalledAppInfo(Context context){
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        List<InstalledAppInfo> appInfoList = new ArrayList<>();
        for(int i = 0; i < packageInfos.size(); i++){
            PackageInfo packageInfo = packageInfos.get(i);
            if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0){
                //非系统应用
                InstalledAppInfo appInfo = new InstalledAppInfo();
                appInfo.setPackageName(packageInfo.packageName);
                appInfo.setIcon(packageInfo.applicationInfo.loadIcon(pm));
                appInfo.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
                appInfo.setVersionName(packageInfo.versionName);
                appInfoList.add(appInfo);
            }
        }
        return appInfoList;
    }

    /**
     * 创建单个应用获取包大小的observable
     * @param appInfo
     * @param context
     * @return
     */
    private Observable<InstalledAppInfo> getPackageSizeObservable(final InstalledAppInfo appInfo, final Context context){
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(final ObservableEmitter emitter) throws Exception {
                if (appInfo != null && appInfo.getPackageName() != null) {
                    //使用放射机制得到PackageManager类的隐藏函数getPackageSizeInfo
                    PackageManager pm = context.getPackageManager();  //得到pm对象
                    try {
                        //通过反射机制获得该隐藏函数 getDeclaredMethod
                        Method getPackageSizeInfo = pm.getClass().getMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
                        //调用该函数，并且给其分配参数 ，待调用流程完成后会回调PkgSizeObserver类的函数
                        getPackageSizeInfo.invoke(pm, appInfo.getPackageName(), new IPackageStatsObserver.Stub(){
                            @Override
                            public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
                                if(succeeded) {
                                    appInfo.setSize(pStats.cacheSize + pStats.dataSize + pStats.codeSize);
                                }
                                emitter.onNext(appInfo);
                                emitter.onComplete();
                            }
                        });
                    } catch (Exception ex) {
                        Log.e("tag_ss", "queryPackageSize Exception: " + ex.getMessage());
                        ex.printStackTrace();
                        emitter.onError(ex);
                    }
                }
            }
        });
    }

    /**
     * 获取已安装应用的包大小
     * @param appInfoList
     * @param context
     */
    private void queryInstalledAppPackageSize(List<InstalledAppInfo> appInfoList, final Context context){
        Log.i("tag_ss", "queryInstalledAppPackageSize appInfoList size = " + appInfoList.size());
        Observable.fromIterable(appInfoList)
                .flatMap(new Function<InstalledAppInfo, ObservableSource<InstalledAppInfo>>() {
                    @Override
                    public ObservableSource<InstalledAppInfo> apply(InstalledAppInfo appInfo) throws Exception {
                        return getPackageSizeObservable(appInfo, context);
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<InstalledAppInfo>>() {
                    @Override
                    public void accept(List<InstalledAppInfo> installedAppInfos) throws Exception {
                        loadingLiveData.setValue(false);
                        installAppInfoLiveData.setValue(installedAppInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("tag_ss","queryInstalledAppPackageSize exception = " + throwable.getMessage());
                        errorLiveData.setValue(RetrofitException.wrapperToErrorInfo(throwable));
                    }
                });
    }

    public boolean getNeedReboot(){
        return needRebootObservableField.getValue();
    }

    public void setNeedReboot(boolean needReboot){
        needRebootObservableField.setValue(needReboot);
    }

    public void cleanSelectedApps(){
//        Disposable disposable =
//                cleanAppsRxObservable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer result) throws Exception {
//                        //成功
//                        ResultInfo resultInfo = new ResultInfo(CleanConstants.StepStatusType.CLEAN_APPS_SUCCESS, null);
//                        stepStatusLiveData.setValue(resultInfo);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        //失败
//                        ResultInfo resultInfo = new ResultInfo(CleanConstants.StepStatusType.CLEAN_APPS_FAIL, "清理应用失败");
//                        stepStatusLiveData.setValue(resultInfo);
//                    }
//                });
//
//        addDisposable(disposable);

        //todo for testing，需要注释掉下面这个，恢复上面注释内容
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //成功
                ResultInfo resultInfo = new ResultInfo(CleanConstants.StepStatusType.CLEAN_APPS_SUCCESS, null);//"清理失败");
                stepStatusLiveData.setValue(resultInfo);
            }
        }, 3000);
    }

    private Observable cleanAppsRxObservable = Observable.create(new ObservableOnSubscribe<Integer>(){
        @Override
        public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
            List<Integer> selectedPosList = new ArrayList<>(selectedAppPositions.getValue());
            Log.e("tag_ss", "selectedPosList 0000 = " + selectedPosList);
            Collections.sort(selectedPosList); //位置按从小到大排序
            Log.e("tag_ss", "selectedPosList = " + selectedPosList);

            for(int i = 0; i < selectedPosList.size(); i++){
                InstalledAppInfo info = installAppInfoLiveData.getValue().get(i);
                int res = 0;//NativeHelper.getInstance().clearApp(info.getPackageName()); //todo 需要恢复这段注释代码
                if(res != 0){
                    emitter.onError(new Exception("应用" + info.getAppName() + "清理失败"));
                    return;
                }
            }
            emitter.onNext(0);
            emitter.onComplete();
        }
    });
}
