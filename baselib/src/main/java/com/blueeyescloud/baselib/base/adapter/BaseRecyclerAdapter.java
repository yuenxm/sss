package com.blueeyescloud.baselib.base.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerView的基类adapter
 * Created by caill on 2016/7/19.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerHolder>{
    protected List<T> mData;
    protected OnItemClickListener listener;
    protected int mLayoutId;

    public interface OnItemClickListener {
        void onItemClick(View view, Object data, int position);
    }

    public BaseRecyclerAdapter(List<T> data, int layoutId){
        mData = data;
        mLayoutId = layoutId;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new RecyclerHolder(root);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        convert(holder, mData.get(position), position);
        holder.itemView.setOnClickListener(getOnClickListener(position));
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public abstract void convert(RecyclerHolder holder, T item, int position);

    public View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null && v != null) {
                    listener.onItemClick(v, mData.get(position), position);
                }
            }
        };
    }
}
