package com.gosuncn.core.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
/**
 * 虚拟键盘辅助类
 * @author HWJ
 *
 */
public class KeyboardUtil {
	
	private KeyboardUtil() {
		throw new UnsupportedOperationException(
				"sorry,KeyboardUtil cannot be instantiated");
	}
	/**
	 * 打开软键盘
	 * @param editText
	 * @param context
	 */
	public static void showSoftInputView(EditText editText, Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 隐藏虚拟键盘
	 * @param editText
	 * @param context
	 */
	public static void hideSoftInputView(EditText editText, Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	/**
	 * 隐藏虚拟键盘
	 * @param activity
	 */
	public static void hideSoftInputView(Activity activity) {
		View currentFoucsView = activity.getCurrentFocus();
		if (currentFoucsView != null) {
			((InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(currentFoucsView.getWindowToken(),
							0);
		}
	}
}
