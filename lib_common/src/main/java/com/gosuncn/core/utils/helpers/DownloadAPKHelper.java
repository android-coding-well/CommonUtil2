package com.gosuncn.core.utils.helpers;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;

/**
 * apk下载助手
 *
 * @author HWJ
 */

public class DownloadAPKHelper {

    private DownloadManager downloadManager;
    private Request request;
    private Context context;
    private File downloadFile;

    private long myDownloadReference;

    public long getMyDownloadReference() {
        return myDownloadReference;
    }

    /**
     * @param context
     * @param appName     应用名
     * @param description 应用描述
     * @param url         下载地址
     */
    public DownloadAPKHelper(Context context, String appName, String description, String url) {
        this.context = context;
        downloadManager = (DownloadManager) context
                .getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri
                .parse(url);
        request = new Request(uri);
        request.setTitle(appName);
        request.setDescription(description);
        registerDownloadCompleteReceiver();
        registerNotificationClickedReceiver();
    }

    /**
     * 返回request，可用于更多参数设置
     *
     * @return
     */
    public Request getRequest() {
        return request;
    }

    /**
     * 只允许在wifi下下载
     */
    public void setOnlyWifi() {
        request.setAllowedNetworkTypes(Request.NETWORK_WIFI);
    }

    /**
     * 用于设置下载时时候在状态栏显示通知信息
     */
    public void setNotificationVisibility(int visibility) {
        request.setNotificationVisibility(visibility);
    }


    /**
     * 指定保存路径
     *
     * @param file
     */
    public void setSavePath(File file) {
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        this.downloadFile = file;
        request.setDestinationUri(Uri.fromFile(downloadFile));
    }

    /**
     * 设置标题和描述
     *
     * @param title
     * @param description
     */
    public void setTitle(String title, String description) {
        request.setTitle(title);
        request.setDescription(description);
    }

    /**
     * 开始下载
     */
    public void startDownload() {
        myDownloadReference = downloadManager.enqueue(request);
    }


    /**
     * 注册下载完成的监听器
     */
    public void registerDownloadCompleteReceiver() {
        IntentFilter filter = new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long reference = intent.getLongExtra(
                        DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (myDownloadReference == reference) {
                    Toast.makeText(context, "下载完成,即将安装", Toast.LENGTH_LONG).show();
                    installAPK();
                }
            }
        };
        context.registerReceiver(receiver, filter);
    }

    /**
     * 注册通知栏被点击事件
     */
    public void registerNotificationClickedReceiver() {
        IntentFilter filter = new IntentFilter(
                DownloadManager.ACTION_NOTIFICATION_CLICKED);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String extraID = DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS;
                long[] references = intent.getLongArrayExtra(extraID);
                for (long reference : references)
                    if (reference == myDownloadReference) {
                        //Toast.makeText(context, "通知栏被点击", Toast.LENGTH_LONG)
                        //		.show();
                    }
            }
        };

        context.registerReceiver(receiver, filter);
    }

    /**
     * 取消下载
     *
     * @param references
     */
    public void cancelDownload(long... references) {
        downloadManager.remove(references);
    }

    /**
     * 安装APK
     */
    public Intent installAPK() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = "application/vnd.android.package-archive";
        intent.setDataAndType(Uri.fromFile(downloadFile), type);
        context.startActivity(intent);
        return intent;
    }

}