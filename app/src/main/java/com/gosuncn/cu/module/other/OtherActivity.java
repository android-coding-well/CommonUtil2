package com.gosuncn.cu.module.other;

import android.os.Bundle;

import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.core.utils.helpers.VersionUpdateManager;
import com.gosuncn.cu.R;

public class OtherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();//测试全屏
        showShortToast("屏幕宽高：" + getScreenWidth() + "*" + getScreenHeight());
        setContentView(R.layout.activity_other);
        VersionUpdateManager.getInstance().checkUpdateWithDialog(this);//测试版本更新
    }
}
