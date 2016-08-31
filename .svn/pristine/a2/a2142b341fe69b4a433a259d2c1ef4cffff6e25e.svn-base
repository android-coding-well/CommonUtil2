package com.gosuncn.core.adapter.recycler;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 与BaseRecyclerAdapter配合使用
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;//存放控件
    public RecyclerViewHolder(View itemView) {
        super(itemView);
        this.views =new SparseArray<View>();
    }

    /**
     * 获得控件
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view=views.get(viewId);
        if(view==null){
            view=itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }


    //************************扩展功能函数，方便使用，可在此自定义更多控件的设置******************************
    /**
     * 设置TextView的内容
     * @param viewId
     * @param text
     * @return
     */
    public RecyclerViewHolder setText(int viewId,CharSequence text){
        TextView tv=getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置TextView的内容
     * @param viewId
     * @param textId
     * @return
     * @throws Exception
     */
    public RecyclerViewHolder setText(int viewId,int textId) {
        //try{
        TextView tv=getView(viewId);
        tv.setText(textId);
        //}catch(ClassCastException e){
        //	throw new Exception("类型转换错误，请确保传入的是TextView的viewId");
        //}
        return this;
    }

    /**
     *  设置TextView的颜色
     * @param viewId
     * @param color
     * @return
     */
    public RecyclerViewHolder setTextColor(int viewId,int color){
        TextView tv=getView(viewId);
        tv.setTextColor(color);
        return this;
    }


    /**
     * 设置textView的图片
     * @param viewId
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public RecyclerViewHolder setCompoundDrawablesWithIntrinsicBounds(int viewId,Drawable left, Drawable top, Drawable right, Drawable bottom){
        TextView tv=getView(viewId);
        tv.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        return this;
    }

    //----------------------公共-----------------------
    public RecyclerViewHolder setVisibility(int viewId,int visibility){

        getView(viewId).setVisibility(visibility);
        return this;
    }

    public RecyclerViewHolder setBackgroundResource(int viewId,int resid){

        getView(viewId).setBackgroundResource(resid);
        return this;
    }

    public RecyclerViewHolder setBackgroundDrawable(int viewId,Drawable background){
        getView(viewId).setBackgroundDrawable(background);
        return this;
    }

    public RecyclerViewHolder setOnClickListener(int viewId,View.OnClickListener listener){
        View iv=getView(viewId);
        iv.setOnClickListener(listener);
        return this;
    }


    public RecyclerViewHolder setCheckedOfToggleButton(int viewId,boolean checked){
        ToggleButton iv=getView(viewId);
        iv.setChecked(checked);
        return this;
    }
    public RecyclerViewHolder setOnCheckedChangeListenerOfCheckBox(int viewId,CompoundButton.OnCheckedChangeListener listener){
        CheckBox iv=getView(viewId);
        iv.setOnCheckedChangeListener(listener);
        return this;
    }

    public RecyclerViewHolder setCheckedOfCheckBox(int viewId,boolean checked){
        CheckBox iv=getView(viewId);
        iv.setChecked(checked);
        return this;
    }




    //----------------------ImageView-----------------------------
    public RecyclerViewHolder setImageBitmap(int viewId,Bitmap bitmap){
        ImageView iv=getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }
    public RecyclerViewHolder setImageDrawable(int viewId,Drawable drawable){
        ImageView iv=getView(viewId);
        iv.setImageDrawable(drawable);
        return this;
    }
    public RecyclerViewHolder setImageResource(int viewId,int resourceId){
        ImageView iv=getView(viewId);
        iv.setImageResource(resourceId);
        return this;
    }
    public RecyclerViewHolder setImageURI(int viewId,Uri uri){
        ImageView iv=getView(viewId);
        iv.setImageURI(uri);
        return this;
    }
}
