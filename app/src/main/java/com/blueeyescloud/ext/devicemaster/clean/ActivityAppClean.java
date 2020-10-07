package com.blueeyescloud.ext.devicemaster.clean;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.blueeyescloud.baselib.mvvm.BaseAppCompatActivity;
import com.blueeyescloud.ext.R;
import com.blueeyescloud.ext.devicemaster.plan.PlanConstants;

public class ActivityAppClean extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_clean);

        Fragment fragment = new FragmentAppClean();
        Bundle bundle = new Bundle();
        bundle.putInt(PlanConstants.Param.SOURCE_TYPE, PlanConstants.SourceType.HOME_APP_CLEAN);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.flyt_content, fragment).commit();
    }
}