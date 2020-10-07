package com.blueeyescloud.ext.devicemaster.plan.util;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class FragmentNavigationUtil {

    public static void back(FragmentActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        int backEntryCount = fragmentManager.getBackStackEntryCount();
        if (backEntryCount >= 1) {
            activity.getSupportFragmentManager().popBackStack();
        } else {
            activity.finish();
        }
    }
}
