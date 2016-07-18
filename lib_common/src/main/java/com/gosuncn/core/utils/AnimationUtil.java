package com.gosuncn.core.utils;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

public class AnimationUtil {

    /**
     * 左右摇晃
     *
     * @param counts 晃动次数
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    /**
     * 左右摇晃
     *
     * @param counts   晃动次数
     * @param duration 时间
     * @return
     */
    public static Animation shakeAnimation(int counts, int duration) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));//来回循环次数
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    /**
     * 上下跳动
     *
     * @param counts
     * @param duration
     * @return
     */
    public static Animation jumpAnimation(int counts, int duration) {
        Animation translateAnimation = new TranslateAnimation(0, 0, 0, 15);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));//来回循环次数
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    /**
     * 旋转动画
     *
     * @param repeatCount 重复次数
     * @param duration    动画时间
     * @param fillAfter   动画执行完后是否停留在执行完的状态
     * @param startOffset 执行前的等待时间
     * @return
     */
    public static Animation rorateAnimation(int repeatCount, int duration, boolean fillAfter, long startOffset) {
        Animation rorateAnimation = new RotateAnimation(0f, -360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rorateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        rorateAnimation.setRepeatCount(repeatCount);//设置重复次数
        rorateAnimation.setFillAfter(fillAfter);//动画执行完后是否停留在执行完的状态
        rorateAnimation.setStartOffset(startOffset);//执行前的等待时间
        rorateAnimation.setDuration(duration);
        return rorateAnimation;
    }

    /**
     * 根据动画资源获得动画对象
     * @param context
     * @param animId        动画资源
     * @param counts        循环次数
     * @param duration      动画时间
     * @return
     */
    public static Animation getAnimation(Context context, @AnimRes int animId, int counts, int duration) {
        Animation animation = AnimationUtils.loadAnimation(context, animId);
        animation.setInterpolator(new CycleInterpolator(counts));
        animation.setDuration(duration);
        return animation;
    }

    /**
     * 开启View闪烁效果
     *
     * */
    public static void startFlick( View view,int duration ){

        if( null == view ){
            return;
        }
        Animation alphaAnimation = new AlphaAnimation( 1, 0 );
        alphaAnimation.setDuration(duration);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(alphaAnimation);

    }

    /**
     * 取消View闪烁效果
     *
     * */
    public static void stopFlick( View view ){
        if( null == view ){
            return;
        }
        view.clearAnimation();
    }
}
