package com.gosuncn.core.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * support non-direct descendant scrolling views
 */
public class SwipeRefreshLayoutExtend extends SwipeRefreshLayout {
    private View mScrollUpChild;
    public SwipeRefreshLayoutExtend(Context context) {
        super(context);
    }


    public SwipeRefreshLayoutExtend(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if (mScrollUpChild != null) {
            return ViewCompat.canScrollVertically(mScrollUpChild, -1);
        }
        return super.canChildScrollUp();
    }

    public void setScrollUpChild(View view) {
        mScrollUpChild = view;
    }
}
