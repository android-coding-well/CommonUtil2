package com.gosuncn.cu.module.annotation;

import android.Manifest;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.Size;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

/**
 * Created by hwj on 2016/3/7.
 */
public class AnnotationTest {

    public static final int SPRING=1;
    public static final int SUMMER=2;
    public static final int AUTUMN=3;
    public static final int WINTER=4;
    /**
     * 指定常量参数，StringDef亦类似
     */
    @IntDef({SPRING,SUMMER,AUTUMN,WINTER})
    public @interface Seasons{}

    /**
     * 必须调用super
     */
    @CallSuper
    public void callsuper(){}


    public void setType(@Seasons int type){}
    public int getType(){
        return AUTUMN;
    }

    /**
     * 返回结果是有意义的，检查是否使用返回结果
     * @return
     */
    @CheckResult
    public int checkResult(){
        return 1;
    }

    /**
     * 不被混淆
     */
    @Keep
    public void keep(){}


    /**
     * 指定线程
     * @param color
     */
    @WorkerThread
    public void setColor(@Nullable @ColorRes int color){ }
    @UiThread
    public void setText(@NonNull @StringRes int text){}

    public void setImage(@DrawableRes int res){}


    /**
     * 指定需要的权限，anyOf:任意一个即可
     */
    @RequiresPermission(Manifest.permission.INTERNET)
    public void permission(){}

    @RequiresPermission(allOf = {
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WAKE_LOCK})
    public void permission2(){}

    @RequiresPermission(anyOf = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION})
    public void permission3(){}

    /**
     * 指定范围
     * @param i
     */
    public void setNumber(@FloatRange(from=0.0,to = 1.0) float i){}

    /**
     * 指定字符串长度的范围
     */
    @Size(min = 2,max = 10)
    public String ss;

    /**
     * 指定数组元素的个数
     */
    @Size(2)
    public int[] array;

    /**
     * 指定数组元素必须是2的倍数
     */
    @Size(multiple=2)
    public int[] array2;

}
