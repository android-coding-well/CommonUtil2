package com.gosuncn.cu.module.dagger2;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.core.utils.L;
import com.gosuncn.cu.R;
import com.gosuncn.cu.module.dagger2.fragment.BlankFragment;
import com.gosuncn.cu.net.CallbackExtend;
import com.gosuncn.cu.net.RetrofitCallbackProxy;
import com.gosuncn.cu.net.TestService;
import com.gosuncn.cu.net.TestService2;

import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 更多猛戳：http://google.github.io/dagger/users-guide
 * http://blog.csdn.net/hsk256/article/details/51530667
 * http://blog.csdn.net/u013163564/article/details/51847784
 */
public class Dagger2Activity extends BaseActivity {
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @Inject
    TestService testService;
    @Inject
    TestService2 testService2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);
        ButterKnife.bind(this);
        Dagger2Component.getInstance().inject(this);
        test();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content, BlankFragment.newInstance("", ""))
                .commitAllowingStateLoss();

    }

    private void test() {
      /*  testService2.getWeb().enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                L.e("123456", "onResponse:" + response.body().toString());
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                L.e("123456", "onFailure:" + t.toString());
            }
        });*/

        //测试扩展的回调接口
        testService2.getWeb().enqueue(new RetrofitCallbackProxy().newProxyInstance(new CallbackExtend<JSONObject>() {
            @Override
            public void onBefore() {
                L.e("123456", "onBefore:" );
            }

            @Override
            public void onAfter() {
                L.e("123456", "onAfter:" );
            }

            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                L.e("123456", "onResponse:"+response.body());
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                L.e("123456", "onFailure:" );
            }
        }));
    }

}
