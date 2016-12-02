package com.gosuncn.core.adapter.databinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类是对RecyclerView.Adapter和DataBinding的进一步封装（可简化代码量）
 * 数据结构为List，提供addAll(),add(),clear()接口，非List结构的此类仅作为参考
 * 如果使用DataBinding框架,此类显然非常合适
 *
 * @param <T>   数据类型
 * @param <VDB> ViewDataBinding
 */
public abstract class BaseBindingRecyclerAdapter<T, VDB extends ViewDataBinding> extends RecyclerView.Adapter<BindingViewHolder<VDB>> {

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

    public BaseBindingRecyclerAdapter(@NonNull Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        onItemLongClickListener = listener;
    }

    @Override
    public BindingViewHolder<VDB> onCreateViewHolder(ViewGroup parent, int viewType) {
        VDB binding = DataBindingUtil.inflate(layoutInflater, getItemLayoutResId(), parent, false);
        return new BindingViewHolder<VDB>(binding);
    }


    @Override
    public void onBindViewHolder(BindingViewHolder<VDB> holder, final int position) {
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
        convert(holder, position, holder.getBinding(), t);
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

    public List<T> getData(){
        return this.list;
    }

    /**
     * 设置控件的显示内容
     *
     * @param holder
     * @param binding
     * @param position
     * @param t
     */
    public abstract void convert(BindingViewHolder<VDB> holder, int position, VDB binding, T t);


    /**
     * 在此处返回item布局id
     *
     * @return
     */
    @CheckResult
    public abstract int getItemLayoutResId();
}
