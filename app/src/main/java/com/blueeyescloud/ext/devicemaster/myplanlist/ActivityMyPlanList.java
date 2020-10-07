package com.blueeyescloud.ext.devicemaster.myplanlist;

import android.content.Context;

import com.blueeyescloud.baselib.mvvm.BaseActivity;
import com.blueeyescloud.baselib.mvvm.BaseViewModel;
import com.blueeyescloud.ext.R;
import com.blueeyescloud.ext.databinding.ActivityMyPlanListBinding;
import com.blueeyescloud.ext.view.TitleBar;

public class ActivityMyPlanList extends BaseActivity<ActivityMyPlanListBinding, BaseViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_plan_list;
    }

    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }

    @Override
    protected void initView() {
        binding.setTitle(getString(R.string.device_plan_title_manage_plan));
        binding.setOnBackLisener(new TitleBar.OnBackClickListener() {
            @Override
            public void onBackClick() {
                finish();
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.flyt_content, new FragmentMyPlanList()).commit();
    }

    @Override
    protected void initData(Context context) {

    }

    @Override
    protected void onRetryClicked() {

    }

}
