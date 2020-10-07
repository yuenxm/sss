package com.blueeyescloud.ext.devicemaster.plan.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.blueeyescloud.baselib.mvvm.BaseFragment;
import com.blueeyescloud.baselib.utils.ToastUtils;
import com.blueeyescloud.ext.R;
import com.blueeyescloud.ext.databinding.FragmentDevicePlanBinding;
import com.blueeyescloud.ext.devicemaster.app.AppViewModelFactory;
import com.blueeyescloud.ext.devicemaster.clean.FragmentAppClean;
import com.blueeyescloud.ext.devicemaster.entity.AttributeEntity;
import com.blueeyescloud.ext.devicemaster.entity.MasterPlanEntity;
import com.blueeyescloud.ext.devicemaster.plan.util.FragmentNavigationUtil;
import com.blueeyescloud.ext.devicemaster.plan.widget.AddPlanConfirmDialog;
import com.blueeyescloud.ext.devicemaster.plan.PlanConstants;
import com.blueeyescloud.ext.devicemaster.plan.util.PlanUiUtils;
import com.blueeyescloud.ext.devicemaster.entity.ResultInfo;
import com.blueeyescloud.ext.devicemaster.plan.viewmodel.DevicePlanViewModel;
import com.blueeyescloud.ext.devicemaster.plan.widget.DeletePlanConfirmDialog;
import com.blueeyescloud.ext.view.TipsView;
import com.blueeyescloud.ext.view.NoMultiClickListener;
import com.blueeyescloud.ext.view.ProgressDialog;
import com.blueeyescloud.ext.view.TitleBar;
import com.google.gson.Gson;


public class FragmentPlan extends BaseFragment<FragmentDevicePlanBinding, DevicePlanViewModel> {


    @Override
    public int getLayoutId() {
        return R.layout.fragment_device_plan;
    }

    @Override
    protected DevicePlanViewModel createViewModel() {
        return new ViewModelProvider(getActivity(),
                AppViewModelFactory.getInstance(mActivity.getApplication()))
                .get(DevicePlanViewModel.class);
    }

