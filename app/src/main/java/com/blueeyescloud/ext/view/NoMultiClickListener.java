package com.blueeyescloud.ext.view;

import android.view.View;

/**防止多次点击
 * Created by caill on 2018/5/22.
 */

public abstract class NoMultiClickListener implements View.OnClickListener {
    private final long INTERVAL = 600;
    private long mLastClickTime = 0;

    public abstract void onNewClick(View v);//{};

    @Override
    public void onClick(View v) {
        long currentClickTime = System.currentTimeMillis();
        if (currentClickTime - mLastClickTime > INTERVAL){
            onNewClick(v);
        }
        mLastClickTime = currentClickTime;
    }
}
