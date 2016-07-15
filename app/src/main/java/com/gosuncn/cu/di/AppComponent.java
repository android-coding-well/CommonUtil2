package com.gosuncn.cu.di;

import android.content.Context;

import com.google.gson.Gson;
import com.gosuncn.core.utils.ACacheUtil;
import com.gosuncn.cu.db.DBManager;
import com.gosuncn.cu.net.TestService;
import com.gosuncn.cu.net.TestService2;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hwj on 2016/7/12.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
    Context context();  // 提供Applicaiton的Context

    DBManager dbManager();  // 数据库管理类

    ACacheUtil acacheUtil();  //缓存管理

    Gson gson();  // gson

    TestService testService();

    TestService2 testService2();
}
