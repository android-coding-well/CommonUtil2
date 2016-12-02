package com.gosuncn.core.utils.helpers;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * logcat日志保存助手，需要写权限
 * 由于logcat数量巨大，因此本类只存储设定的最大值，超过则不会继续存储，而且只保留本次应用启动的logcat
 * 单例模式，注意本类在线程里慎用Log,否则会使数据冗余剧增
 * Created by hwj on 2016/11/25.
 */

public class LogcatHelper {
    private static final String TAG = "LogcatHelper";
    /**
     * 单位 KB
     */
    public static final int UNIT_KB = 1024;
    /**
     * 单位 MB
     */
    public static final int UNIT_MB = 1024 * 1024;

    @IntDef({UNIT_KB, UNIT_MB})
    public @interface Unit {
    }


    /**
     * 日志的默认最大存储空间
     */
    private static final int DEFAULT_MAX_STORAGE_SIZE = UNIT_MB*100;//100M


    private int maxSize = DEFAULT_MAX_STORAGE_SIZE;


    private static LogcatHelper instance = null;
    private static String logcatPath;//日志保存路径
    private LogDumper mLogDumper = null;//日志记录线程
    private int pid;//进程id


    /**
     * logcat 命令字符串
     * 如："logcat -s [filtername]";//打印标签过滤信息,filtername 过滤名称
     * 如："logcat *:e *:w | grep \"( [pid])\"";//打印标签过滤信息,pid 进程id,日志等级：*:v , *:d , *:w , *:e , *:f , *:s
     */
    private String cmd;

    public static LogcatHelper getInstance(Context context) {
        if (instance == null) {
            instance = new LogcatHelper(context);
        }
        return instance;
    }

    private LogcatHelper(@NonNull Context context) {

        PackageManager pm = context.getPackageManager();
        String appName = context.getApplicationInfo().loadLabel(pm).toString();
        //设置默认的存储路径
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            logcatPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + appName + File.separator + "logcat";
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            logcatPath = context.getFilesDir().getAbsolutePath()
                    + File.separator + "logcat";
        }
        pid = android.os.Process.myPid();
        cmd = "logcat  | grep \"(" + pid + ")\"";//打印所有日志信息
        // cmd = "logcat *:e *:w | grep \"(" + pid + ")\""; //打印指定等级日志信息
    }


    /**
     * 设置最大的日志存储空间
     *
     * @param bytes 字节
     */
    public LogcatHelper setMaxStorageSizeWithByte(int bytes) {
        this.maxSize = bytes;
        return this;
    }

    /**
     * 设置最大的日志存储空间
     *
     * @param size 大小
     * @param unit 单位
     */
    public LogcatHelper setMaxStorageSize(int size, @Unit int unit) {
        this.maxSize = size * unit;
        return this;
    }


    /**
     * 设置logcat日志存储目录,默认以应用名为根目录
     *
     * @param path
     */
    public LogcatHelper setLogcatPath(@NonNull String path) {
        this.logcatPath = path;
        return this;
    }

    /**
     * 获得logcat日志保存目录
     *
     * @return
     */
    public String getLogcatPath() {
        return logcatPath;
    }

    /**
     * 设置logcat cmd,默认是当前线程全部logcat
     *
     * @param cmd
     */
    public LogcatHelper setCmd(@NonNull String cmd) {
        this.cmd = cmd;
        return this;
    }

    /**
     * 获得当前的z执行的cmd
     *
     * @return
     */
    public String getCmd() {
        return this.cmd;
    }

    /**
     * 开始保存logcat日志
     */
    public void start() {
        if (mLogDumper == null) {
            //删除原先保存的日志
            File file = new File(logcatPath);
            delete(file);
            mLogDumper = new LogDumper(String.valueOf(pid), logcatPath, cmd);
        }
        mLogDumper.start();
    }

    /**
     * 停止logcat日志记录
     */
    public void stop() {
        if (mLogDumper != null) {
            mLogDumper.stopLogs();
            mLogDumper = null;
        }
    }


    private class LogDumper extends Thread {

        private Process logcatProc;
        private BufferedReader mReader = null;
        private boolean mRunning = true;
        private String cmd = null;
        private String pid;
        private FileOutputStream fileOutputStream = null;
        private OutputStreamWriter outputStreamWriter = null;

        private int size = 0;

        public LogDumper(String pid, String dir, String cmd) {
            this.pid = pid;
            this.cmd = cmd;
            size = 0;
            try {
                File direction = new File(dir);
                if (!direction.exists()) {
                    direction.mkdirs();
                }
                // fileOutputStream = new FileOutputStream(new File(dir, "Logcat-"
                //                   + getFormatDate() + ".log"));

                fileOutputStream = new FileOutputStream(new File(dir, "Logcat.log"));

                outputStreamWriter = new OutputStreamWriter(fileOutputStream, "gbk");//指定编码，否则中文会乱码，因为默认的是utf-8

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 停止logcat线程
         */
        public void stopLogs() {
            mRunning = false;
        }

        @Override
        public void run() {
            try {
                logcatProc = Runtime.getRuntime().exec(cmd);
                mReader = new BufferedReader(new InputStreamReader(
                        logcatProc.getInputStream()), 1024);
                String line = null;
                while (mRunning && (line = mReader.readLine()) != null) {
                    if (!mRunning) {
                        break;
                    }
                    if (line.length() == 0) {
                        continue;
                    }

                    if (fileOutputStream != null && line.contains(pid)) {
                        //fileOutputStream.write((getFormatTime() + "  " + line + "\n").getBytes("gbk"));
                        size += line.length();

                        if (size < maxSize) {
                            outputStreamWriter.write(getFormatDateTime() + "  " + line + "\n");
                        } else {
                            mRunning = false;
                            Log.i(TAG, "logcat日志超出设定大小(" + maxSize + ")：" + size);
                            size = 0;
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }

        }

        /**
         * 关闭输入输出流,关闭顺序：reader->writer->stream
         */
        private void close() {
            if (logcatProc != null) {
                logcatProc.destroy();
                logcatProc = null;
            }
            if (mReader != null) {
                try {
                    mReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mReader = null;
            }
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                outputStreamWriter = null;
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fileOutputStream = null;
            }
        }
    }

    /**
     * 获得当前时间的格式化字符串
     *
     * @return
     */
    private String getFormatDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;
    }

    private String getFormatDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;
    }

    private String getFormatTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;
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

    /**
     * 删除多个文件
     *
     * @param files
     */
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
}
