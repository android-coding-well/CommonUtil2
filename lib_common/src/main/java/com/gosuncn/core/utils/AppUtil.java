package com.gosuncn.core.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * App辅助类
 *
 * @author HWJ
 */
public class AppUtil {

    private AppUtil() {
        throw new UnsupportedOperationException("sorry,AppUtil cannot be instantiated");
    }

    /**
     * 获取应用程序名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得当前应用的版本号<br>
     *
     * @param context     上下文
     * @param defaultCode 如果获取失败，则返回默认值
     * @return 版本号
     */
    public static String getVersionCode(Context context, String defaultCode) {

        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo;
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
            String version = packInfo.versionName;
            if (version == null) {
                return defaultCode;
            } else {
                return version;
            }

        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return defaultCode;
        }

    }

    /**
     * 获得Uid
     *
     * @param packageName 包名
     * @param context
     * @return
     */
    public static int getUid(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName,
                    PackageManager.GET_ACTIVITIES);
            return ai.uid;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 判断app是否安装
     *
     * @param context
     * @param packageName 包名
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    /**
     * 打开app
     *
     * @param context
     * @param packageName 包名
     */
    public static boolean openApp(Context context, String packageName) {
        if (isAppInstalled(context, packageName)) {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

}
