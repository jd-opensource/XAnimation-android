package com.jingdong.app.mall.bundle.xanimation.common;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;

public class CommonViewHolder extends RecyclerView.ViewHolder {
    // 用来存放子View减少findViewById的次数
    private SparseArray<View> mViews;

    public CommonViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    /**
     * 设置TextView文本
     */
    public CommonViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public CommonViewHolder setTextColor(int viewId, @ColorInt int color) {
        TextView tv = getView(viewId);
        tv.setTextColor(color);
        return this;
    }

    public CommonViewHolder setTextSize(int viewId, float size) {
        TextView tv = getView(viewId);
        tv.setTextSize(size);
        return this;
    }

    /**
     * 通过id获取view
     */
    public <T extends View> T getView(int viewId) {
        // 先从缓存中找
        View view = mViews.get(viewId);
        if (view == null) {
            // 直接从ItemView中找
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置View的Visibility
     */
    public CommonViewHolder setViewVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    /**
     * 设置ImageView的资源
     */
    public CommonViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    /**
     * 设置条目点击事件
     */
    public void setOnItemClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }

    /**
     * 设置条目长按事件
     */
    public void setOnItemLongClickListener(View.OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
    }

}
