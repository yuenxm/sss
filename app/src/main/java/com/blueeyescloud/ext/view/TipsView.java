package com.blueeyescloud.ext.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blueeyescloud.baselib.mvvm.AbstractTipsView;
import com.blueeyescloud.baselib.mvvm.ErrorInfo;
import com.blueeyescloud.baselib.mvvm.ITipsView;
import com.blueeyescloud.ext.R;

public class TipsView extends AbstractTipsView {
    private ErrorStatusView mErrorView;
    private ViewGroup mEmptyView;
    private ViewGroup mLoadingView;

    public TipsView(Context context) {
        super(context);
        init(context, null,0);
    }

    public TipsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs,0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_tips_view, this, true);
        mLoadingView = view.findViewById(R.id.loading_view);
        mErrorView = view.findViewById(R.id.error_view);
        mEmptyView = view.findViewById(R.id.empty_view);

        mLoadingView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);

        setBackgroundColor(getResources().getColor(R.color.page_bg_color));
    }

    @Override
    public void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoadingView.setVisibility(View.GONE);
        setVisibility(View.GONE);
    }

    @Override
    public void showError(ErrorInfo errorInfo) {
        mLoadingView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.showError(errorInfo);
        setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        mErrorView.setVisibility(View.GONE);
        setVisibility(View.GONE);
    }

    @Override
    public void showEmptyData() {
        mLoadingView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
        setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyData() {
        mEmptyView.setVisibility(View.GONE);
        setVisibility(View.GONE);
    }

    @Override
    public void hideAll() {
        setVisibility(View.GONE);
    }

    public static void attachTipView(TipsView tipsView, ViewGroup viewGroup) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        if (viewGroup != null) {
            viewGroup.addView(tipsView, params);
        }
    }

    public void setOnReTryClickListener(ITipsView.OnReTryClickListener listener){
        mErrorView.setOnReTryClickListener(listener);
    }
}
