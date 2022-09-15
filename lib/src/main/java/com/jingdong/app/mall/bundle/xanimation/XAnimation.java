package com.jingdong.app.mall.bundle.xanimation;

import android.animation.Animator;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;
import com.airbnb.lottie.LottieTask;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.utils.LottieValueAnimator;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.ScaleXY;
import com.jingdong.app.mall.bundle.xanimation.interfaces.IXAnimationListener;
import com.jingdong.app.mall.bundle.xanimation.interfaces.IXAnimationProtocol;
import com.jingdong.app.mall.bundle.xanimation.interfaces.XAnimationType;
import com.jingdong.app.mall.bundle.xanimation.util.ActivityUtil;
import com.jingdong.app.mall.bundle.xanimation.util.AnimationUtil;
import com.jingdong.app.mall.bundle.xanimation.util.StringUtil;
import com.jingdong.app.mall.bundle.xanimation.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XAnimation implements IXAnimationProtocol {
    private static final String TAG = XAnimation.class.getSimpleName();
    private IXAnimationListener mIJDAnimationListener;
    private LottieValueAnimator mLottieValueAnimator;
    private List<LayerAnimation> mLayerAnimationList;

    public XAnimation() {
        mLottieValueAnimator = new LottieValueAnimator();
        mLayerAnimationList = new ArrayList<>();
    }

    @Override
    public void startViewAnimation(Context context, XAnimationOptions xAnimationOptions) {
        if (ActivityUtil.isLiving(context) && xAnimationOptions != null && !StringUtil.strIsNull(xAnimationOptions.lottieUrl)) {
            LottieTask<LottieComposition> lottieTask = xAnimationOptions.lottieUrl.startsWith(XAnimationConstants.HTTP_LINK)
                    ? LottieCompositionFactory.fromUrl(context, xAnimationOptions.lottieUrl)
                    : LottieCompositionFactory.fromAsset(context, xAnimationOptions.lottieUrl);
            lottieTask.addListener(new LottieListener<LottieComposition>() {
                @Override
                public void onResult(LottieComposition composition) {
                    String resourceEntryName = xAnimationOptions.resourceEntryName;
                    String layerName = xAnimationOptions.layerName;
                    int animationType = xAnimationOptions.animationType;

                    // 获取原生控件
                    View targetView;
                    if (!TextUtils.isEmpty(resourceEntryName) && (animationType == XAnimationType.TYPE_ASSIGN_VIEW
                            || animationType == XAnimationType.TYPE_ASSIGN_VIEW_GROUP)) {
                        targetView = ViewUtil.findViewByResourceEntryName(context, resourceEntryName);
                    } else {
                        targetView = xAnimationOptions.targetView;
                    }
                    if (targetView == null) {
                        return;
                    }

                    if (mIJDAnimationListener != null) {
                        mIJDAnimationListener.onLottieLoadResult(composition);
                    }

                    if (mLottieValueAnimator == null) {
                        return;
                    }
                    mLottieValueAnimator.setRepeatCount(xAnimationOptions.repeatCount);
                    mLottieValueAnimator.setSpeed(xAnimationOptions.speed);
                    mLottieValueAnimator.setComposition(composition);
                    mLottieValueAnimator.setFrame(composition.getFrameForProgress(mLottieValueAnimator.getAnimatedFraction()));

                    // 获取图层动画
                    Map<String, List<Layer>> precompsList = composition.getPrecomps();
                    for (Map.Entry<String, List<Layer>> entry : precompsList.entrySet()) {
                        List<Layer> layerList = entry.getValue();
                        collectLayers(layerList, mLayerAnimationList);
                    }

                    List<Layer> layerList = composition.getLayers();
                    collectLayers(layerList, mLayerAnimationList);

                    // 监听整体动画进度
                    mLottieValueAnimator.addUpdateListener(animation -> {
                        if (animation instanceof LottieValueAnimator) {
                            float animatedValueAbsolute = ((LottieValueAnimator) animation).getAnimatedValueAbsolute();
                            if (mIJDAnimationListener != null) {
                                mIJDAnimationListener.onAnimationUpdate(animatedValueAbsolute);
                            }
                            if (mLayerAnimationList == null) {
                                return;
                            }
                            for (int i = 0; i < mLayerAnimationList.size(); i++) {
                                LayerAnimation layerAnimation = mLayerAnimationList.get(i);
                                TransformKeyframeAnimation keyframeAnimation = layerAnimation.getTransformKeyframeAnimation();
                                if (keyframeAnimation != null) {
                                    // 更新动画进度
                                    keyframeAnimation.setProgress(animatedValueAbsolute);
                                }

                                FloatKeyframeAnimation inOutAnimation = layerAnimation.getInOutAnimation();
                                if (inOutAnimation != null) {
                                    // 更新图层进度
                                    inOutAnimation.setProgress(animatedValueAbsolute);
                                }
                            }
                        }
                    });
                    mLottieValueAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            if (mIJDAnimationListener != null) {
                                mIJDAnimationListener.onAnimationStart(animation);
                            }
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (mIJDAnimationListener != null) {
                                mIJDAnimationListener.onAnimationEnd(animation);
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            if (mIJDAnimationListener != null) {
                                mIJDAnimationListener.onAnimationCancel(animation);
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                            if (mIJDAnimationListener != null) {
                                mIJDAnimationListener.onAnimationRepeat(animation);
                            }
                        }
                    });

                    // 监听控件生命周期
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        targetView.getViewTreeObserver().addOnWindowAttachListener(
                                new ViewTreeObserver.OnWindowAttachListener() {
                                    @Override
                                    public void onWindowAttached() {
                                        // Log.d(TAG, "onWindowAttached");
                                    }

                                    @Override
                                    public void onWindowDetached() {
                                        // Log.d(TAG, "onWindowDetached");
                                        destroy();
                                    }
                                });
                    } else {
                        Application applicationContext = (Application) context.getApplicationContext();
                        applicationContext.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                            @Override
                            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                            }

                            @Override
                            public void onActivityStarted(@NonNull Activity activity) {
                            }

                            @Override
                            public void onActivityResumed(@NonNull Activity activity) {
                            }

                            @Override
                            public void onActivityPaused(@NonNull Activity activity) {
                            }

                            @Override
                            public void onActivityStopped(@NonNull Activity activity) {
                            }

                            @Override
                            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
                            }

                            @Override
                            public void onActivityDestroyed(@NonNull Activity activity) {
                                // Log.d(TAG, "onActivityDestroyed");
                                destroy();
                            }
                        });
                    }


                    // 应用动画到原生控件
                    if (targetView instanceof ViewGroup && animationType == XAnimationType.TYPE_ASSIGN_VIEW_GROUP) {
                        if (mLayerAnimationList == null) {
                            return;
                        }
                        ViewGroup targetViewGroup = (ViewGroup) targetView;
                        int childViewCount = targetViewGroup.getChildCount();
                        int animationSize = mLayerAnimationList.size();
                        for (int i = 0; i < Math.min(animationSize, childViewCount); i++) {
                            LayerAnimation layerAnimation = mLayerAnimationList.get(i);
                            View childView = targetViewGroup.getChildAt(animationSize - i - 1);

                            applyKeyframeAnimationToView(childView, layerAnimation.getTransformKeyframeAnimation());
                        }
                    } else {// 当前原生控件
                        applyKeyframeAnimationToView(targetView, AnimationUtil.getAnimationByLayerName(layerName, mLayerAnimationList));
                    }

                    mLottieValueAnimator.playAnimation();
                }
            });
            lottieTask.addFailureListener(new LottieListener<Throwable>() {
                @Override
                public void onResult(Throwable throwable) {
                    throwable.printStackTrace();
                    if (mIJDAnimationListener != null) {
                        mIJDAnimationListener.onAnimationError(throwable);
                    }
                }
            });
        }
    }

    private void collectLayers(List<Layer> layerList, List<LayerAnimation> layerAnimationList) {
        if (layerList != null && !layerList.isEmpty()) {
            for (int i = 0; i < layerList.size(); i++) {
                Layer layer = layerList.get(i);
                if (layer != null) {
                    LayerAnimation layerAnimation = new LayerAnimation();
                    List<Keyframe<Float>> inOutKeyframes = layer.getInOutKeyframes();
                    if (inOutKeyframes != null && !inOutKeyframes.isEmpty()) {
                        FloatKeyframeAnimation inOutAnimation = new FloatKeyframeAnimation(inOutKeyframes);
                        inOutAnimation.addUpdateListener(new BaseKeyframeAnimation.AnimationListener() {
                            @Override
                            public void onValueChanged() {
                                if (mIJDAnimationListener != null) {
                                    mIJDAnimationListener.onLayerStatusListener(layer.getName(), (int) inOutAnimation.getFloatValue(), layerList.size());
                                }
                                //float floatValue = inOutAnimation.getFloatValue();
                                //Log.d(TAG,  layer.getName() + " floatValue: " + floatValue);
                            }
                        });
                        layerAnimation.setInOutAnimation(inOutAnimation);
                    }
                    layerAnimation.setLayer(layer);
                    layerAnimation.setTransformKeyframeAnimation(layer.getTransform().createAnimation());
                    layerAnimationList.add(layerAnimation);
                }
            }
        }
    }

    @Override
    public void setViewAnimationProgress(float progress) {
        if (mLottieValueAnimator == null || mLottieValueAnimator.getComposition() == null) {
            return;
        }
        mLottieValueAnimator.setFrame(mLottieValueAnimator.getComposition().getFrameForProgress(progress));
    }

    @Override
    public void pauseViewAnimation() {
        if (mLottieValueAnimator == null) {
            return;
        }
        mLottieValueAnimator.pauseAnimation();
    }

    @Override
    public void resumeViewAnimation() {
        if (mLottieValueAnimator == null) {
            return;
        }
        mLottieValueAnimator.resumeAnimation();
    }

    private void applyKeyframeAnimationToView(View targetView, TransformKeyframeAnimation keyframeAnimation) {
        if (targetView == null || keyframeAnimation == null) {
            return;
        }
        keyframeAnimation.addListener(() -> {
            //缩放
            BaseKeyframeAnimation<ScaleXY, ScaleXY> scale = keyframeAnimation.getScale();
            if (scale != null) {
                ScaleXY scaleTransform = scale.getValue();
                if (scaleTransform.getScaleX() != 1f || scaleTransform.getScaleY() != 1f) {
                    targetView.setScaleX(scaleTransform.getScaleX());
                    targetView.setScaleY(scaleTransform.getScaleY());
                }
                //Log.d(TAG, "ScaleXY.x: " + scaleTransform.getScaleX() + " ScaleXY.y: " + scaleTransform.getScaleY());
            }
            //旋转
            BaseKeyframeAnimation<Float, Float> rotation = keyframeAnimation.getRotation();
            if (rotation != null) {
                float rotationValue;
                if (rotation instanceof ValueCallbackKeyframeAnimation) {
                    rotationValue = rotation.getValue();
                } else {
                    rotationValue = ((FloatKeyframeAnimation) rotation).getFloatValue();
                }
                if (rotationValue != 0f) {
                    targetView.setRotation(rotationValue);
                }
            }

            //透明
            BaseKeyframeAnimation<?, Integer> animationOpacity = keyframeAnimation.getOpacity();
            if (animationOpacity != null) {
                int opacity = keyframeAnimation.getOpacity().getValue();
                float alpha = opacity / 100f;
                //float viewAlpha = targetView.getAlpha();
                targetView.setAlpha(alpha);
                //Log.d(TAG, "alpha: " + alpha + " viewAlpha: " + viewAlpha);
            }

            //平移
            BaseKeyframeAnimation<?, PointF> position = keyframeAnimation.getPosition();
            BaseKeyframeAnimation<PointF, PointF> anchorPoint = keyframeAnimation.getAnchorPoint();
            if (position != null && anchorPoint != null) {
                PointF positionValue = position.getValue();
                PointF anchorPointValue = anchorPoint.getValue();
                if (positionValue != null && (positionValue.x != 0 || positionValue.y != 0) && (anchorPointValue.x != 0 || anchorPointValue.y != 0)) {
                    //Log.d(TAG, "positionValue.x: " + positionValue.x + " positionValue.y: " + positionValue.y);
                    //Log.d(TAG, "anchorPointValue.x: " + anchorPointValue.x + " anchorPointValue.y: " + anchorPointValue.y);
                    float x = positionValue.x - anchorPointValue.x;
                    if (targetView.getTranslationX() != x) {
                        targetView.setTranslationX(x);
                    }
                    float y = positionValue.y - anchorPointValue.y;
                    if (targetView.getTranslationY() != y) {
                        targetView.setTranslationY(y);
                    }
                    //Log.d(TAG, "view0000 translationX: " + targetView.getTranslationX() + " TranslationY: " + targetView.getTranslationY());
                }
            } else if (position != null) {
                PointF positionValue = position.getValue();
                if (positionValue != null && (positionValue.x != 0 || positionValue.y != 0)) {
                    //Log.d(TAG, "positionValue.x: " + positionValue.x + " positionValue.y: " + positionValue.y);
                    if (targetView.getTranslationX() != positionValue.x) {
                        targetView.setTranslationX(positionValue.x);
                    }
                    if (targetView.getTranslationY() != positionValue.y) {
                        targetView.setTranslationY(positionValue.y);
                    }
                    //Log.d(TAG, "view1111 positionValue.x: " + view.getTranslationX() + " positionValue.y: " + view.getTranslationY());
                }
            } else if (anchorPoint != null) {
                PointF anchorPointValue = anchorPoint.getValue();
                if (anchorPointValue.x != 0 || anchorPointValue.y != 0) {
                    //Log.d(TAG, "anchorPointValue.x: " + anchorPointValue.x + " anchorPointValue.y: " + anchorPointValue.y);
                    if (targetView.getTranslationX() != anchorPointValue.x) {
                        targetView.setTranslationX(-anchorPointValue.x);
                    }
                    if (targetView.getTranslationY() != anchorPointValue.y) {
                        targetView.setTranslationY(-anchorPointValue.y);
                    }
                    //Log.d(TAG, "view2222 positionValue.x: " + view.getTranslationX() + " positionValue.y: " + view.getTranslationY());
                }
            }
        });
    }

    public void destroy() {
        destroyAnimation(mLottieValueAnimator);
        mLottieValueAnimator = null;
        mLayerAnimationList = null;
        mIJDAnimationListener = null;
    }

    private void destroyAnimation(LottieValueAnimator lottieValueAnimator) {
        if (lottieValueAnimator == null) {
            return;
        }
        if (lottieValueAnimator.isRunning()) {
            lottieValueAnimator.cancel();
        }
        lottieValueAnimator.removeAllListeners();
        lottieValueAnimator.clearComposition();
    }

    public void setXAnimationListener(IXAnimationListener jdAnimationListener) {
        this.mIJDAnimationListener = jdAnimationListener;
    }
}
