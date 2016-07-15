package com.gosuncn.cu.module.greendao.activity;

import com.gosuncn.cu.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hwj on 2016/7/14.
 */
@Module
public class GreenDaoModule {
    private GreenDaoContract.View view;
    public GreenDaoModule(GreenDaoContract.View view){
        this.view=view;
    }
    @Provides
    @ActivityScope
    GreenDaoContract.View provideGreenDaoContractView(){
        return view;
    }
}
