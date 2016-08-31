package com.gosuncn.cu.module.animview;

import android.os.Bundle;

import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.cu.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class AnimViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_view);
        Observable.timer(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> {
            final AVLoadingIndicatorDialog dialog=new AVLoadingIndicatorDialog(this);
            dialog.setMessage("Loading...");
            dialog.show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