    @Override
    protected void initView() {
        binding.titlebar.setOnBackClickListener(new TitleBar.OnBackClickListener() {
            @Override
            public void onBackClick() {
                FragmentNavigationUtil.back(mActivity);
            }
        });
        binding.setClickListener(new MyOnClickListener());

        addTipsView(binding.rlytMultipleStatus, new TipsView(mActivity));
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        int sourceType = PlanConstants.SourceType.ONE_CLICK_NEW_PHONE;
        if(bundle != null){
            sourceType = bundle.getInt(PlanConstants.Param.SOURCE_TYPE, PlanConstants.SourceType.ONE_CLICK_NEW_PHONE);
        }
        binding.setSourceType(sourceType);
        //数据监听
        viewModel.deviceInfoLiveData.observe(this, new Observer<MasterPlanEntity>() {
            @Override
            public void onChanged(MasterPlanEntity deviceInfoEntity) {
                binding.setMasterPlanEntity(deviceInfoEntity);
            }
        });
        viewModel.savePlanResultLiveData.observe(this, new Observer<ResultInfo>() {
            @Override
            public void onChanged(ResultInfo resultInfo) {
                switch (resultInfo.getStatus()){
                    case PlanConstants.PlanOperationResult.ADD_SUCCESS:
                    case PlanConstants.PlanOperationResult.UPDATE_SUCCESS:
                        ToastUtils.show(mActivity, R.string.device_plan_save_plan_success);
                        break;
                    case PlanConstants.PlanOperationResult.ADD_FAIL:
                    case PlanConstants.PlanOperationResult.UPDATE_FAIL:
                        ToastUtils.show(mActivity,
                                String.format(getString(R.string.device_plan_save_plan_fail_label), resultInfo.getMessage()));
                        break;
                    case PlanConstants.PlanOperationResult.DELETE_SUCCESS:
                        Log.e("tag_ss", "delte success activity = " + mActivity);
                        ToastUtils.show(mActivity, R.string.device_plan_delete_plan_success);
                        mActivity.setResult(PlanConstants.ActivityResultCode.NEED_UPDATE); //为了通知上一个界面，删除时间发生，可能需要刷新界面
                        mActivity.finish(); //退出界面
                        break;
                    case PlanConstants.PlanOperationResult.DELETE_FAIL:
                        ToastUtils.show(mActivity,
                                String.format(getString(R.string.device_plan_delete_plan_fail_label), resultInfo.getMessage()));
                        break;
                }
            }
        });
        viewModel.progressDialogLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shown) {
                if(shown){
                    if(mProgressDialog == null) {
                        mProgressDialog = new ProgressDialog.Builder(mActivity).create();
                    }
                    mProgressDialog.show();
                }else{
                    mProgressDialog.dismiss();
                }
            }
        });

        //请求数据
        loadData();
    }
    ProgressDialog mProgressDialog;

    @Override
    protected void onRetryClicked() {
        loadData();
    }

    private void loadData(){
        Bundle bundle = getArguments();
        int sourceType = PlanConstants.SourceType.ONE_CLICK_NEW_PHONE;
        MasterPlanEntity masterPlanEntity = null;
        String deviceModel = null;
        if(bundle != null){
            sourceType = bundle.getInt(PlanConstants.Param.SOURCE_TYPE, PlanConstants.SourceType.ONE_CLICK_NEW_PHONE);
            masterPlanEntity = bundle.getParcelable(PlanConstants.Param.PLAN_INFO);
            deviceModel = bundle.getString(PlanConstants.Param.PHONE_MODEL);
        }
        viewModel.loadData(sourceType, deviceModel, masterPlanEntity);
    }

    public class MyOnClickListener extends NoMultiClickListener {
        public void onNewClick(View v) {
            switch (v.getId()) {
                case R.id.btn_add_as_new_plan:
                    String gson = new Gson().toJson(viewModel.deviceInfoLiveData.getValue()); //for testing
                    Log.e("tag_ss", "gson = " + gson); //for testing

                    //先检查字段，再提交请求
                    if(validateAttributes()) {
                        if(PlanUiUtils.isAddPlan(viewModel.sourceType)){
                            final AddPlanConfirmDialog dialog = new AddPlanConfirmDialog(viewModel, mActivity);
                            dialog.setOnClickListener(new AddPlanConfirmDialog.OnClickListener() {
                                @Override
                                public void onConfirm() {
                                    Log.e("tag_ss", "name = " + viewModel.deviceInfoLiveData.getValue().getName());
                                    if(validatePlanName()) {
                                        viewModel.addPlan();
                                        dialog.dismiss();
                                    }
                                }
                            });
                            dialog.show();
                        }else{
                            viewModel.savePlan();
                        }
                    }

                    break;
                case R.id.btn_create_new_device:
                    nextStepGenerateNewDevice();
                    break;
                case R.id.btn_delete:
                    displayDeleteConfirmDialog();
                    break;
            }
        }
    }

    private void nextStepGenerateNewDevice(){
        FragmentAppClean fragmentAppClean = new FragmentAppClean();
        Bundle bundle = new Bundle();
        bundle.putInt(PlanConstants.Param.SOURCE_TYPE, PlanConstants.SourceType.GENERATE_NEW_DEVICE_CLEAN);
        fragmentAppClean.setArguments(bundle);
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        transaction.hide(this);
        transaction.add(R.id.flyt_content, fragmentAppClean);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private boolean validatePlanName(){
        String planName = viewModel.deviceInfoLiveData.getValue().getName();
        if(TextUtils.isEmpty(planName)){
            ToastUtils.show(mActivity, R.string.device_plan_name_empty);
            return false;
        }
        if(planName.length() > 64){
            ToastUtils.show(mActivity, R.string.device_plan_name_invalid);
            return false;
        }
        return true;
    }

    /**
     * 检验属性输入的有效性
     * @return
     */
    private boolean validateAttributes(){
        AttributeEntity attribute = viewModel.deviceInfoLiveData.getValue().getAttribute();
        if(TextUtils.isEmpty(attribute.getManufacturer())){
            ToastUtils.show(mActivity, R.string.device_plan_empty_manufacturer);
            return false;
        }
        if(!viewModel.checkValid("manufacture", attribute.getManufacturer())){
            ToastUtils.show(mActivity, R.string.device_plan_invalid_manufacturer);
            return false;
        }
        if(TextUtils.isEmpty(attribute.getBrand())){
            ToastUtils.show(mActivity, R.string.device_plan_empty_brand);
            return false;
        }
        if(!viewModel.checkValid("brand", attribute.getBrand())){
            ToastUtils.show(mActivity, R.string.device_plan_invalid_brand);
            return false;
        }
        if(TextUtils.isEmpty(attribute.getModel())){
            ToastUtils.show(mActivity, R.string.device_plan_empty_model);
            return false;
        }
        if(!viewModel.checkValid("model", attribute.getModel())){
            ToastUtils.show(mActivity, R.string.device_plan_invalid_model);
            return false;
        }
        if(TextUtils.isEmpty(attribute.getSerial())){
            ToastUtils.show(mActivity, R.string.device_plan_empty_serial);
            return false;
        }
        if(!viewModel.checkValid("serial", attribute.getSerial())){
            ToastUtils.show(mActivity, R.string.device_plan_invalid_serial);
            return false;
        }
        if(TextUtils.isEmpty(attribute.getPhonenum())){
            ToastUtils.show(mActivity, R.string.device_plan_empty_phonenum);
            return false;
        }
        if(!viewModel.checkValid("phonenum", attribute.getPhonenum())){
            ToastUtils.show(mActivity, R.string.device_plan_invalid_phonenum);
            return false;
        }
        if(TextUtils.isEmpty(attribute.getAndroidid())){
            ToastUtils.show(mActivity, R.string.device_plan_empty_androidid);
            return false;
        }
        if(!viewModel.checkValid("androidid", attribute.getAndroidid())){
            ToastUtils.show(mActivity, R.string.device_plan_invalid_androidid);
            return false;
        }
        if(TextUtils.isEmpty(attribute.getImei())){
            ToastUtils.show(mActivity, R.string.device_plan_empty_imei);
            return false;
        }
        if(!viewModel.checkValid("imei", attribute.getImei())){
            ToastUtils.show(mActivity, R.string.device_plan_invalid_imei);
            return false;
        }
        if(TextUtils.isEmpty(attribute.getImsi())){
            ToastUtils.show(mActivity, R.string.device_plan_empty_imsi);
            return false;
        }
        if(!viewModel.checkValid("imsi", attribute.getImsi())){
            ToastUtils.show(mActivity, R.string.device_plan_invalid_imsi);
            return false;
        }
        if(TextUtils.isEmpty(attribute.getIccid())){
            ToastUtils.show(mActivity, R.string.device_plan_empty_iccid);
            return false;
        }
        if(!viewModel.checkValid("iccid", attribute.getIccid())){
            ToastUtils.show(mActivity, R.string.device_plan_invalid_iccid);
            return false;
        }
        if(TextUtils.isEmpty(attribute.getWifiname())){
            ToastUtils.show(mActivity, R.string.device_plan_empty_wifiname);
            return false;
        }
        if(!viewModel.checkValid("wifiname", attribute.getWifiname())){
            ToastUtils.show(mActivity, R.string.device_plan_invalid_wifiname);
            return false;
        }
        if(TextUtils.isEmpty(attribute.getWifimac())){
            ToastUtils.show(mActivity, R.string.device_plan_empty_wifimac);
            return false;
        }
        if(!viewModel.checkValid("wifimac", attribute.getWifimac())){
            ToastUtils.show(mActivity, R.string.device_plan_invalid_wifimac);
            return false;
        }

        return true;
    }

    public void displayDeleteConfirmDialog(){
        DeletePlanConfirmDialog dialog = new DeletePlanConfirmDialog(mActivity);
        dialog.setOnClickListener(new DeletePlanConfirmDialog.OnClickListener() {
            @Override
            public void onConfirm() {
                viewModel.deletePlan();
            }
        });
        dialog.show();
    }

}
