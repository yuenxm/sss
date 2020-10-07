package com.blueeyescloud.ext.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blueeyescloud.baselib.mvvm.ErrorInfo;
import com.blueeyescloud.baselib.mvvm.ITipsView;
import com.blueeyescloud.ext.R;

public class ErrorStatusView extends RelativeLayout {
    private ViewGroup mNoNetworkVg;
    private ViewGroup mServerErrorVg;
    private Button mRetryButton;
    private TextView mErrorMsgTv;

    public ErrorStatusView(Context context) {
        this(context, null);
    }

    public ErrorStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_error_status, this, true);
        mNoNetworkVg = contentView.findViewById(R.id.llyt_no_network_layout);
        mServerErrorVg = contentView.findViewById(R.id.llyt_server_error_layout);
        mRetryButton = contentView.findViewById(R.id.btn_retry);
        mErrorMsgTv = contentView.findViewById(R.id.tv_error_msg);
        ((RelativeLayout.LayoutParams)mServerErrorVg.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT);
        mRetryButton.setOnClickListener(new NoMultiClickListener() {
            @Override
            public void onNewClick(View v) {
                if(mOnReTryClickListener != null){
                    mOnReTryClickListener.onRetry();
                }
            }
        });
    }

    public void showError(ErrorInfo errorInfo){
        if(true){//!NetworkUtils.isConnected(getContext()) || errorInfo.getErrorType() == ErrorType.NO_NETWORK){ //todo for testing
            mNoNetworkVg.setVisibility(View.VISIBLE);
            mServerErrorVg.setVisibility(View.GONE);
        }else{
//            mErrorMsgTv.setText(errorInfo.getMessage());
            mNoNetworkVg.setVisibility(View.GONE);
            mServerErrorVg.setVisibility(View.VISIBLE);
        }
    }

    private ITipsView.OnReTryClickListener mOnReTryClickListener;

    public void setOnReTryClickListener(ITipsView.OnReTryClickListener listener){
        this.mOnReTryClickListener = listener;
    }
}
