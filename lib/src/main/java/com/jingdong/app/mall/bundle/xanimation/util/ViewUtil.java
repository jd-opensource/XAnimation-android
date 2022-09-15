package com.jingdong.app.mall.bundle.xanimation.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

public class ViewUtil {
    public static List<View> traverseView(View view) {
        List<View> childViewList = new ArrayList<>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewChild = vp.getChildAt(i);
                if (!isViewIgnored(viewChild)) {
                    childViewList.add(viewChild);
                }
                childViewList.addAll(traverseView(viewChild));
            }
        } else {

        }
        return childViewList;
    }

    public static boolean isViewIgnored(View view) {
        if (view == null || view.getVisibility() != View.VISIBLE || !isValid(view.getId())) {
            return true;
        }
        return false;
    }

    private static boolean isValid(int id) {
        return id != -1 && (id & 0xff000000) != 0 && (id & 0x00ff0000) != 0;
    }

    public static View findViewByResourceEntryName(Context context, String resourceEntryName) {
        if (TextUtils.isEmpty(resourceEntryName)) {
            return null;
        }
        if (context instanceof Activity) {
            Window window = ((Activity) context).getWindow();
            if (window.isActive()) {
                View decorView = window.getDecorView();
                List<View> viewList = ViewUtil.traverseView(decorView);
                for (View view : viewList) {
                    if (view != null && TextUtils.equals(resourceEntryName, context.getResources().getResourceEntryName(view.getId()))) {
                        return view;
                    }
                }
            }
        }
        return null;
    }
}
