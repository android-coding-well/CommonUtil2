package com.gosuncn.core.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

/**
 * 可用于跳转到手机安装的应用市场里的对应的APP详情页，可用于版本更新下载
 * 用法示例：
 * Intent i = APPMarketUtil.getIntent(this);
 * boolean b = APPMarketUtil.judge(this, i);
 * if(b){
 * startActivity(i);
 * }
 *
 * @author HWJ
 */
public class APPMarketUtil {

    /**
     * 获得跳转到应用市场的intent（默认本地应用）
     *
     * @param paramContext
     * @return
     */
    public static Intent getIntent(Context paramContext) {
        StringBuilder localStringBuilder = new StringBuilder()
                .append("market://details?id=");
        String str = paramContext.getPackageName();
        localStringBuilder.append(str);
        Uri localUri = Uri.parse(localStringBuilder.toString());
        return new Intent("android.intent.action.VIEW", localUri);
    }

    /**
     * 获得跳转到应用市场的intent（指定应用）
     *
     * @param paramContext
     * @param packageName
     * @return
     */
    public static Intent getIntent(Context paramContext, String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + packageName));
        return intent;
    }


    /**
     * 直接跳转不判断是否存在市场应用
     *
     * @param paramContext
     * @param paramString
     */
    public static void start(Context paramContext, String paramString) {
        Uri localUri = Uri.parse(paramString);
        Intent localIntent = new Intent("android.intent.action.VIEW", localUri);
        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        paramContext.startActivity(localIntent);
    }

    /**
     * 判断是否可以跳转到应用市场
     *
     * @param paramContext
     * @param paramIntent
     * @return
     */
    public static boolean judge(Context paramContext, Intent paramIntent) {
        List<ResolveInfo> localList = paramContext.getPackageManager()
                .queryIntentActivities(paramIntent,
                        PackageManager.GET_INTENT_FILTERS);
        if ((localList != null) && (localList.size() > 0)) {
            return true;
        } else {
            return false;
        }
    }
}
