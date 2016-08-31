package com.gosuncn.cu.net;

import retrofit2.Callback;

/**
 * 扩展retrofit回调，实现在请求前和请求后做统一处理事件
 * @param <T>
 */
public interface CallbackExtend<T> extends Callback<T>{
    /**
     * 在请求前的操作，比如弹出对话框
     */
    void onBefore();
    /**
     * 在请求后的操作，比如关闭对话框
     */
    void onAfter();
}
