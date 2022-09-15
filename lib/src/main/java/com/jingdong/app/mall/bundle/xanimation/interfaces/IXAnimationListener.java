package com.jingdong.app.mall.bundle.xanimation.interfaces;

import android.animation.Animator;

import com.airbnb.lottie.LottieComposition;

public interface IXAnimationListener {
    default void onAnimationStart(Animator animation) {
    }

    default void onAnimationEnd(Animator animation) {
    }

    default void onAnimationCancel(Animator animation) {
    }

    default void onAnimationRepeat(Animator animation) {
    }

    default void onLottieLoadResult(LottieComposition composition) {
    }

    default void onLayerStatusListener(String layerName, int status, int layerCount) {
    }

    default void onAnimationUpdate(float value) {
    }

    default void onAnimationError(Throwable throwable) {
    }
}
