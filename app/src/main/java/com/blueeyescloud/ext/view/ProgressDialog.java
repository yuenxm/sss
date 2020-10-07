package com.blueeyescloud.ext.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blueeyescloud.ext.R;

/**
 * 进度弹窗
 */
public class ProgressDialog extends Dialog {
    private boolean mShownCloseIcon = false;
    private DialogCancelListener mDialogCancelListener;

    public ProgressDialog(@NonNull Context context){
        super(context);
    }

//    public ProgressDialog(@NonNull Context context, boolean shownCloseIcon) {
//        super(context);
//        this.mShownCloseIcon = shownCloseIcon;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_device_progress);
//
//        setCancelable(false);
//        ViewGroup closeVg = findViewById(R.id.llyt_close_layout);
//        closeVg.setOnClickListener(new NoMultiClickListener() {
//            @Override
//            public void onNewClick(View v) {
//                if(mDialogCancelListener != null){
//                    mDialogCancelListener.onClick();
//                }
//                dismiss();
//            }
//        });
//        closeVg.setVisibility(mShownCloseIcon ? View.VISIBLE : View.GONE);
//    }
//
//    public void setDialogCancelListener(DialogCancelListener listener){
//        mDialogCancelListener = listener;
//    }

    public interface DialogCancelListener{
        void onClick();
    }

    public static class Builder{
        private Context context;
        private String message;
        private boolean showCloseIcon = false;
        private DialogCancelListener dialogCancelListener;
        private boolean cancelable = false;

        public Builder(Context context){
            this.context = context;
        }

        public Builder setMessage(String message){
            this.message = message;
            return this;
        }

        public Builder setShownCloseIcon(boolean shownCloseIcon){
            this.showCloseIcon = shownCloseIcon;
            return this;
        }

        public Builder setDialogCancelListener(DialogCancelListener listener){
            this.dialogCancelListener = listener;
            return this;
        }

        public Builder  setCancelable(boolean cancelable){
            this.cancelable = cancelable;
            return this;
        }

        public ProgressDialog create(){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ProgressDialog dialog = new ProgressDialog(context);
            View layout = inflater.inflate(R.layout.dialog_device_progress, null);
            dialog.setContentView(layout);
            dialog.setCancelable(cancelable);

            if(!TextUtils.isEmpty(message)) {
                TextView messageTv = layout.findViewById(R.id.tv_message);
                messageTv.setText(message);
            }
            ViewGroup closeVg = layout.findViewById(R.id.llyt_close_layout);
            closeVg.setOnClickListener(new NoMultiClickListener() {
                @Override
                public void onNewClick(View v) {
                    if(dialogCancelListener != null){
                        dialogCancelListener.onClick();
                    }
                    dialog.dismiss();
                }
            });
            closeVg.setVisibility(showCloseIcon ? View.VISIBLE : View.GONE);

            return dialog;
        }
    }
}
