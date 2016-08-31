package com.gosuncn.cu.app;

import com.gosuncn.core.utils.helpers.VersionUpdateManager;
import com.gosuncn.cu.di.AppComponent;
import com.gosuncn.cu.di.AppModule;
import com.gosuncn.cu.di.DaggerAppComponent;
import com.gosuncn.cu.di.NetModule;

import org.greenrobot.greendao.query.QueryBuilder;


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

        testVersionUpdate();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }


    public static MyApplication getInstance() {
        return instance;
    }

    public void testVersionUpdate(){
        VersionUpdateManager.getInstance().init(this);
        VersionUpdateManager.VersionUpdateInfo versionUpdateInfo=new VersionUpdateManager.VersionUpdateInfo();
        versionUpdateInfo.isForce=false;
        versionUpdateInfo.versionCode="1.0.0.01";
        versionUpdateInfo.url="http://gdown.baidu.com/data/wisegame/55dc62995fe9ba82/jinritoutiao_448.apk";
        VersionUpdateManager.getInstance().setVersionUpdateInfo(versionUpdateInfo);
        //VersionUpdateManager.getInstance().setSavePath(AppConfig.SDCARD_ROOT_PATH+"/a.apk");
    }

}
