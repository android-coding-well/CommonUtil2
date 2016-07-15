package com.gosuncn.core.utils.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.gosuncn.core.utils.DateUtil;
import com.gosuncn.core.utils.NetUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.TrafficStats;

/**
 * 流量助手
 * 
 * @author HWJ
 * 
 */
public class TrafficHelper {
	/**
	 * 保存Preference的name
	 */
	public static final String PREFERENCE_NAME = "SYUN_INFO";
	private static SharedPreferences mSharedPreferences;
	private static TrafficHelper trafficHelper;
	private static SharedPreferences.Editor editor;

	private String DAY_MOBILE = "day_mobile";// 当天移动流量
	private String DAY_WIFI = "day_wifi";// 当天wifi流量
	private String MONTH_MOBILE = "month_mobile";// 当月移动流量
	private String MONTH_WIFI = "month_wifi";// 当月wifi流量
	private String TOTAL_MOBILE = "total_mobile";// 总移动流量
	private String TOTAL_WIFI = "total_wifi";// 总wifi流量

	private String TRAFFIC_CURRENT = "traffic_current";// 本次流量
	private String TRAFFIC_TODAY = "traffic_today";// 今日流量
	private String TRAFFIC_MONTH = "traffic_month";// 本月流量
	private String TRAFFIC_TOTAL = "traffic_total";// 历史总流量
	
	
	private static final float MB = 1024 * 1024;
	private static final float KB = 1024;

	private static final float UNIT = MB;// 单位
	public static final String STR_UNIT="MB";
	private static final int ACCURACY = 1;// 计算精度，保留小数点后几位
	private static final int tmp = ACCURACY * 10;
	
	public static float currentMobile=0f;//本次移动流量
	public static float currentWifi=0f;//本次wifi流量

	private String TRAFFIC_DATE_DAY = "traffic_date_day";// 计算日期

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static float newTraffic=0f;//当前流量
	private static float firstTraffic=0f;//每次打开应用时流量
	
	public static  int uid=-1;
	
	private static boolean isWifi=false;//是否在wifi环境
	
