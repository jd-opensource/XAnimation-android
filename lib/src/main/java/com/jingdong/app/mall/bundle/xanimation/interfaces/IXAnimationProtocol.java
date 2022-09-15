package com.jingdong.app.mall.bundle.xanimation.interfaces;

import android.content.Context;

import com.jingdong.app.mall.bundle.xanimation.XAnimationOptions;


public interface IXAnimationProtocol {
    void startViewAnimation(Context context, XAnimationOptions xAnimationOptions);

    void setViewAnimationProgress(float progress);

    void pauseViewAnimation();

    void resumeViewAnimation();
}
