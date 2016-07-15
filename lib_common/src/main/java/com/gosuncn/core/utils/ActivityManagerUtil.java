package com.gosuncn.core.utils;

import java.util.Stack;

import android.app.Activity;
/**
 * ActivityManager,用于退出APP
 * @author HWJ
 *
 */
public class ActivityManagerUtil {
	private static Stack<Activity> activityStack;
	private static ActivityManagerUtil instance;

	private ActivityManagerUtil() {
	}

	public static ActivityManagerUtil getInstance() {
		if (instance == null) {
			instance = new ActivityManagerUtil();
		}
		return instance;
	}

	// 退出栈顶Activity
	public void popActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}
	}

	// 获得当前栈顶Activity
	public Activity currentActivity() {
		Activity activity = null;
		if (activityStack.size() > 0) {
			activity = activityStack.lastElement();
		}
		return activity;
	}

	// 将当前Activity推入栈中
	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	// 退出栈中所有Activity除了设置的activity
	public void popAllActivityExceptionOne(Class cls) {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(cls)) {
				break;
			}
			popActivity(activity);
		}
	}

	// 退出栈中所有Activity
	public void popAllActivity() {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			popActivity(activity);
		}
	}

}
