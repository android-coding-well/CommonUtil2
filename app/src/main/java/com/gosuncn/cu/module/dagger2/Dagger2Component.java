package com.gosuncn.cu.module.dagger2;

import com.gosuncn.cu.di.AppComponent;
import com.gosuncn.cu.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by hwj on 2016/7/14.
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface Dagger2Component {
    void inject(Dagger2Activity activity);
}
