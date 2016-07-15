package com.gosuncn.cu.app;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

/**
 * Created by hwj on 2016/7/11.
 */
public class ParamType {
    public static final int SPRING=1;
    public static final int SUMMER=2;
    public static final int AUTUMN=3;
    public static final int WINTER=4;
    /**
     * 指定常量参数，StringDef亦类似
     */
    @IntDef({SPRING,SUMMER,AUTUMN,WINTER})
    public @interface Seasons{}


    public static final String HUMAN="human";
    public static final String CAT="cat";
    public static final String DOG="dog";
    public static final String BIRD="bird";
    @StringDef({HUMAN,CAT,DOG,BIRD})
    public @interface Animal{}
}
