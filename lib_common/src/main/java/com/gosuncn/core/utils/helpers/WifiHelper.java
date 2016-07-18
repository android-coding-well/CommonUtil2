package com.gosuncn.core.utils.helpers;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

/**
 * wifi配置助手
 *
 * @author HWJ
 */
public class WifiHelper {
    private static final String TAG = "WifiHelper";
    public static int WIFICIPHER_NOPASS = 1;
    public static int WIFICIPHER_WEP = 2;
    public static int WIFICIPHER_WPA = 3;

    private Context context = null;
    private WifiManager mWifiManager;

    public WifiHelper(Context context) {
        this.context = context;
        mWifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 打开WiFi
     *
     * @return
     */
    public boolean openWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            return mWifiManager.setWifiEnabled(true);
        }

        return true;
    }

    /**
     * 关闭wifi
     *
     * @return
     */
    public boolean closeWifi() {
        if (mWifiManager.isWifiEnabled()) {
            return mWifiManager.setWifiEnabled(false);
        }

        return true;
    }

    /**
     * 扫描wifi
     *
     * @return
     */
    public List<ScanResult> wifiScanResult() {
        mWifiManager.startScan();
        return mWifiManager.getScanResults();
    }

    public List<WifiConfiguration> wifiConfigResult() {
        return mWifiManager.getConfiguredNetworks();
    }

    /**
     * 连接wifi
     *
     * @param SSID     wifi名称
     * @param password 密码
     * @param type     WIFICIPHER_NOPASS WIFICIPHER_WEP WIFICIPHER_WPA
     * @return
     */
    public boolean wifiConnect(String SSID, String password, int type) {
        if (SSID != null && SSID.trim().length() > 0) {
            List<ScanResult> mWifiList = wifiScanResult();
            for (ScanResult e : mWifiList) {
                if (e.SSID.equalsIgnoreCase(SSID)) {
                    WifiConfiguration mWifiConfiguration = createWifiConfiguration(SSID, password,
                            type);
                    if (mWifiConfiguration != null) {
                        WifiConfiguration tempConfig = isExsits(SSID);

                        if (tempConfig != null) {
                            mWifiManager.removeNetwork(tempConfig.networkId);
                        }

                        int netID = mWifiManager.addNetwork(mWifiConfiguration);
                        mWifiManager.enableNetwork(netID, true);
                        boolean connected = mWifiManager.reconnect();
                        return connected;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 断开指定wifi连接
     *
     * @param netId
     */
    public void disConnectionWifi(int netId) {
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }

    /**
     * 获得当前连接的wifi
     *
     * @return
     */
    public WifiInfo getConnectionInfo() {
        return mWifiManager.getConnectionInfo();
    }

    public WifiConfiguration createWifiConfiguration(String SSID, String Password, int Type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        WifiConfiguration tempConfig = isExsits(SSID);
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }

        if (Type == WIFICIPHER_NOPASS) {
            Log.e(TAG, "WIFICIPHER_NOPASS");
            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (Type == WIFICIPHER_WEP) {
            Log.e(TAG, "WIFICIPHER_WEP");
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + Password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (Type == WIFICIPHER_WPA) {
            Log.e(TAG, "WIFICIPHER_WPA");
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }

        return config;
    }

    private WifiConfiguration isExsits(String SSID) {
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }

        return null;
    }
}
