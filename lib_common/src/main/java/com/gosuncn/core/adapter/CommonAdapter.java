package com.gosuncn.core.adapter;

import android.content.Context;
import android.support.annotation.CheckResult;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 通用Adapter
 * 内部封装了ViewHolder，只需继承并实现抽象方法即可
 * 请使用com.gosuncn.core.adapter.common.CommonAdapter代替
 * @param <T>
 * @author HWJ
 */

@Deprecated
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected List<T> list;
    protected Context context;

    public CommonAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        if (getCount() == 0) {
            return null;
        }
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(context, position, convertView, parent, getItemLayoutResId());
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    /**
     * 设置控件的显示内容
     *
     * @param viewHolder
     * @param t
     */
    public abstract void convert(ViewHolder viewHolder, T t);

    /**
     * 在此处返回item布局id
     *
     * @return
     */
    @CheckResult
    public abstract int getItemLayoutResId();


}
