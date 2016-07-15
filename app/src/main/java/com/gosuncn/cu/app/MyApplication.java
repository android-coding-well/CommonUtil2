package com.gosuncn.cu.app;

import com.gosuncn.cu.di.AppComponent;
import com.gosuncn.cu.di.AppModule;
import com.gosuncn.cu.di.DaggerAppComponent;
import com.gosuncn.cu.di.NetModule;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by hwj on 2016/7/5.
 */
public class MyApplication extends android.app.Application {

    private AppComponent appComponent;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        //设置greenDAO的sqlite语句控制台打印
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;

        //参数不为空的module必须在此处创建
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).netModule(new NetModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }


    public static MyApplication getInstance() {
        return instance;
    }


}
