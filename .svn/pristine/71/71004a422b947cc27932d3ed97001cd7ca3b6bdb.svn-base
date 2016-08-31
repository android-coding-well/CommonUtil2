package com.gosuncn.cu.module.retrofit;

import android.os.Bundle;

import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.cu.R;
import com.gosuncn.cu.app.MyApplication;

import javax.inject.Inject;

/**
 * http://blog.csdn.net/lmj623565791/article/details/51304204
 */
public class RetrofitActivity extends BaseActivity implements RetrofitContract.View {

    @Inject
    RetrofitPresenter retrofitPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        //  MyApplication.getInstance().getNetComponent().inject(this);
        DaggerRetrofitComponent.builder()
                .appComponent(MyApplication.getInstance().getAppComponent())
                .retrofitModule(new RetrofitModule(this))
                .build().inject(this);
        //retrofitPresenter.attachView(this);
        retrofitPresenter.init();
    }

    /**
     * 设置presenter，在使用依赖注入后此方法可以直接忽略
     *
     * @param presenter
     */
    @Override
    public void setPresenter(RetrofitContract.Presenter presenter) {

    }

    /**
     * 显示提示内容
     *
     * @param hint
     */
    @Override
    public void showHint(String hint) {
        showShortToast(hint);
    }
}
