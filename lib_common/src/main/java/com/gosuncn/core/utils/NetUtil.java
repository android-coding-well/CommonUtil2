package com.gosuncn.core.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 网络相关的工具类
 *
 * @author HWJ
 */
public class NetUtil {

    private static boolean isIgnoreNetStatus = false;

    private NetUtil() {
        throw new UnsupportedOperationException(
                "sorry,NetUtil cannot be instantiated");
    }

    /**
     * 判断是否连接网络
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 打开网络设置界面
     *
     * @param activity
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 判断是否有网络权限
     *
     * @param context
     * @return
     */
    public static boolean hasInternetPermission(Context context) {
        if (context != null) {
            return context
                    .checkCallingOrSelfPermission("android.permission.INTERNET") == PackageManager. PERMISSION_GRANTED;
        }
        return true;
    }

    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            NetworkInfo info = getActiveNetworkInfo(context);
            return (info != null) && (info.isConnected());
        }

        return false;
    }

    /**
     * 判断当前是否是wifi
     *
     * @param context
     * @return
     */
    public static boolean isWifiValid(Context context) {
        if (context != null) {
            NetworkInfo info = getActiveNetworkInfo(context);

            return (info != null)
                    && (ConnectivityManager.TYPE_WIFI == info.getType())
                    && (info.isConnected());
        }
        return false;
    }

    /**
     * 判断是否为移动网络
     *
     * @param context
     * @return
     */
    public static boolean isMobileNetwork(Context context) {
        if (context != null) {
            NetworkInfo info = getActiveNetworkInfo(context);

            if (info == null) {
                return false;
            }

            return (info != null) && (info.getType() == 0)
                    && (info.isConnected());
        }

        return false;
    }

    /**
     * 获得当前网络信息
     *
     * @param context
     * @return
     */
    public static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivity.getActiveNetworkInfo();
    }

    /**
     * 获得指定网络类型信息
     *
     * @param context
     * @param networkType
     * @return
     */
    public static NetworkInfo getNetworkInfo(Context context, int networkType) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(networkType);
    }

    /**
     * 获得当前的网络类型
     *
     * @param context
     * @return
     */
    public static int getNetworkType(Context context) {
        if (context != null) {
            NetworkInfo info = getActiveNetworkInfo(context);

            return info == null ? -1 : info.getType();
        }

        return -1;
    }


    /**
     * 获得当前网络类型（WIFI,2G,3G,4G）
     * @param context
     * @return 空则为无网络连接
     */
    public static String getNetworkTypeDesc(Context context) {
        String strNetworkType = "";
        if (context != null) {
            NetworkInfo networkInfo = getActiveNetworkInfo(context);
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    strNetworkType = "WIFI";
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    String _strSubTypeName = networkInfo.getSubtypeName();

                    Log.e("cocos2d-x", "Network getSubtypeName : " + _strSubTypeName);

                    // TD-SCDMA   networkType is 17
                    int networkType = networkInfo.getSubtype();
                    switch (networkType) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                            strNetworkType = "2G";
                            break;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                        case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                        case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                            strNetworkType = "3G";
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                            strNetworkType = "4G";
                            break;
                        default:
                            // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                            if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                strNetworkType = "3G";
                            } else {
                                strNetworkType = _strSubTypeName;
                            }

                            break;
                    }

                    Log.e("cocos2d-x", "Network getSubtype : " + Integer.valueOf(networkType).toString());
                }
            }
        }


        Log.e("cocos2d-x", "Network Type : " + strNetworkType);

        return strNetworkType;
    }

    /**
     * 获得wifi状态
     *
     * @param context
     * @return
     */
    public static int getWifiState(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (wifi == null) {
            return 4;
        }

        return wifi.getWifiState();
    }

    public static NetworkInfo.DetailedState getWifiConnectivityState(
            Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context, 1);
        return networkInfo == null ? NetworkInfo.DetailedState.FAILED
                : networkInfo.getDetailedState();
    }

    /**
     * 连接到指定wifi
     *
     * @param context
     * @param wifiSSID
     * @param password
     * @return
     */
    public static boolean wifiConnection(Context context, String wifiSSID,
                                         String password) {
        boolean isConnection = false;
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String strQuotationSSID = "\"" + wifiSSID + "\"";

        WifiInfo wifiInfo = wifi.getConnectionInfo();
        if ((wifiInfo != null)
                && ((wifiSSID.equals(wifiInfo.getSSID())) || (strQuotationSSID
                .equals(wifiInfo.getSSID())))) {
            isConnection = true;
        } else {
            List scanResults = wifi.getScanResults();
            if ((scanResults != null) && (scanResults.size() != 0)) {
                for (int nAllIndex = scanResults.size() - 1; nAllIndex >= 0; nAllIndex--) {
                    String strScanSSID = ((ScanResult) scanResults
                            .get(nAllIndex)).SSID;
                    if ((wifiSSID.equals(strScanSSID))
                            || (strQuotationSSID.equals(strScanSSID))) {
                        WifiConfiguration config = new WifiConfiguration();
                        config.SSID = strQuotationSSID;
                        config.preSharedKey = ("\"" + password + "\"");
                        config.status = 2;

                        int nAddWifiId = wifi.addNetwork(config);
                        isConnection = wifi.enableNetwork(nAddWifiId, false);
                        break;
                    }
                }
            }
        }

        return isConnection;
    }

    /**
     * 清除缓存
     *
     * @param context
     */
    public static void clearCookies(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }


    /**
     * 设置手机的移动数据的开关
     * 需要权限android.permission.ACCESS_NETWORK_STATE<br/>
     * android.permission.CHANGE_NETWORK_STATE
     *
     * @param pContext
     * @param pBoolean
     */
    public static void setMobileDataEnable(Context pContext, boolean pBoolean) {

        try {

            ConnectivityManager mConnectivityManager = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);

            Class ownerClass = mConnectivityManager.getClass();

            Class[] argsClass = new Class[1];
            argsClass[0] = boolean.class;

            Method method = ownerClass.getMethod("setMobileDataEnabled", argsClass);

            method.invoke(mConnectivityManager, pBoolean);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("移动数据设置错误: " + e.toString());
        }
    }

    /**
     * 返回手机移动数据的状态
     *
     * @param pContext
     * @param arg      默认填null
     * @return true 连接 false 未连接
     */
    public static boolean getMobileDataState(Context pContext, Object[] arg) {

        try {

            ConnectivityManager mConnectivityManager = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);

            Class ownerClass = mConnectivityManager.getClass();

            Class[] argsClass = null;
            if (arg != null) {
                argsClass = new Class[1];
                argsClass[0] = arg.getClass();
            }

            Method method = ownerClass.getMethod("getMobileDataEnabled", argsClass);

            Boolean isOpen = (Boolean) method.invoke(mConnectivityManager, arg);

            return isOpen;

        } catch (Exception e) {
            // TODO: handle exception

            System.out.println("得到移动数据状态出错");
            return false;
        }

    }

    /**
     * 非wifi环境下显示提醒
     *
     * @param context
     */
    public static void showHintWhenNonWifi(final Context context) {
        if (!isIgnoreNetStatus) {

            NetworkInfo networkInfo = getActiveNetworkInfo(context);

            if (networkInfo != null && networkInfo.isAvailable()) {
                if (networkInfo.getType() != ConnectivityManager.TYPE_WIFI) {// 非WiFi环境
                    Dialog alertDialog = new AlertDialog.Builder(context)
                            .setTitle("温馨提醒")
                            .setMessage("当前处在非WIFI网络中，是否继续？")
                            .setNegativeButton("忽略", new OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    isIgnoreNetStatus = true;
                                }
                            })
                            .setPositiveButton("去设置WIFI",
                                    new OnClickListener() {

                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            Intent intent = new Intent();
                                            intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
                                            context.startActivity(intent);

                                        }
                                    }).create();
                    alertDialog.show();
                }
            }
        }
    }

}
