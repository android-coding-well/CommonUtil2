package com.gosuncn.core.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IntRange;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 扩展了保存历史记录的功能
 * Created by hwj on 2016/10/31.
 */

public class AutoCompleteTextViewExtend extends AppCompatAutoCompleteTextView {

    private Set<String> set=new LinkedHashSet<>();
    private List<String> list=new ArrayList<>();
    private ArrayAdapter adapter;

    private SharedPreferences sp;
    private int saveCount=10;

    public AutoCompleteTextViewExtend(Context context) {
        super(context);
        init(context);
    }

    public AutoCompleteTextViewExtend(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoCompleteTextViewExtend(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private  void init(final Context context) {
        sp=context.getSharedPreferences(getId()+"",Context.MODE_PRIVATE);
        set.addAll(sp.getStringSet(getId()+"",set));
        list.addAll(set);
        setAdapter(adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, list));
    }

    public List<String> getData(){
        return list;
    }

    /**
     * 设置保存的条数
     * @param count
     */
    public void setSaveCount(@IntRange(from = 0) int count){
        this.saveCount=count;
    }

    /**
     * 保存提示
     * @param record
     * @return
     */
    public boolean save(String record){
        if(!TextUtils.isEmpty(record)){
            int oldSize=set.size();
            set.add(record);
            int newSize=set.size();
            if(newSize!=oldSize&&set.size()>saveCount){
                set.remove(set.toArray()[0]);
            }
            list.clear();
            list.addAll(set);
            setAdapter(adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, list));
            SharedPreferences.Editor editor=sp.edit();
            editor.putStringSet(getId()+"",set);
            editor.commit();
            return true;
        }
        return false;
    }

    /**
     * 保存提示
     * @return
     */
    public boolean save(){
        String item=getText().toString();
        return save(item);
    }

    /**
     * 清除保存记录
     */
    public void clear(){
        list.clear();
        set.clear();
        setAdapter(adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, list));
        SharedPreferences.Editor editor=sp.edit();
        editor.remove(getId()+"");
        editor.commit();
    }

}
