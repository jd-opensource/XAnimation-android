package com.jingdong.app.mall.bundle.xanimation.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

public class ActivityUtil {
    public static boolean isLiving(Context context) {
        if (context == null) {
            return false;
        } else {
            if (context instanceof Activity && isActivityFinishingOrDestroyed(context)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isActivityFinishingOrDestroyed(Context context) {
        Activity activity = (Activity) context;
        if (activity.isFinishing()) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return activity.isDestroyed();
        }
        return false;
    }
}
