package com.gosuncn.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
/**
 * 自定义gridview，解决与scrollview冲突的问题
 * @author HWJ
 *
 */
public class CustomGridView extends GridView {
	public CustomGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomGridView(Context context) {
		super(context);
	}

	public CustomGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 *  设置不滚动,即显示全部
	 */
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
