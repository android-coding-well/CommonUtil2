package com.gosuncn.cu.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by hwj on 2016/7/12.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
