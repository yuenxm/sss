package com.blueeyescloud.ext.devicemaster.plan.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.blueeyescloud.baselib.mvvm.BaseAppCompatActivity;
import com.blueeyescloud.ext.R;


public class ActivitySelfSelectDevice extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_self_select);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentSelfSelect fragment = new FragmentSelfSelect();
        transaction.add(R.id.flyt_content, fragment);
        transaction.commit();
    }
}
