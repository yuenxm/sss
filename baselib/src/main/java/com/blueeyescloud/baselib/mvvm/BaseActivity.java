package com.blueeyescloud.baselib.mvvm;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.blueeyescloud.baselib.utils.StatusBarUtil;

public abstract class BaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel>  extends AppCompatActivity {
    protected VM viewModel;
    protected DB binding;

    protected AbstractTipsView mTipsView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.init(this);
        viewModel = initViewModel();
        initVMObserver();
        binding = (DB)DataBindingUtil.setContentView(this, getLayoutId());
        binding.setLifecycleOwner(this);
        initView();
        initData(this);
    }

    private VM initViewModel(){
        VM viewModel = createViewModel();
        return viewModel;
    }

    /**
     * 监听BaseViewModel里的LiveData
     */
    private void initVMObserver(){
        if(viewModel == null){
            return;
        }
        viewModel.loadingLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isShow) {
                if(mTipsView == null){
                    return;
                }
                if(isShow){
                    mTipsView.showLoading();
                }else{
                    mTipsView.hideLoading();
                }
            }
        });
        viewModel.errorLiveData.observe(this, new Observer<ErrorInfo>() {
            @Override
            public void onChanged(ErrorInfo errorInfo) {
                if(mTipsView == null){
                    return;
                }
                mTipsView.showError(errorInfo);
            }
        });
        viewModel.emptyDataLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isShow) {
                if(mTipsView == null){
                    return;
                }
                if(isShow){
                    mTipsView.showEmptyData();
                }else{
                    mTipsView.hideEmptyData();//TODO 没必要？
                }
            }
        });
    }

    public void addTipsView(ViewGroup parentVg, AbstractTipsView tipsView){
        if (parentVg != null) {
            mTipsView = tipsView;
            mTipsView.setOnReTryClickListener(new ITipsView.OnReTryClickListener() {
                @Override
                public void onRetry() {
                    mTipsView.hideAll();
                    onRetryClicked();
                }
            });
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            parentVg.addView(tipsView, params);
            //先隐藏再后面调用再显示
            mTipsView.setVisibility(View.GONE);
        }
    }

    /**
     * 指定布局资源
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 创建viewModel
     * @return
     */
    protected abstract VM createViewModel();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData(Context context);

    /**
     * 网络异常重试
     */
    protected abstract void onRetryClicked();

}
