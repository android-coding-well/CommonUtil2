package com.gosuncn.core.widget.viewexplosion;

import android.animation.Animator;
import android.view.View;

/**
 * Created by hwj on 2016/1/14.
 */
public interface AnimatorStatusInterface {
    /**
     * 动画开始
     * @param v 动画的view
     * @param animation
     */
    void animStart(View v, Animator animation);

    /**
     * 动画结束
     * @param v 动画的view
     * @param animation
     */
    void animEnd(View v, Animator animation);
}
