package com.jingdong.app.mall.bundle.xanimation;

import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.layer.Layer;

public class LayerAnimation {
    private Layer layer;
    private TransformKeyframeAnimation transformKeyframeAnimation;
    private FloatKeyframeAnimation inOutAnimation;

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public TransformKeyframeAnimation getTransformKeyframeAnimation() {
        return transformKeyframeAnimation;
    }

    public void setTransformKeyframeAnimation(TransformKeyframeAnimation transformKeyframeAnimation) {
        this.transformKeyframeAnimation = transformKeyframeAnimation;
    }

    public FloatKeyframeAnimation getInOutAnimation() {
        return inOutAnimation;
    }

    public void setInOutAnimation(FloatKeyframeAnimation inOutAnimation) {
        this.inOutAnimation = inOutAnimation;
    }
}
