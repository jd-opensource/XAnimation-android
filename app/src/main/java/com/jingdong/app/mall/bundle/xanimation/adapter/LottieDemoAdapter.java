package com.jingdong.app.mall.bundle.xanimation.adapter;

import android.content.Context;

import com.jingdong.app.mall.bundle.xanimation.app.R;
import com.jingdong.app.mall.bundle.xanimation.bean.LottieDemo;
import com.jingdong.app.mall.bundle.xanimation.common.CommonRecyclerViewAdapter;
import com.jingdong.app.mall.bundle.xanimation.common.CommonViewHolder;
import com.jingdong.app.mall.bundle.xanimation.viewholder.LottieDemoViewHolder;


public class LottieDemoAdapter extends CommonRecyclerViewAdapter<LottieDemo> {

    public LottieDemoAdapter(Context context) {
        super(context, R.layout.layout_lottie_demo);
    }

    @Override
    public void convert(CommonViewHolder holder, LottieDemo item, OnItemClickListener mItemClickListener) {
        LottieDemoViewHolder lottieDemoViewHolder = new LottieDemoViewHolder(holder.itemView);
        lottieDemoViewHolder.setDataAndShow(item);
    }
}
