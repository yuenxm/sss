package com.blueeyescloud.ext.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextEx extends EditText {

    public EditTextEx(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EditTextEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextEx(Context context) {
        super(context);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        //保证光标始终在最后面
        if(selStart==selEnd){//防止不能多选
            setSelection(getText().length());
        }

    }
}
