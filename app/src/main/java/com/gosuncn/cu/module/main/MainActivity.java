package com.gosuncn.cu.module.main;

import android.os.Bundle;
import android.view.View;

import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.cu.R;
import com.gosuncn.cu.module.annotation.AnnotationActivity;
import com.gosuncn.cu.module.dagger2.Dagger2Activity;
import com.gosuncn.cu.module.databinding.DataBindingActivity;
import com.gosuncn.cu.module.greendao.activity.GreenDaoActivity;
import com.gosuncn.cu.module.image.ImageActivity;
import com.gosuncn.cu.module.permission.activity.PermissionActivity;
import com.gosuncn.cu.module.retrofit.RetrofitActivity;
import com.gosuncn.cu.module.rxjava.RxjavaActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 在此处处理intent传值
     */
    @Override
    protected void processExtraData() {
    }

    /**
     * 数据绑定
     *
     * @param view
     */
    public void onDatabindingClick(View view) {
        gotoActivity(DataBindingActivity.class);
    }

    /**
     * greenDAO
     *
     * @param view
     */
    public void onGreenDaoClick(View view) {
        gotoActivity(GreenDaoActivity.class);
    }

    /**
     * 运行时权限
     *
     * @param view
     */
    public void onPermissionClick(View view) {
        gotoActivity(PermissionActivity.class);
    }

    /**
     * 依赖注入
     *
     * @param view
     */
    public void onDagger2Click(View view) {
        gotoActivity(Dagger2Activity.class);
    }

    /**
     * 注解(非AndroidAnnotations)
     *
     * @param view
     */
    public void onAnnotationClick(View view) {
        gotoActivity(AnnotationActivity.class);
    }

    public void onRetrofitClick(View view) {
        gotoActivity(RetrofitActivity.class);
    }

    public void onRxjavaClick(View view) {
        gotoActivity(RxjavaActivity.class);
    }

    public void onImageClick(View view) {
        gotoActivity(ImageActivity.class);
    }
}
