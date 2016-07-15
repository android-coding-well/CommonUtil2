package com.gosuncn.cu.app;

import android.os.Environment;

/**
 * 在此类下放置常量值<br/>
 * 注意包名和版本名称无需在这里指定，直接从BuildConfig获取
 */
public class AppConfig {

    /**
     * SD卡根目录
     */
    public static final String SDCARD_ROOT_PATH = Environment
            .getExternalStorageDirectory().toString();
    /**
     * APP根目录
     */
    public static final String APP_ROOT_PATH = Environment
            .getDataDirectory().toString();
    /**
     * APP下载缓存根目录
     */
    public static final String APP_CACHE_ROOT_PATH = Environment.getDownloadCacheDirectory().toString();

    public static final String NET_SERVER_IP="http://ip.gosunyun.com:8000/syservice3/";
}
