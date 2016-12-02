package com.gosuncn.core.adapter.databinding;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hwj on 2016/11/9.
 */

public class BindingViewHolder <T extends ViewDataBinding>  extends RecyclerView.ViewHolder {

    private T mBinding;
    public BindingViewHolder(View itemView) {
        super(itemView);
    }

    public BindingViewHolder(T binding) {
        super(binding.getRoot());
        mBinding = binding;
    }
    public void  setBinding(T binding) {
        this.mBinding=binding;
    }
    public T getBinding() {
        return mBinding;
    }
}
