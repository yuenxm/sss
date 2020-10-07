package com.blueeyescloud.baselib.mvvm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {
    protected VM viewModel;
    protected DB binding;
    protected FragmentActivity mActivity;

    protected AbstractTipsView mTipsView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = initViewModel();
        initVMObserver();
        binding = (DB)DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
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
        viewModel.loadingLiveData.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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
        viewModel.errorLiveData.observe(getViewLifecycleOwner(), new Observer<ErrorInfo>() {
            @Override
            public void onChanged(ErrorInfo errorInfo) {
                if(mTipsView == null){
                    return;
                }
                mTipsView.showError(errorInfo);
            }
        });
        viewModel.emptyDataLiveData.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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
     * 初始化根布局
     *
     * @return 布局layout的id
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
    protected abstract void initData();

    /**
     * 网络异常重试
     */
    protected abstract void onRetryClicked();

}
