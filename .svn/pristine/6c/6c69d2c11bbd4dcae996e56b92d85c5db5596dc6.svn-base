package com.gosuncn.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * 为了解决在scrollview中listview的item中的TextView只计算一行高度而导致显示不全的问题
 * @author HWJ
 *
 */
public class CustomListView extends ListView {

	public CustomListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public CustomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	
	@Override  
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
	    int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
	            MeasureSpec.AT_MOST);  
	    super.onMeasure(widthMeasureSpec, expandSpec);  
	}  
}
