package com.gosuncn.core.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

public class ViewHolder {

	
	private View convertView;
	private SparseArray<View> views;//存放控件
	private int position;
	private ViewHolder(Context context,int position, ViewGroup parent,int layoutId){
		this.position=position;
		this.views =new SparseArray<View>();
		this.convertView=LayoutInflater.from(context).inflate(layoutId, parent,false);
		convertView.setTag(this);
		
	}
	/**
	 * 获得viewHolder
	 * @param context
	 * @param position
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @return
	 */
	public static ViewHolder getViewHolder(Context context,int position, View convertView, ViewGroup parent,int layoutId){
		
		if(convertView==null){
			return new ViewHolder(context,position,parent,layoutId);
		}else{
			ViewHolder viewHolder=(ViewHolder) convertView.getTag();
			//由于viewholder是复用的，因此需要修改position
			viewHolder.position =position;
			return viewHolder;
		}
	}
	
	
	/**
	 * 获得控件
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId){
		View view=views.get(viewId);
		if(view==null){
			view=convertView.findViewById(viewId);
			views.put(viewId, view);
		}
		return (T) view;
	}
	
	
	/**
     * 获得convertView
     * @return
     */
    public View getConvertView(){
        return convertView;
    }
    /**
     * 获得position
     * @return
     */
    public int getPosition(){
        return position;
    }
    
    //-------------------------TextView-----------------------
    /**
     * 设置TextView的内容
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(@IdRes int viewId, CharSequence text){
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
    public ViewHolder setText(@IdRes int viewId,@StringRes int textId) {
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
    public ViewHolder setTextColor(@IdRes int viewId,int color){
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
    public ViewHolder setCompoundDrawablesWithIntrinsicBounds(@IdRes int viewId,Drawable left, Drawable top, Drawable right, Drawable bottom){
      	 TextView tv=getView(viewId);
      	 tv.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        return this;
      }
    
    //----------------------公共-----------------------
    public ViewHolder setVisibility(@IdRes int viewId,int visibility){
    	
   	 	getView(viewId).setVisibility(visibility);
        return this;
   }
    
    public ViewHolder setBackgroundResource(@IdRes int viewId, int resid){
    	
   	 	getView(viewId).setBackgroundResource(resid);
        return this;
   }
    
 public ViewHolder setBackgroundDrawable(@IdRes int viewId,Drawable background){
   	 	getView(viewId).setBackgroundDrawable(background);
        return this;
   }
    
    public ViewHolder setOnClickListener(@IdRes int viewId,OnClickListener  listener){
        View iv=getView(viewId);
        iv.setOnClickListener(listener);
        return this;
    }
    
    
    public ViewHolder setCheckedOfToggleButton(@IdRes int viewId,boolean checked){
        ToggleButton iv=getView(viewId);
        iv.setChecked(checked);
        return this;
    }
    public ViewHolder setOnCheckedChangeListenerOfCheckBox(@IdRes int viewId,OnCheckedChangeListener listener){
        CheckBox iv=getView(viewId);
        iv.setOnCheckedChangeListener(listener);
        return this;
    }
    
    public ViewHolder setCheckedOfCheckBox(@IdRes int viewId,boolean checked){
        CheckBox iv=getView(viewId);
        iv.setChecked(checked);
        return this;
    }
    
    
    

//----------------------ImageView-----------------------------
    public ViewHolder setImageBitmap(@IdRes int viewId,Bitmap bitmap){
        ImageView iv=getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }
    public ViewHolder setImageDrawable(@IdRes int viewId,Drawable drawable){
        ImageView iv=getView(viewId);
        iv.setImageDrawable(drawable);
        return this;
    }
    public ViewHolder setImageResource(@IdRes int viewId,int resourceId){
        ImageView iv=getView(viewId);
        iv.setImageResource(resourceId);
        return this;
    }
    public ViewHolder setImageURI(@IdRes int viewId,Uri uri){
        ImageView iv=getView(viewId);
        iv.setImageURI(uri);
        return this;
    }

    /**
     * 网络加载图片
     * @param viewId
     * @param context
     * @param uri               图片的网络地址
     * @param defResourceId     默认显示的图片
     * @return
     */
    public ViewHolder displayImage(@IdRes  int viewId,Context context,String uri,@DrawableRes  int defResourceId){
        Glide.with(context).load(uri).error(defResourceId).into((ImageView)getView(viewId));
        return this;
    }

}
