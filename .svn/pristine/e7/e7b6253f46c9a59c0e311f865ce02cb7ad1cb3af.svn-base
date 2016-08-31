package com.gosuncn.cu.module.dagger2;

import com.gosuncn.cu.app.MyApplication;
import com.gosuncn.cu.di.AppComponent;
import com.gosuncn.cu.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by hwj on 2016/7/14.
 */
@ActivityScope
@Component(dependencies = AppComponent.class)//能用@Component注解的只能是interface或者抽象类
public abstract class Dagger2Component {
    abstract void inject(Dagger2Activity activity);

    private static Dagger2Component sComponent;

    public static Dagger2Component getInstance() {
        if (sComponent == null) {
            sComponent = DaggerDagger2Component.builder().appComponent(MyApplication.getInstance().getAppComponent()).build();
        }
        return sComponent;
    }
}
