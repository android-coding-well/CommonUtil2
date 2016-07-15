package com.gosuncn.cu.module.greendao.activity;

import com.gosuncn.cu.di.AppComponent;
import com.gosuncn.cu.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by hwj on 2016/7/14.
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = GreenDaoModule.class)
public interface GreenDaoComponent {
    void inject(GreenDaoActivity greenDaoActivity);
}
