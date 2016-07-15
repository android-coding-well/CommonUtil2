package com.gosuncn.core.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.gosuncn.core.widget.HorizontalListView;

public class ListViewUtil {

	/**
	 * 计算listview的显示高度，以解决与scrollview冲突的问题
	 * 与customListView配合使用
	 * 在添加数据后调用
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(final ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	/**
	 * 计算HorizontalListView的显示高度（一行），以解决与scrollview冲突的问题
	 * 与HorizontalListView配合使用
	 * @param horizontalListView
	 */
	public static void setHorizontalListViewHeightBasedOnChildren(
			HorizontalListView horizontalListView) {

		// 获取ListView对应的Adapter
		ListAdapter listAdapter = horizontalListView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;

		if (listAdapter.getCount() != 0) {
			View listItem = listAdapter.getView(0, null, horizontalListView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = horizontalListView.getLayoutParams();
		params.height = totalHeight;
		horizontalListView.setLayoutParams(params);
	}
}
