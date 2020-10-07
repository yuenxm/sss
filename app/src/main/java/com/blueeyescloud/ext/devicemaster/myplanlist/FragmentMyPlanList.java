package com.blueeyescloud.ext.devicemaster.myplanlist;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blueeyescloud.baselib.mvvm.BaseFragment;
import com.blueeyescloud.baselib.mvvm.adapter.OnItemClickListener;
import com.blueeyescloud.ext.R;
import com.blueeyescloud.ext.databinding.FragmentMyPlanListBinding;
import com.blueeyescloud.ext.devicemaster.app.AppViewModelFactory;
import com.blueeyescloud.ext.devicemaster.entity.MasterPlanEntity;
import com.blueeyescloud.ext.devicemaster.plan.ui.ActivityPlan;
import com.blueeyescloud.ext.devicemaster.plan.PlanConstants;
import com.blueeyescloud.ext.view.TipsView;

public class FragmentMyPlanList extends BaseFragment<FragmentMyPlanListBinding, MyPlanListViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_plan_list;
    }

    @Override
    protected MyPlanListViewModel createViewModel() {
        return new ViewModelProvider(mActivity,
                AppViewModelFactory.getInstance(mActivity.getApplication()))
                .get(MyPlanListViewModel.class);
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.bg_plan_list_divider));
        binding.recyclerView.addItemDecoration(itemDecoration);
//        binding.recyclerView.setHasFixedSize(true);

        addTipsView(binding.rlytContentParent, new TipsView(mActivity));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    @Override
    protected void initData() {
        viewModel.listLoadingLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                //没有数据的时候，加载使用中间居中的加载圈。有数据了，使用swipeRefreshLayout的加载圈
                if(loading) {
                    if (binding.recyclerView.getAdapter().getItemCount() == 0) {
                        if (mTipsView != null) {
                            mTipsView.showLoading();
                        }
                    }
                }else{
                    if (mTipsView != null) {
                        mTipsView.hideLoading();
                    }
                    binding.swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        //加载页面数据
        final MyPlanListAdapter adapter = new MyPlanListAdapter();
        viewModel.planPagedList.observe(mActivity, new Observer<PagedList<MasterPlanEntity>>() {
            @Override
            public void onChanged(PagedList<MasterPlanEntity> masterPlanEntities) {
                Log.e("tag_ss", "planPagedList onChanged list = " + masterPlanEntities.size());
                adapter.submitList(masterPlanEntities);
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener<MasterPlanEntity>() {
            @Override
            public void onItemClick(MasterPlanEntity item, int position) {
                Intent intent = new Intent(mActivity, ActivityPlan.class);
                intent.putExtra(PlanConstants.Param.SOURCE_TYPE, PlanConstants.SourceType.MY_PLAN_LIST_ITEM);
                intent.putExtra(PlanConstants.Param.PLAN_INFO, item);
                //Note:如果下面调用mActivity.startActivityForResult，只有Activity的onActivityForResult会响应。
                //如果是fragment.startActivityForResult，fragment的onActivityResult先响应，然后activity的onActivityForResult也会响应
                //为了让fragment的onActivityResult收到消息，这里不用mActivity.startActivityForResult
                startActivityForResult(intent, PlanConstants.ActivityRequestCode.SHOW_PLAN_DETAIL);
            }
        });
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onRetryClicked() {
        refresh();
    }

    public void refresh(){
        viewModel.refresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PlanConstants.ActivityRequestCode.SHOW_PLAN_DETAIL
                && resultCode == PlanConstants.ActivityResultCode.NEED_UPDATE){
            refresh();
        }
    }
}
