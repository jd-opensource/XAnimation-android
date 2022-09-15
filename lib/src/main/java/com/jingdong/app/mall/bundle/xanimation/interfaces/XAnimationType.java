package com.jingdong.app.mall.bundle.xanimation.interfaces;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        XAnimationType.TYPE_ASSIGN_VIEW,
        XAnimationType.TYPE_ASSIGN_VIEW_GROUP,
        XAnimationType.TYPE_ASSIGN_LAYER,
        XAnimationType.TYPE_MONITOR_PROGRESS
})
@Retention(RetentionPolicy.SOURCE)
public @interface XAnimationType {
    /**
     * 0: 指定原生控件执行动画
     * 1: 指定原生容器控件执行动画
     * 2: 指定json图层动画
     * 3: 监听json动画进度
     */
    int TYPE_ASSIGN_VIEW = 0;
    int TYPE_ASSIGN_VIEW_GROUP = 1;
    int TYPE_ASSIGN_LAYER = 2;
    int TYPE_MONITOR_PROGRESS = 3;
}
