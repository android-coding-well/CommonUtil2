package com.gosuncn.core;

import android.app.Application;

import com.gosuncn.core.utils.AppUtil;
import com.gosuncn.core.utils.helpers.TrafficHelper;


public class AndroidApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
        TrafficHelper.uid=AppUtil.getUid(getApplicationContext(),BuildConfig.APPLICATION_ID);
	}

}

