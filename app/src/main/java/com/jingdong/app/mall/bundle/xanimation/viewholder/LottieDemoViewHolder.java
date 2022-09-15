package com.jingdong.app.mall.bundle.xanimation.viewholder;

import android.view.View;

import com.jingdong.app.mall.bundle.xanimation.app.R;
import com.jingdong.app.mall.bundle.xanimation.bean.LottieDemo;
import com.jingdong.app.mall.bundle.xanimation.common.CommonViewHolder;


public class LottieDemoViewHolder extends CommonViewHolder {

    public LottieDemoViewHolder(View itemView) {
        super(itemView);
    }

    public void setDataAndShow(LottieDemo lottieDemo) {
        if (lottieDemo != null) {
            setText(R.id.tv_name, lottieDemo.getName());
        } else {
            itemView.setVisibility(View.GONE);
        }
    }
}
