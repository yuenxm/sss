package com.blueeyescloud.baselib.mvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BaseBindingRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private List<T> mList;
    private int mLayoutId;
    private int mVariableId;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public BaseBindingRecyclerAdapter(List<T> list, int layoutId, int variableId){
        this.mList = list;
        this.mLayoutId = layoutId;
        this.mVariableId = variableId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, mLayoutId, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final T item = mList.get(position);
        holder.getViewDataBinding().setVariable(mVariableId, item);
        holder.getViewDataBinding().executePendingBindings();
        onBindViewHolder(item, holder.getViewDataBinding(), position);
        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(item, position);
                }
            });
        }
        if(mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mOnItemLongClickListener.onItemLongClick(item, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * 绑定数据
     */
    protected void onBindViewHolder(T data, ViewDataBinding binding, int position) {

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        mOnItemLongClickListener = listener;
    }

    public void setData(List<T> data){
        this.mList.clear();
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<T> data){
        this.mList.addAll(data);
        notifyDataSetChanged();
    }
}
