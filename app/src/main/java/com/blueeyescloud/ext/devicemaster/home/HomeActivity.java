package com.blueeyescloud.ext.devicemaster.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.blueeyescloud.baselib.mvvm.BaseActivity;
import com.blueeyescloud.ext.R;
import com.blueeyescloud.ext.databinding.ActivityDeviceMasterBinding;
import com.blueeyescloud.ext.devicemaster.app.AppViewModelFactory;
import com.blueeyescloud.ext.devicemaster.clean.ActivityAppClean;
import com.blueeyescloud.ext.devicemaster.myplanlist.ActivityMyPlanList;
import com.blueeyescloud.ext.devicemaster.plan.ui.ActivityPlan;
import com.blueeyescloud.ext.devicemaster.plan.ui.ActivitySelfSelectDevice;
import com.blueeyescloud.ext.devicemaster.plan.PlanConstants;

public class HomeActivity extends BaseActivity<ActivityDeviceMasterBinding, HomeViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_device_master;
    }

    @Override
    protected HomeViewModel createViewModel() {
        return new ViewModelProvider(this,
                AppViewModelFactory.getInstance(getApplication()))
                .get(HomeViewModel.class);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Context context) {

    }

    @Override
    protected void onRetryClicked() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setOnClickListener(new OnClickListener());
        binding.setDeviceInfoBrief(viewModel.getDeviceInfoBrief(this));
    }

    public class OnClickListener{
        public void onClick(View view){
            switch (view.getId()){
                case R.id.ibtn_back:
                    finish();
                    break;
                case R.id.btn_device_detail:
                    Intent intent0 = new Intent(HomeActivity.this, ActivityPlan.class);
                    intent0.putExtra(PlanConstants.Param.SOURCE_TYPE, PlanConstants.SourceType.HOME_DEVICE_INFO);
                    startActivity(intent0);
                    break;
                case R.id.device_home_new_phone:
                    Intent intent1 = new Intent(HomeActivity.this, ActivityPlan.class);
                    intent1.putExtra(PlanConstants.Param.SOURCE_TYPE, PlanConstants.SourceType.ONE_CLICK_NEW_PHONE);
                    startActivity(intent1);
                    break;
                case R.id.device_home_choose_phone:
                    Intent intent2 = new Intent(HomeActivity.this, ActivitySelfSelectDevice.class);
                    intent2.putExtra(PlanConstants.Param.SOURCE_TYPE, PlanConstants.SourceType.SELF_SELECTED_PHONE);
                    startActivity(intent2);
                    break;
                case R.id.device_home_my_device_plan:
                    Intent intent3 = new Intent(HomeActivity.this, ActivityMyPlanList.class);
                    startActivity(intent3);
                    break;
                case R.id.device_home_phone_plan_change_list:
                    //TODO 后续功能
                    break;
                case R.id.device_home_one_click_clean:
                    Intent intent5 = new Intent(HomeActivity.this, ActivityAppClean.class);
                    startActivity(intent5);
                    break;
                case R.id.device_home_app_backup:
                    //TODO 后续功能
                    break;
            }
        }
    }
}
