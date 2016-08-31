package com.gosuncn.cu.di;

import android.content.Context;

import com.google.gson.Gson;
import com.gosuncn.core.utils.ACacheUtil;
import com.gosuncn.cu.db.DBManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hwj on 2016/7/12.
 */
@Module
public class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideAContext() {
        return context;
    }

    @Provides
    DBManager provideDBManager() {
        return new DBManager(context);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    ACacheUtil provideACache() {
        return ACacheUtil.get(context);
    }


}
