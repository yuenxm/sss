package com.blueeyescloud.ext.devicemaster.clean;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blueeyescloud.baselib.mvvm.BaseFragment;
import com.blueeyescloud.baselib.utils.ToastUtils;
import com.blueeyescloud.ext.R;
import com.blueeyescloud.ext.databinding.FragmentAppCleanBinding;
import com.blueeyescloud.ext.devicemaster.app.AppViewModelFactory;
import com.blueeyescloud.ext.devicemaster.plan.PlanConstants;
import com.blueeyescloud.ext.devicemaster.entity.ResultInfo;
import com.blueeyescloud.ext.devicemaster.plan.util.FragmentNavigationUtil;
import com.blueeyescloud.ext.devicemaster.plan.viewmodel.DevicePlanViewModel;
import com.blueeyescloud.ext.view.TipsView;
import com.blueeyescloud.ext.view.NoMultiClickListener;
import com.blueeyescloud.ext.view.TitleBar;

import java.util.List;
import java.util.Set;

public class FragmentAppClean extends BaseFragment<FragmentAppCleanBinding, CleanViewModel> {
    private int sourceType;
    private CleanStepDialogMgr cleanStepDialogMgr;
    private DevicePlanViewModel mDevicePlanViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_app_clean;
    }

    @Override
    protected CleanViewModel createViewModel() {
        return new ViewModelProvider(this,
                AppViewModelFactory.getInstance(mActivity.getApplication()))
                .get(CleanViewModel.class);
    }

    @Override
    protected void initView() {
        binding.setOnBackListener(new TitleBar.OnBackClickListener() {
            @Override
            public void onBackClick() {
                if(sourceType == PlanConstants.SourceType.HOME_APP_CLEAN) {
                    mActivity.finish();
                }else{
                    FragmentNavigationUtil.back(mActivity);
                }
            }
        });
        binding.setClickListener(mNoMultiClickListener);

        //初始化recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.bg_plan_list_divider));
        binding.recyclerView.addItemDecoration(itemDecoration);

        //addtips
        addTipsView(binding.rlytContentParent, new TipsView(mActivity));
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        sourceType = PlanConstants.SourceType.HOME_DEVICE_INFO;
        if(bundle != null){
            sourceType = bundle.getInt(PlanConstants.Param.SOURCE_TYPE, PlanConstants.SourceType.HOME_DEVICE_INFO);
        }
        viewModel.setSourceType(sourceType);
        binding.setViewmodel(viewModel);

        //获取已安装的应用列表信息
        viewModel.queryInstalledAppInfo(mActivity);
        viewModel.installAppInfoLiveData.observe(this, new Observer<List<InstalledAppInfo>>(){
            @Override
            public void onChanged(List<InstalledAppInfo> installedAppInfos) {
                InstallAppAdapter appAdapter = new InstallAppAdapter(installedAppInfos, viewModel);
                binding.recyclerView.setAdapter(appAdapter);
            }
        });

        viewModel.stepStatusLiveData.observe(this, new Observer<ResultInfo>() {
            @Override
            public void onChanged(ResultInfo resultInfo) {
                switch (resultInfo.getStatus()) {
                    case CleanConstants.StepStatusType.CLEAN_APPS_START:
                        cleanStepDialogMgr.showDialog(CleanConstants.CleanStepDialogStyle.TYPE_LOADING);
                        viewModel.cleanSelectedApps();
                        break;
                    case CleanConstants.StepStatusType.CLEAN_APPS_SUCCESS:
                        if(sourceType == PlanConstants.SourceType.GENERATE_NEW_DEVICE_CLEAN) {
                            mDevicePlanViewModel.enablePlanInfoAsNewDevice();
                            cleanStepDialogMgr.showDialog(CleanConstants.CleanStepDialogStyle.TYPE_TIP);
                        }else{
                            cleanStepDialogMgr.showDialog(CleanConstants.CleanStepDialogStyle.TYPE_COUNTDOWN);
                        }
                        break;
                    case CleanConstants.StepStatusType.ENABLE_NEW_DEVICE_SUCCESS:
                        //back to home page or reboot
                        cleanStepDialogMgr.showDialog(CleanConstants.CleanStepDialogStyle.TYPE_COUNTDOWN);
                        break;
                    case CleanConstants.StepStatusType.CLEAN_APPS_FAIL:
                    case CleanConstants.StepStatusType.ENABLE_NEW_DEVICE_FAIL:
                        ToastUtils.show(mActivity, resultInfo.getMessage());
                        cleanStepDialogMgr.dismissDialog();
                        break;
                }
            }
        });

        cleanStepDialogMgr = new CleanStepDialogMgr(mActivity);
        viewModel.needRebootObservableField.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                cleanStepDialogMgr.setNeedReboot(aBoolean);
            }
        });

        mDevicePlanViewModel = new ViewModelProvider(mActivity,
                AppViewModelFactory.getInstance(mActivity.getApplication()))
                .get(DevicePlanViewModel.class);
        mDevicePlanViewModel.enablePlanResultLiveData.observe(this, new Observer<ResultInfo>() {
            @Override
            public void onChanged(ResultInfo resultInfo) {
                ResultInfo stepResultInfo = new ResultInfo();
                stepResultInfo.setMessage(resultInfo.getMessage());
                if(resultInfo.getStatus() == ResultInfo.SUCCESS){
                    stepResultInfo.setStatus(CleanConstants.StepStatusType.ENABLE_NEW_DEVICE_SUCCESS);
                }else if(resultInfo.getStatus() == ResultInfo.FAIL){
                    stepResultInfo.setStatus(CleanConstants.StepStatusType.ENABLE_NEW_DEVICE_FAIL);
                }
                viewModel.stepStatusLiveData.setValue(stepResultInfo);
            }
        });

        viewModel.selectedAppPositions.observe(this, new Observer<Set<Integer>>() {
            @Override
            public void onChanged(Set<Integer> selectedAppsSet) {
                if(selectedAppsSet == null || selectedAppsSet.size() == 0){
                    binding.btnCleanAtOnce.setEnabled(false);
                }else{
                    binding.btnCleanAtOnce.setEnabled(true);
                }
            }
        });
    }

    @Override
    protected void onRetryClicked() {

    }

    NoMultiClickListener mNoMultiClickListener = new NoMultiClickListener() {
        @Override
        public void onNewClick(View v) {
            switch (v.getId()){
                case R.id.btn_clean_skip:
                    ResultInfo resultInfo = new ResultInfo(CleanConstants.StepStatusType.CLEAN_APPS_SUCCESS, null);
                    viewModel.stepStatusLiveData.setValue(resultInfo);
                    break;
                case R.id.btn_clean_at_once:
                    ResultInfo resultInfo2 = new ResultInfo(CleanConstants.StepStatusType.CLEAN_APPS_START, null);
                    viewModel.stepStatusLiveData.setValue(resultInfo2);
                    break;
            }
        }
    };
}
