package com.gosuncn.cu.module.retrofit;

import com.gosuncn.cu.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hwj on 2016/7/14.
 */
@Module
public class RetrofitModule {

    private RetrofitContract.View view;
    public RetrofitModule(RetrofitContract.View view){
        this.view=view;
    }
    @Provides
    @ActivityScope
    RetrofitContract.View provideRetrofitContractView(){
        return view;
    }
}
