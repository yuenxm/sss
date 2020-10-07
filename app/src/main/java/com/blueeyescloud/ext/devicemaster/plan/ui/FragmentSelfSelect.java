package com.blueeyescloud.ext.devicemaster.plan.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.blueeyescloud.baselib.mvvm.BaseFragment;
import com.blueeyescloud.baselib.utils.ToastUtils;
import com.blueeyescloud.ext.R;
import com.blueeyescloud.ext.databinding.FragmentDeviceSelfSelectBinding;
import com.blueeyescloud.ext.devicemaster.app.AppViewModelFactory;
import com.blueeyescloud.ext.devicemaster.plan.PlanConstants;
import com.blueeyescloud.ext.devicemaster.plan.util.FragmentNavigationUtil;
import com.blueeyescloud.ext.devicemaster.plan.viewmodel.SelfSelectViewModel;
import com.blueeyescloud.ext.view.TipsView;
import com.blueeyescloud.ext.view.NoMultiClickListener;
import com.blueeyescloud.ext.view.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FragmentSelfSelect extends BaseFragment<FragmentDeviceSelfSelectBinding, SelfSelectViewModel> {
    private int mSelectedGroupPosition; //第几个分组
    private int mSelectedChildPosition; //分组里的第几个
    private String mSelectedModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_device_self_select;
    }

    @Override
    protected SelfSelectViewModel createViewModel() {
        return new ViewModelProvider(getActivity(),
                AppViewModelFactory.getInstance(mActivity.getApplication()))
                .get(SelfSelectViewModel.class);
    }

    @Override
    protected void initView() {
        binding.setOnBackLisener(new TitleBar.OnBackClickListener() {
            @Override
            public void onBackClick() {
                FragmentNavigationUtil.back(mActivity);
            }
        });
        binding.setClickListener(new OnClickListener());
        binding.setTitleName(getString(R.string.device_plan_title_device_self_select));

        addTipsView(binding.flytContent, new TipsView(mActivity));
    }

    @Override
    protected void initData() {
        viewModel.modelMapLiveData.observe(this, new Observer<Map<String, List<String>>>() {
            @Override
            public void onChanged(Map<String, List<String>> stringListMap) {
                initListView(stringListMap);
            }
        });
        viewModel.loadData();
    }

    @Override
    protected void onRetryClicked() {
        viewModel.loadData();
    }

    private static final String BRAND = "brand";
    private static final String MODEL = "model";

    private List<List<Map<String, String>>> initChildData(Map<String, List<String>> stringListMap){
        List<List<Map<String, String>>> list = new ArrayList<>();
        List<Map<String, String>> child;
        Map<String, String> map;
        Set<Map.Entry<String, List<String>>> entrySet = stringListMap.entrySet();
        for(Map.Entry<String, List<String>> entry : entrySet){
            child = new ArrayList<>();
            List<String> models = entry.getValue();
            int column = models.size();
            for(int i = 0; i < column; i++){
                map = new HashMap<>();
                map.put(MODEL, models.get(i));
                child.add(map);
            }
            list.add(child);
        }
        return list;
    }

    private List<Map<String, String>> initGroupData(Map<String, List<String>> stringListMap){
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map;
        Set<String> keySet = stringListMap.keySet();
        for(String key : keySet){
            map = new HashMap<>();
            map.put(BRAND, key);
            list.add(map);
        }
        return list;
    }

    private void initListView(Map<String, List<String>> stringListMap){
        List<Map<String, String>> brands = initGroupData(stringListMap);
        List<List<Map<String, String>>> models = initChildData(stringListMap);
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(mActivity,
                brands, R.layout.item_model_self_select_brand, new String[]{BRAND}, new int[]{R.id.tv_self_select_brand},
                models, R.layout.item_model_self_select_model, new String[]{MODEL},
                new int[]{R.id.cb_select});
        binding.listview.setAdapter(adapter);

        //默认title域展开
        for(int i = 0; i < brands.size(); i++){
            binding.listview.expandGroup(i);
        }
        binding.listview.setGroupIndicator(null); //去掉指示头

        binding.listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // 返回 true，分组不会展开
                return true;
            }
        });

        //设置子选项点击监听事件
        binding.listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            private CheckBox prevSelectedView;
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
                if(prevSelectedView != null){
                    prevSelectedView.toggle();
                }
                prevSelectedView = view.findViewById(R.id.cb_select);
                prevSelectedView.setChecked(true);

                mSelectedGroupPosition = groupPosition;
                mSelectedChildPosition = childPosition;
                mSelectedModel = prevSelectedView.getText().toString();
                Log.e("tag_ss", "mSelectedModel = " + mSelectedModel);

                return true;
            }
        });
    }


    public class OnClickListener extends NoMultiClickListener {

        public void onNewClick(View view){
            switch (view.getId()){
                case R.id.btn_next_step:
                    if(checkValid()) {
                        nextStep();
                    }
                    break;
            }
        }
    }

    private boolean checkValid(){
        if(TextUtils.isEmpty(mSelectedModel)){
            ToastUtils.show(mActivity, "请选择一个型号");
            return false;
        }
        return true;
    }

    private void nextStep(){
        FragmentPlan fragmentPlan = new FragmentPlan();
        Bundle bundle = new Bundle();
        bundle.putInt(PlanConstants.Param.SOURCE_TYPE, PlanConstants.SourceType.SELF_SELECTED_PHONE);
        bundle.putString(PlanConstants.Param.PHONE_MODEL, mSelectedModel);
        fragmentPlan.setArguments(bundle);
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        transaction.hide(this);
        transaction.add(R.id.flyt_content, fragmentPlan);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
