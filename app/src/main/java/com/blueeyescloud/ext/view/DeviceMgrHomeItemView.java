package com.blueeyescloud.ext.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blueeyescloud.ext.R;

public class DeviceMgrHomeItemView extends RelativeLayout {
    private OnClickListener mOnClickListener;

    public interface OnClickListener{
        void onClick(View view);
    }

    public DeviceMgrHomeItemView(Context context) {
        super(context);
        init(context, null);
    }

    public DeviceMgrHomeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_device_mgr_home_item, this, true);
        TextView labelTv = view.findViewById(R.id.tv_item_label);
        TextView hintTv = view.findViewById(R.id.tv_item_hint);

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.DeviceMgrHomeItemView);
        String label = styledAttrs.getString(R.styleable.DeviceMgrHomeItemView_label);
        String hint = styledAttrs.getString(R.styleable.DeviceMgrHomeItemView_hint);
        styledAttrs.recycle();

        labelTv.setText(label);
        hintTv.setText(hint);

        setBackgroundResource(R.drawable.bg_btn_rect_transparent_pressed_selector);
        setClickable(true);
        this.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mOnClickListener != null){
                    mOnClickListener.onClick(v);
                }
            }
        });
    }

    public void setOnClickListener(OnClickListener listener){
        mOnClickListener = listener;
    }
}
