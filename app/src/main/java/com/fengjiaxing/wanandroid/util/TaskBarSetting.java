package com.fengjiaxing.wanandroid.util;

import android.app.Activity;
import android.graphics.Color;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * @description 隐藏状态栏、导航栏的工具类
 * */
public class TaskBarSetting {

    /**
     * @description 获取状态栏高度
     */
    private static int getStatusBarHeight(Activity activity) {
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return activity.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * @description 获取导航栏高度高度
     */
    private static int getNavBarHeight(Activity activity) {
        int navBarHeight = 0;
        if (!ViewConfiguration.get(activity).hasPermanentMenuKey()) {
            String name = "navigation_bar_height";
            navBarHeight = activity.getResources().getDimensionPixelSize(
                    activity.getResources().getIdentifier(name, "dimen", "android"));
        }
        return navBarHeight;
    }

    /**
     * @description 隐藏导航栏和状态栏
     */
    public static void hideAll(Activity activity, TextView bar) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.rgb(175, 149, 221));
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        bar.setHeight(getStatusBarHeight(activity));
    }

    /**
     * @description 仅隐藏状态栏
     */
    public static void hideTaskBarOnly(Activity activity, TextView statusBar) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.rgb(175, 149, 221));
        statusBar.setHeight(getStatusBarHeight(activity));
    }

}
