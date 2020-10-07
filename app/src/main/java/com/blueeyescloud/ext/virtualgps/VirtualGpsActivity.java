package com.blueeyescloud.ext.virtualgps;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blueeyescloud.baselib.mvvm.BaseAppCompatActivity;
import com.blueeyescloud.ext.R;

public class VirtualGpsActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_gps);
    }
}