	private TrafficHelper(Context context) {
		mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
				Context.MODE_PRIVATE);
	}

	/**
	 * 单例模式，获取instance实例
	 * 
	 * @param cxt
	 * @return
	 */
	public static TrafficHelper getInstance(Context context) {
		/*if(uid==-1){
			throw new UnsupportedOperationException(
					"please init the value of uid");
		}*/
		TrafficHelper.isWifi=NetUtil.isWifiValid(context);
		if (trafficHelper == null) {
			trafficHelper = new TrafficHelper(context);
		}
		editor = mSharedPreferences.edit();
		return trafficHelper;
	}
	
	/**
	 * 网络环境发生变化
	 * 当网络环境发生变化时需要执行此方法
	 */
	public  void netWorkChanges(Boolean isWifi){
		TrafficHelper.isWifi=isWifi;
		updateData();
	}

	/**
	 * 获得应用的流量(包含移动和wifi)
	 * 
	 * @return
	 */
	public  float getTotlaByUid(int uid) {
		if (-1 != uid) {
			long rx = TrafficStats.getUidRxBytes(uid);
			long tx = TrafficStats.getUidRxBytes(uid);
			return (float) (rx + tx) / UNIT;
		}
		return 0f;
	}

	/**
	 * 清空所保存的数据
	 */
	public void cleanAllData(int uid) {
		saveTrafficCurrent(0f);
		saveTrafficToday(0f);
		saveTrafficMonth(0f);
		saveTrafficTotal(0f);
		saveDayMobile(0f);
		saveDayWifi(0f);
		saveMonthMobile(0f);
		saveMonthWifi(0f);
		saveTotalMobile(0f);
		saveTotalWifi(0f);
		TrafficHelper.currentMobile=0f;//当前流量
		TrafficHelper.currentWifi=0f;
		startStatistic(uid);
	}

	/**
	 * 更新流量统计数据
	 */
	public  void updateData() {
		if (uid!= -1) {
			
			boolean flag = DateUtil.isCurrentDay(getTrafficDateDay());
			boolean flag2 = DateUtil.isCurrentMonth(getTrafficDateDay());
			saveTrafficDateDay(new Date());
			
			if (!flag) {// 如果不是当天则清零
				saveTrafficCurrent(0f);
				saveTrafficToday(0f);
				
				currentMobile=0f;
				currentWifi=0f;
				saveDayMobile(0f);
				saveDayWifi(0f);
				
			}
			if (!flag2) {// 如果不是当月则清零
				saveTrafficCurrent(0f);
				saveTrafficToday(0f);
				saveTrafficMonth(0f);
				
				currentMobile=0f;
				currentWifi=0f;
				saveDayMobile(0f);
				saveDayWifi(0f);
				saveMonthMobile(0f);
				saveMonthMobile(0f);
			}

			float newTraffic = getTotlaByUid(uid);
			float current;
			float today ;
			float month;
			float total;
			if(isWifi){//统计wifi流量
				float addWifi = newTraffic - TrafficHelper.newTraffic;// 增加的流量
				currentWifi+=addWifi;//本次流量
				currentWifi=(float) (Math.round(currentWifi * tmp))/ tmp;//确定精度
				TrafficHelper.newTraffic=newTraffic;
				float todayWifi = getDayWifi() + addWifi;
				float monthWifi = getMonthWifi() + addWifi;
				float totalWifi = getTotalWifi() + addWifi;
				
				 current=getTrafficCurrent()+addWifi;
				 today = getTrafficToday() + addWifi;
				 month = getTrafficMonth() + addWifi;
				 total = getTrafficTotal() + addWifi;
				
				saveDayWifi(todayWifi);
				saveMonthWifi(monthWifi);
				saveTotalWifi(totalWifi);
				
			}else{//统计移动流量
				float addMobile = newTraffic - TrafficHelper.newTraffic;// 增加的流量
				currentMobile+=addMobile;//本次流量
				currentMobile=(float) (Math.round(currentMobile * tmp))/ tmp;//确定精度
				TrafficHelper.newTraffic=newTraffic;
				float todayMobile = getDayMobile() + addMobile;
				float monthMobile = getMonthMobile() + addMobile;
				float totalMobile = getTotalMobile() + addMobile;
				
				 current=getTrafficCurrent()+addMobile;
				 today = getTrafficToday() + addMobile;
				 month = getTrafficMonth() + addMobile;
				 total = getTrafficTotal() + addMobile;
				
				saveDayMobile(todayMobile);
				saveMonthMobile(monthMobile);
				saveTotalMobile(totalMobile);
				
			}
			
			//保存总流量
			saveTrafficCurrent(current);
			saveTrafficToday(today);
			saveTrafficMonth(month);
			saveTrafficTotal(total);
		}
	}
	
	/**
	 * 在需要开始统计流量的地方执行以下方法
	 */
	public  void startStatistic(int uid) {
		if(uid!=-1){
			TrafficHelper.uid=uid;
			saveTrafficCurrent(0f);//开始时本次清零
			float startTraffic=getTotlaByUid(uid);
			TrafficHelper.firstTraffic=startTraffic;
			TrafficHelper.newTraffic=startTraffic;
		}
	}
	
	public void saveTrafficDateDay(Date date) {
		editor.putString(TRAFFIC_DATE_DAY, sdf.format(date));
		editor.commit();
	}

	public String getTrafficDateDay() {
		return mSharedPreferences.getString(TRAFFIC_DATE_DAY,
				sdf.format(new Date()));
	}

	public void saveTrafficCurrent(float trafficCurrent) {

		editor.putFloat(TRAFFIC_CURRENT,
				(float) (Math.round(trafficCurrent * tmp)) / tmp);
		editor.commit();
	}

	public float getTrafficCurrent() {
		return mSharedPreferences.getFloat(TRAFFIC_CURRENT, 0f);
	}

	public void saveTrafficToday(float trafficToday) {
		editor.putFloat(TRAFFIC_TODAY, (float) (Math.round(trafficToday * tmp))
				/ tmp);
		editor.commit();
	}

	public float getTrafficToday() {
		return mSharedPreferences.getFloat(TRAFFIC_TODAY, 0f);
	}

	public void saveTrafficMonth(float trafficMonth) {
		editor.putFloat(TRAFFIC_MONTH, (float) (Math.round(trafficMonth * tmp))
				/ tmp);
		editor.commit();
	}

	public float getTrafficMonth() {
		return mSharedPreferences.getFloat(TRAFFIC_MONTH, 0f);
	}

	public void saveTrafficTotal(float trafficTotal) {
		editor.putFloat(TRAFFIC_TOTAL, (float) (Math.round(trafficTotal * tmp))
				/ tmp);
		editor.commit();
	}

	public float getTrafficTotal() {
		return mSharedPreferences.getFloat(TRAFFIC_TOTAL, 0f);
	}

	public void saveDayMobile(float dayMobile) {
		editor.putFloat(DAY_MOBILE,(float) (Math.round(dayMobile * tmp))
				/ tmp);
		editor.commit();
	}

	public float getDayMobile() {
		return mSharedPreferences.getFloat(DAY_MOBILE, 0f);
	}

	public void saveDayWifi(float dayWifi) {
		editor.putFloat(DAY_WIFI,(float) (Math.round(dayWifi * tmp))
				/ tmp);
		editor.commit();
	}

	public float getDayWifi() {
		return mSharedPreferences.getFloat(DAY_WIFI, 0f);
	}

	public void saveMonthMobile(float monthMobile) {
		editor.putFloat(MONTH_MOBILE, (float) (Math.round(monthMobile * tmp))
				/ tmp);
		editor.commit();
	}

	public float getMonthMobile() {
		return mSharedPreferences.getFloat(MONTH_MOBILE, 0f);
	}

	public void saveMonthWifi(float monthWifi) {
		editor.putFloat(MONTH_WIFI, (float) (Math.round(monthWifi * tmp))
				/ tmp);
		editor.commit();
	}

	public float getMonthWifi() {
		return mSharedPreferences.getFloat(MONTH_WIFI, 0f);
	}

	public void saveTotalMobile(float totalMobile) {
		editor.putFloat(TOTAL_MOBILE,(float) (Math.round(totalMobile * tmp))
				/ tmp);
		editor.commit();
	}

	public float getTotalMobile() {
		return mSharedPreferences.getFloat(TOTAL_MOBILE, 0f);
	}

	public void saveTotalWifi(float totalWifi) {
		editor.putFloat(TOTAL_WIFI, (float) (Math.round(totalWifi * tmp))
				/ tmp);
		editor.commit();
	}

	public float getTotalWifi() {
		return mSharedPreferences.getFloat(TOTAL_WIFI, 0f);
	}

}
