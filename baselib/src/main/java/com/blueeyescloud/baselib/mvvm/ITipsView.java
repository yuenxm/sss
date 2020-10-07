package com.blueeyescloud.baselib.mvvm;

public interface ITipsView {
    /**
     * 加载非透明的进度条
     */
    void showLoading();

    /**
     * 隐藏非透明的进度条
     */
    void hideLoading();

    /**
     * 显示错误贴图（网络错误或者数据错误）
     */
    void showError(ErrorInfo errorInfo);

    /**
     * 隐藏错误贴图（网络错误或者数据错误）
     */
    void hideError();

    /**
     * 隐藏错误贴图
     */
    void showEmptyData();


    /**
     * 隐藏无数据贴图
     */
    void hideEmptyData();

    /**
     * 隐藏所有的进度条或者错误提示
     */
    void hideAll();

    public interface OnReTryClickListener{
        void onRetry();
    }
}
