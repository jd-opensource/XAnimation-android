package com.jingdong.app.mall.bundle.xanimation.util;

import android.text.TextUtils;

public class StringUtil {
    public static boolean strIsNull(String str) {
        return TextUtils.isEmpty(str) || str.trim().equals("") || "null".equalsIgnoreCase(str);
    }
}
