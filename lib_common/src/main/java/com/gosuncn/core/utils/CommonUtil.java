package com.gosuncn.core.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

/**
 * 其他工具类
 * @author HWJ
 *
 */
public class CommonUtil {
    /**
     * 判断是否安装目标应用
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    public  static boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }


    /**
     * 判断某个界面是否在前台
     *<uses-permission android:name="android.permission.GET_TASKS"/>
     * @param context
     * @param className
     *            某个界面名称
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    public static String keepStringNotNull(String s) {
        return s == null ? "" : s;
    }

}
