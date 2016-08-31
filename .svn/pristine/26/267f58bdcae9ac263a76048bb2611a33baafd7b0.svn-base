package com.gosuncn.cu.module.dagger2.fragment;

import com.gosuncn.cu.di.AppComponent;
import com.gosuncn.cu.di.scope.FragmentScoped;

import dagger.Component;

/**
 * Created by hwj on 2016/7/14.
 */
@FragmentScoped
@Component(dependencies = AppComponent.class,modules = BlankModule.class)
public interface BlankComponent {
    void inject(BlankFragment fragment);
}
