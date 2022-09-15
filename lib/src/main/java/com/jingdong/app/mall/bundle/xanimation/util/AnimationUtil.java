package com.jingdong.app.mall.bundle.xanimation.util;

import android.text.TextUtils;

import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.layer.Layer;
import com.jingdong.app.mall.bundle.xanimation.LayerAnimation;

import java.util.List;

public class AnimationUtil {
    public static TransformKeyframeAnimation getAnimationByLayerName(String layerName, List<LayerAnimation> layerAnimationList) {
        if (layerAnimationList == null || layerAnimationList.isEmpty()) {
            return null;
        }
        TransformKeyframeAnimation transformKeyframeAnimation = null;
        if (!StringUtil.strIsNull(layerName)) {
            for (LayerAnimation layerAnimation : layerAnimationList) {
                Layer layer = layerAnimation.getLayer();
                if (TextUtils.equals(layerName, layer.getName())) {
                    transformKeyframeAnimation = layerAnimation.getTransformKeyframeAnimation();
                    break;
                }
            }
        }
        if (transformKeyframeAnimation == null && !layerAnimationList.isEmpty()) {
            transformKeyframeAnimation = layerAnimationList.get(0).getTransformKeyframeAnimation();
        }

        return transformKeyframeAnimation;
    }
}
