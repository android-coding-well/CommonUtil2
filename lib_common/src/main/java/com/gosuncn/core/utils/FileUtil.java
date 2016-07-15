package com.gosuncn.core.utils;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.widget.Toast;

import java.io.File;

public class FileUtil {
    /**
     * 删除文件(包括目录)
     *
     * @param file
     */
    public static void deleteAll(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                deleteAll(childFiles[i]);
            }
            file.delete();
        }
    }


    /**
     * 删除文件(不包括目录)
     *
     * @param file
     */
    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
        }
    }

    public static void delete(File[] files) {

        for (int i = 0; files != null && i < files.length; i++) {
            if (files[i].isFile()) {
                files[i].delete();

            }

            if (files[i].isDirectory()) {
                File[] childFiles = files[i].listFiles();
                if (childFiles == null || childFiles.length == 0) {
                    files[i].delete();

                }

                for (int j = 0; childFiles != null && j < childFiles.length; j++) {
                    delete(childFiles[j]);
                }
                files[i].delete();
            }
        }
    }

    /**
     * 打开手机相册
     *
     * @param context
     * @param path    相册路径
     */
    @Deprecated
    public static void openGallery(final Context context, String path) {
        File folder = new File(path);
        String[] allFiles = folder.list();
        if (allFiles.length > 0) {
            MediaScannerConnection.scanFile(context, new String[]{path
                            + allFiles[allFiles.length - 1]},
                    new String[]{"image/*"},
                    new MediaScannerConnection.OnScanCompletedListener() {

                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri, "image/*");
                            context.startActivity(intent);
                        }

                    });
        } else {
            Toast.makeText(context, "还没有快照哟，请到实时视频抓拍一些~", Toast.LENGTH_SHORT)
                    .show();
        }

    }


    /**
     * 打开手机相册
     *
     * @param context
     * @param path    相册路径
     * @return 图片个数
     */
    public static int openGallery2(final Context context, String path) {
        File folder = new File(path);
        String[] allFiles = folder.list();
        int picCount = 0;
        if (allFiles != null && allFiles.length > 0) {
            picCount = allFiles.length;
            MediaScannerConnection.scanFile(context, new String[]{path
                            + allFiles[allFiles.length - 1]},
                    new String[]{"image/*"},
                    new MediaScannerConnection.OnScanCompletedListener() {

                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri, "image/*");
                            context.startActivity(intent);
                        }

                    });
        }
        return picCount;
    }


    /**
     * 检查磁盘空间是否大于10mb
     *
     * @return true 大于
     */
    public static boolean isDiskAvailable() {
        long size = getDiskAvailableSize();
        return size > 10 * 1024 * 1024; // > 10bm
    }

    /**
     * 获取磁盘可用空间
     *
     * @return byte 单位 kb
     */
    public static long getDiskAvailableSize() {
        if (!existsSdcard()) return 0;
        File path = Environment.getExternalStorageDirectory(); // 取得sdcard文件路径
        StatFs stat = new StatFs(path.getAbsolutePath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
        // (availableBlocks * blockSize)/1024 KIB 单位
        // (availableBlocks * blockSize)/1024 /1024 MIB单位
    }

    public static Boolean existsSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static long getFileOrDirSize(File file) {
        if (!file.exists()) return 0;
        if (!file.isDirectory()) return file.length();

        long length = 0;
        File[] list = file.listFiles();
        if (list != null) { // 文件夹被删除时, 子文件正在被写入, 文件属性异常返回null.
            for (File item : list) {
                length += getFileOrDirSize(item);
            }
        }

        return length;
    }


}
