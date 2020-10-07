package com.blueeyescloud.baselib.mvvm;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public abstract class AbstractTipsView extends RelativeLayout implements ITipsView {


    public AbstractTipsView(Context context) {
        super(context);
    }

    public AbstractTipsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractTipsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnReTryClickListener(OnReTryClickListener listener) {

    }
}
