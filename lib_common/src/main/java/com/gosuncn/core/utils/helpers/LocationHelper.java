package com.gosuncn.core.utils.helpers;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import java.util.List;


/**
 * 定位助手(只适用于室外定位)
 * Created by hwj on 2016/11/21.
 */

public class LocationHelper {

    public interface OnLocationChangeListener {
        void onLocationChange(Location location);
    }

    private static final String TAG = "LocationHelper";
    private LocationManager locationManager;
    private String locationProvider = null;

    private int minTime = 10000;//毫秒
    private int minDistance = 1;//米

    private OnLocationChangeListener onLocationChangeListener;

    public LocationHelper(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void setOnLocationChangeListener(OnLocationChangeListener listener) {
        this.onLocationChangeListener = listener;
    }

    /**
     * 设置定位间隔时间
     *
     * @param minTime 毫秒
     */
    public void setIntervalTime(int minTime) {
        this.minTime = minTime;
    }

    /**
     * 设置定位最小变动距离
     *
     * @param minDistance 米
     */
    public void setMinDistance(int minDistance) {
        this.minDistance = minDistance;
    }

    /**
     * 检测定位是否可用，在定位前需先调用此方法
     *
     * @return
     */
    public boolean prepare() {
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            // Toast.makeText(context, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "没有可用的位置提供器");
            return false;
        }

        return true;
    }

    /**
     * 开始定位
     */
    @RequiresPermission(anyOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void startLocation() {
        //获取Location
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            //不为空,显示地理位置经纬度
            // showLocation(location);
            String locationStr = "纬度：" + location.getLatitude() + "\n"
                    + "经度：" + location.getLongitude();
            Log.e(TAG, locationStr);
            if (onLocationChangeListener != null) {
                onLocationChangeListener.onLocationChange(location);
            }
        }
        //监视地理位置变化
        //参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
        locationManager.requestLocationUpdates(locationProvider, minTime, minDistance, locationListener);
    }

    /**
     * 关闭定位
     */
    @RequiresPermission(anyOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void close() {
        if (locationManager != null) {
            //移除监听器
            locationManager.removeUpdates(locationListener);
        }
    }

    /**
     * 设置位置监听器
     *
     * @param listener
     */
    public void setLocationListener(LocationListener listener) {
        this.locationListener = listener;
    }

    /**
     * 获得最近定位的位置
     *
     * @return
     */
    @RequiresPermission(anyOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public Location getLastKnownLocation() {
        if (locationProvider != null) {
            return locationManager.getLastKnownLocation(locationProvider);
        }
        return null;
    }

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {
            Log.e(TAG, "onStatusChanged:" + provider+",status="+status);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled:" + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled:" + provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            String locationStr = "纬度：" + location.getLatitude() + "\n"
                    + "经度：" + location.getLongitude()+ "\n"
                    + "精度：" + location.getAccuracy();
            Log.e(TAG, locationStr);
            if (onLocationChangeListener != null) {
                onLocationChangeListener.onLocationChange(location);
            }
        }
    };

}
