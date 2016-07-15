package com.gosuncn.cu.bean.databinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * 数据绑定第二种bean类
 */
public class User2 extends BaseObservable {
    private String name;
    private int age;
    private boolean sex;

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(com.gosuncn.cu.BR.age);
    }
    @Bindable
    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
        notifyPropertyChanged(com.gosuncn.cu.BR.sex);
    }
    @Bindable
    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.gosuncn.cu.BR.name);
    }
}
