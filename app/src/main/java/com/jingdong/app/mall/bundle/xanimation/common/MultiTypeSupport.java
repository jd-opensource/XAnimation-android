package com.jingdong.app.mall.bundle.xanimation.common;

public interface MultiTypeSupport<T> {
    // 根据当前位置或者条目数据返回布局
    int getLayoutId(T item, int position);
}
