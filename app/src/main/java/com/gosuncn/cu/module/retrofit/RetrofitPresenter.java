package com.gosuncn.cu.module.retrofit;

import com.gosuncn.core.utils.L;
import com.gosuncn.cu.net.TestService;

import org.json.JSONObject;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hwj on 2016/7/12.
 */
public class RetrofitPresenter implements RetrofitContract.Presenter {

    private RetrofitContract.View view;
    private TestService testService;

    @Inject
    public RetrofitPresenter(RetrofitContract.View view,TestService testService) {
        this.view = view;
        this.testService = testService;
    }

    /**
     * 初始化
     */
    @Override
    public void init() {
        view.showHint("init");
        testService.getWeb().enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                L.e("123456", response.body().toString());
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                L.e("123456", t.toString());
            }
        });


    }

    /**
     * 退出释放相关资源
     */
    @Override
    public void exit() {
    }
}
