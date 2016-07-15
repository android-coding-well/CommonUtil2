package com.gosuncn.core.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.gosuncn.core.utils.L;

/**
 * 可以设置禁止左右滑动的viewpager
 */
public class NoSlidingViewPager extends ViewPager {

    private boolean isPagingEnabled = false;

    public NoSlidingViewPager(Context context) {
        super(context);
    }

    public NoSlidingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    /**
     * 设置是否可以左右滑动，默认false
     *
     * @param b
     */
    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        L.e("123456", "NoSlidingViewPager onKeyDown");
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @param event
     */
    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        return false;
//        return super.dispatchKeyEventPreIme(event);
    }
}