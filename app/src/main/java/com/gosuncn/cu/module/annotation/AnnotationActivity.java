package com.gosuncn.cu.module.annotation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gosuncn.cu.R;

/**
 * android注解
 * 推荐在代码中使用，可以提供良好的可视性
 */
public class AnnotationActivity extends AppCompatActivity {
    AnnotationTest annotationTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        annotationTest=new AnnotationTest();
        annotationTest.setType(AnnotationTest.WINTER);

        //这样也能编译通过，但明显不如上一种好
        int type=0;
        annotationTest.setType(type);
    }
}
