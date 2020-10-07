package com.blueeyescloud.ext.devicemaster.plan.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blueeyescloud.baselib.mvvm.BaseAppCompatActivity;
import com.blueeyescloud.ext.R;

public class ActivityPlan extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_plan);

        FragmentPlan fragment = new FragmentPlan();
        Bundle bundle = new Bundle();
        bundle.putAll(getIntent().getExtras());
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.flyt_content, fragment).commit();
    }
}
