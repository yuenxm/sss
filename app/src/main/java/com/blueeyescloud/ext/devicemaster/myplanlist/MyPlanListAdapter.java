package com.blueeyescloud.ext.devicemaster.myplanlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.blueeyescloud.baselib.mvvm.adapter.OnItemClickListener;
import com.blueeyescloud.ext.R;
import com.blueeyescloud.ext.devicemaster.entity.MasterPlanEntity;

public class MyPlanListAdapter extends PagedListAdapter<MasterPlanEntity, MyPlanListAdapter.MyPlanListViewHolder> {

    protected MyPlanListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MyPlanListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plan_list, parent,false);
        return new MyPlanListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPlanListViewHolder holder, final int position) {
        MasterPlanEntity planEntity = getItem(position);
        holder.planNameTv.setText(planEntity.getName());
        holder.planImeiTv.setText("imei:" + planEntity.getAttribute().getImei());

        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(getItem(position), position);
                }
            });
        }
    }

    class MyPlanListViewHolder extends RecyclerView.ViewHolder{
        private TextView planNameTv;
        private TextView planImeiTv;

        public MyPlanListViewHolder(@NonNull View itemView) {
            super(itemView);
            planNameTv = itemView.findViewById(R.id.tv_plan_name);
            planImeiTv = itemView.findViewById(R.id.tv_plan_imei);
        }
    }

    private static DiffUtil.ItemCallback<MasterPlanEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<MasterPlanEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull MasterPlanEntity oldItem, @NonNull MasterPlanEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MasterPlanEntity oldItem, @NonNull MasterPlanEntity newItem) {
            return oldItem.equals(newItem);
        }
    };

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }
}
