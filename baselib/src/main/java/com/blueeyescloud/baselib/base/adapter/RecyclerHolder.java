package com.blueeyescloud.baselib.base.adapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView的ViewHolder
 * Created by caill on 2016/7/19.
 */
public class RecyclerHolder extends RecyclerView.ViewHolder{
    private SparseArray<View> mViews = new SparseArray<>();

    public RecyclerHolder(View itemView) {
        super(itemView);

    }

    public <T extends View> T getView(int id){
        View view = mViews.get(id);
        if (view == null){
            view = itemView.findViewById(id);
            mViews.put(id, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public RecyclerHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public RecyclerHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }
}
