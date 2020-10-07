package com.blueeyescloud.ext.home;

import android.content.Intent;
import android.net.Uri;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blueeyescloud.baselib.mvvm.BaseFragment;
import com.blueeyescloud.baselib.mvvm.adapter.OnItemClickListener;
import com.blueeyescloud.baselib.utils.ToastUtils;
import com.blueeyescloud.ext.R;
import com.blueeyescloud.ext.databinding.FragmentExtHomeBinding;
import com.blueeyescloud.ext.devicemaster.app.AppViewModelFactory;
import com.blueeyescloud.ext.view.TipsView;

import java.util.List;

public class FragmentExtHome extends BaseFragment<FragmentExtHomeBinding,HomeServiceViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ext_home;
    }

    @Override
    protected HomeServiceViewModel createViewModel() {
        return new ViewModelProvider(this,
                AppViewModelFactory.getInstance(mActivity.getApplication()))
                .get(HomeServiceViewModel.class);
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);

        addTipsView(binding.rlytContentParent, new TipsView(mActivity));
    }

    @Override
    protected void initData() {
        binding.setViewModel(viewModel);
        viewModel.serviceListLiveData.observe(this, new Observer<List<ServiceResEntity>>() {
            @Override
            public void onChanged(List<ServiceResEntity> serviceResEntities) {
                HomServiceRecyclerAdapter adapter = new HomServiceRecyclerAdapter(serviceResEntities);
                adapter.setOnItemClickListener(new OnItemClickListener<ServiceResEntity>() {
                    @Override
                    public void onItemClick(ServiceResEntity item, int position) {
                        if(item.getGranted()) {
                            String uri = item.getUri();
                            Intent intent = new Intent();
                            intent.setData(Uri.parse(uri));
                            startActivity(intent);
                        }else{
                            ToastUtils.show(mActivity, "无权限");
                        }
                    }
                });
                binding.recyclerView.setAdapter(adapter);
            }
        });
        viewModel.loadData();
    }

    @Override
    protected void onRetryClicked() {
        viewModel.loadData();
    }
}
