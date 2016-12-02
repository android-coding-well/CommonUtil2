package com.gosuncn.core.adapter.recycler;

import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * 此类是对RecyclerView.Adapter的进一步封装（可简化代码量）
 * 数据结构为List，提供addAll(),add(),clear()接口，非List结构的此类仅作为参考
 * 如果是替代ListView,那么此类显然非常合适
 * 此类与BaseRecyclerAdapter不同的是对ViewHolder并没有进行封装，可以根据实际情况选择使用BaseRecyclerAdapter或BaseRecyclerAdapter2
 *
 * @param <T>  数据类型
 * @param <VH> ViewHolder
 */
public abstract class BaseRecyclerAdapter2<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public interface OnItemClickListener<T> {
        void onItemClick(View v, int position, T t);
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(View v, int position, T t);
    }

    private List<T> list = new ArrayList<T>();
    private OnItemClickListener<T> onItemClickListener;
    private OnItemLongClickListener<T> onItemLongClickListener;
    private LayoutInflater layoutInflater;

    public BaseRecyclerAdapter2(@NonNull Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        onItemLongClickListener = listener;
    }

    /**
     * 获得itemview
     *
     * @param parent
     * @return
     */
    public View getItemLayoutView(ViewGroup parent) {
        return layoutInflater.inflate(getItemLayoutResId(), parent, false);
    }


    @Override
    public void onBindViewHolder(VH holder, final int position) {
        final T t = list.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position, t);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(v, position, t);
                }
                return true;
            }
        });
        convert(holder, position, t);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAllData(@NonNull List<T> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(@NonNull T t) {
        this.list.add(t);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return this.list;
    }


    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 设置控件的显示内容
     *
     * @param holder
     * @param position
     * @param t
     */
    public abstract void convert(VH holder, int position, T t);


    /**
     * 在此处返回item布局id
     *
     * @return
     */
    @CheckResult
    public abstract int getItemLayoutResId();

}