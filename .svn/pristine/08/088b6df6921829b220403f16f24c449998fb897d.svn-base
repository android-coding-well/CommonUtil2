package com.gosuncn.cu.module.dagger2.fragment;

import com.gosuncn.cu.di.scope.FragmentScoped;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hwj on 2016/7/14.
 */
@Module
public class BlankModule {
    private BlankContract.View view;
    public BlankModule(BlankContract.View view){
        this.view=view;
    }
    @Provides
    @FragmentScoped
    BlankContract.View provideBlankContractView(){
        return view;
    }
}
