package com.jingdong.app.mall.bundle.xanimation.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonRecyclerViewAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mData;
    private int mLayoutId;
    private boolean hasHeaderAndFooterLayout = false;
    // 多布局支持
    private MultiTypeSupport mMultiTypeSupport;

    public CommonRecyclerViewAdapter(Context context, List<T> data, int layoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mLayoutId = layoutId;
        this.mData = data;
    }

    public CommonRecyclerViewAdapter(Context context, int layoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mLayoutId = layoutId;
        this.mData = new ArrayList<>();
    }

    public void setData(List<T> data) {
        if (data != null) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void reSetData(List<T> data) {
        if (data != null) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        if (mData != null) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 多布局支持
     */
    public CommonRecyclerViewAdapter(Context context, List<T> data, MultiTypeSupport<T> multiTypeSupport) {
        this(context, data, -1);
        this.mMultiTypeSupport = multiTypeSupport;
    }

    public CommonRecyclerViewAdapter(Context context, MultiTypeSupport<T> multiTypeSupport) {
        this(context, -1);
        this.mMultiTypeSupport = multiTypeSupport;
    }

    /**
     * 根据当前位置获取不同的viewType
     */
    @Override
    public int getItemViewType(int position) {
        // 多布局支持
        if (mMultiTypeSupport != null) {
            return mMultiTypeSupport.getLayoutId(mData.get(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 多布局支持
        if (mMultiTypeSupport != null) {
            mLayoutId = viewType;
        }
        // 先inflate数据
        View itemView = mInflater.inflate(mLayoutId, parent, false);
        // 返回ViewHolder
        CommonViewHolder holder = new CommonViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> mItemClickListener.onItemClick(position));
        }
        convert(holder, hasHeaderAndFooterLayout ? mData.get(position -1 ) : mData.get(position), mItemClickListener);
    }

    /**
     * 利用抽象方法回传出去，每个不一样的Adapter去设置
     *
     * @param item 当前的数据
     */
    public abstract void convert(CommonViewHolder holder, T item, OnItemClickListener mItemClickListener);

    @Override
    public int getItemCount() {
        if (hasHeaderAndFooterLayout) {
            return mData.size() + 2;
        } else {
            return mData.size();
        }
    }

    public List<T> getData() {
        return mData;
    }

    public void setmData(List<T> mData) {
        this.mData = mData;
    }

    public OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setHasHeaderAndFooterLayout(boolean hasHeaderAndFooterLayout) {
        this.hasHeaderAndFooterLayout = hasHeaderAndFooterLayout;
    }
}
