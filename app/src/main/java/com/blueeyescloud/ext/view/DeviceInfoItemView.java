package com.blueeyescloud.ext.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.blueeyescloud.ext.R;

public class DeviceInfoItemView extends RelativeLayout {
    TextView labelTv;
    EditTextEx infoEt;

    private DeviceMgrHomeItemView.OnClickListener mOnClickListener;

    public DeviceInfoItemView(Context context) {
        super(context);
        init(context, null);
    }

    public DeviceInfoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_device_info_item, this, true);
        labelTv = view.findViewById(R.id.tv_item_label);
        infoEt = view.findViewById(R.id.et_item_info);

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.DeviceInfoItemView);
        String label = styledAttrs.getString(R.styleable.DeviceInfoItemView_label);
        String info = styledAttrs.getString(R.styleable.DeviceInfoItemView_info);
        boolean editable = styledAttrs.getBoolean(R.styleable.DeviceInfoItemView_editable, true);
        styledAttrs.recycle();

        labelTv.setText(label);
        infoEt.setText(info);
        infoEt.setEnabled(editable ? true : false);

        setBackgroundResource(R.color.white);
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

    public void setOnClickListener(DeviceMgrHomeItemView.OnClickListener listener){
        mOnClickListener = listener;
    }

    public void setLabel(String label){
        labelTv.setText(label);
    }

    public void setInfo(String info){
        infoEt.setText(info);
    }
    public void setEditable(boolean editable){
        infoEt.setEnabled(editable);
    }

    @BindingAdapter("label")
    public static void setLabel(DeviceInfoItemView view, String label){
        view.setLabel(label);
    }

    @BindingAdapter("editable")
    public static void setEditable(DeviceInfoItemView view, boolean editable){
        view.setEditable(editable);
    }

    @BindingAdapter("info")
    public static void setInfo(DeviceInfoItemView deviceInfoItemView, String text){
        deviceInfoItemView.setInfo(text);
    }

    public String getInfo(){
        return infoEt.getText().toString();
    }

    //设置数据双向绑定
    @InverseBindingAdapter(attribute = "info", event = "textAttrChanged")
    public static String getInfo(DeviceInfoItemView deviceInfoItemView){
        return deviceInfoItemView.getInfo();
    }

    @BindingAdapter(value = "textAttrChanged", requireAll = false)
    public static void setChangeListener(final DeviceInfoItemView deviceInfoItemView, final InverseBindingListener listener){
        deviceInfoItemView.addTextChangedListener(new TextWatcher() {
            String text;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(text != editable.toString()){
                    if(listener != null) {
                        listener.onChange();
                    }
                    text = editable.toString();
                  //  deviceInfoItemView.setSelection(text.length());
                }
            }
        });
    }

    public void addTextChangedListener(TextWatcher watcher){
        infoEt.addTextChangedListener(watcher);
    }

    public void setSelection(int position){
        infoEt.setSelection(position);
    }
}
