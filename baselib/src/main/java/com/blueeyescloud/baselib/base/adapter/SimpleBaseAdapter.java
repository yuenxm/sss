package com.blueeyescloud.baselib.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class SimpleBaseAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mList;

    public SimpleBaseAdapter(List<T> list){
        mList = list;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<T> list){
        mList = list;
        notifyDataSetChanged();
    }
}
