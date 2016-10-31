package com.gosuncn.core.adapter.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 对RecyclerView.Adapter的封装，使其更易用，此类需要与RecyclerViewHolder一起使用
 * 使用时只需继承此类，然后实现抽象方法即可
 *
 * @param <T>
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    private static final String TAG = "BaseRecyclerAdapter";
    public Context context;
    public List<T> list;
    private OnItemClickListener mOnItemClickLitener;
    private OnItemLongClickListener mOnItemLongClickLitener;

    public BaseRecyclerAdapter(Context context, @NonNull List<T> list) {
        this.context = context.getApplicationContext();
        this.list = list;
    }

    /**
     * 设置监听器
     *
     * @param mOnItemClickLitener
     */
    public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    /**
     * 设置监听器
     *
     * @param mOnItemLongClickLitener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickLitener) {
        this.mOnItemLongClickLitener = mOnItemLongClickLitener;
    }

    /**
     * 在此处返回item布局id
     *
     * @return
     */
    public abstract int getItemLayoutResId();

    /**
     * 设置控件的显示内容
     *
     * @param holder
     * @param t
     */
    public abstract void convert(RecyclerViewHolder holder, int position, T t);

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder holder = new RecyclerViewHolder(LayoutInflater.from(
                context).inflate(getItemLayoutResId(), parent,
                false));
        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {

        convert(holder, position, list.get(position));
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }
        if (mOnItemLongClickLitener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemLongClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


}
