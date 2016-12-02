package com.gosuncn.cu.module.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;

import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.cu.R;
import com.gosuncn.cu.bean.databinding.User;
import com.gosuncn.cu.bean.databinding.User2;
import com.gosuncn.cu.databinding.ActivityDatabindingBinding;

/**
 * 使用databinding前，在build.gradle中配置
 * dataBinding {
 * enabled = true
 * }
 * databinding例子，目前为止，ide对数据绑定的支持并不完善<br/>
 * 有时写法并没有任何问题，可ide却提示错误，但却也能正常编译<br/>
 * 但无论如何，databinding都是google未来实现MVVP模式最重要的一环
 */
public class DataBindingActivity extends BaseActivity {

    private User user;
    private User2 user2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActivityDatabindingBinding类是自动生成的，命名规则是布局文件名+后缀Binding,如R.layout.activity_main就是ActivityMainBinding
        final ActivityDatabindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);


        user = new User();
        user2 = new User2();
        user.name.set("我是张三");
        user2.setName("我是张三");
        binding.setUser(user);
        binding.setUser2(user2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                user.name.set("我变成李四了");
                user2.setName("我变成李四了");
            }
        }, 3000);
    }

    /**
     * 在此处处理intent传值
     */
    @Override
    protected void processExtraData() {

    }
}
