package com.gosuncn.core.utils;

/**
 * 防止按钮多次点击
 */
public class DebounceClickUtil {

    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
