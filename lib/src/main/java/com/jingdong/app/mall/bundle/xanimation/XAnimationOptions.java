package com.jingdong.app.mall.bundle.xanimation;

import android.view.View;

public class XAnimationOptions {
    // 原生控件
    View targetView;
    // Lottie动效链接
    String lottieUrl;
    // 图层名称
    String layerName;
    // 动画播放次数，-1: 循环播放，0：执行1次
    int repeatCount = 0;
    float speed = 1f;
    /**
     * 0: 指定原生控件执行动画
     * 1: 指定原生容器里子控件执行动画
     * 2: 指定json图层动画
     * 3: 监听json动画进度
     */
    int animationType = -1;
    // 原生控件 id String
    String resourceEntryName;

    public XAnimationOptions withTargetView(View targetView) {
        this.targetView = targetView;
        return this;
    }

    public XAnimationOptions withLottieUrl(String lottieUrl) {
        this.lottieUrl = lottieUrl;
        return this;
    }

    public XAnimationOptions withLayerName(String layerName) {
        this.layerName = layerName;
        return this;
    }

    public XAnimationOptions withRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
        return this;
    }

    public XAnimationOptions withSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public XAnimationOptions withAnimationType(int animationType) {
        this.animationType = animationType;
        return this;
    }

    public XAnimationOptions withResourceEntryName(String resourceEntryName) {
        this.resourceEntryName = resourceEntryName;
        return this;
    }
}
