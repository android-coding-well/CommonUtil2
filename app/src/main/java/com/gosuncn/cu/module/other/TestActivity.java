package com.gosuncn.cu.module.other;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.core.utils.CharacterParserUtil;
import com.gosuncn.core.utils.ColorPhrase;
import com.gosuncn.core.utils.DoubleClickExitDetector;
import com.gosuncn.core.widget.FlowLayout;
import com.gosuncn.cu.R;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by mj on 2016/7/25.
 */
public class TestActivity extends BaseActivity {

    private Context mContext;

    private FlowLayout flowLayout;
    DoubleClickExitDetector doubleClickExitDetector;
    TextView tvColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContext = this;
        flowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        tvColor = (TextView) findViewById(R.id.tv_color);
        addFlowView();
        doubleClickExitDetector = new DoubleClickExitDetector(this);
        CharSequence format = ColorPhrase.from("这是一个{ColorPhrase}类,可以将{指定的字符串}用一对标识标记出来，并且可以分别设置{不同颜色}。\n 另外这里增加了{双击退出提示器DoubleClickExitDetector},在{onBackPressed}中调用{click},返回值为{true}则可以{退出}该activity")
                .innerColor(Color.parseColor("#ff0000"))
                .outerColor(Color.parseColor("#0000ff"))
                .withSeparator("{}")
                .format();
        tvColor.setText(format);

        Toast.makeText(TestActivity.this, "汉字转拼音:"+CharacterParserUtil.getSpelling("汉字转拼音"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (doubleClickExitDetector.click()) {
            super.onBackPressed();
        }
    }

    private void addFlowView() {
        for (int i = 0; i < 4; i++) {
            TextView tv = new TextView(this);
            tv.setText("动态添加" + i);
            flowLayout.addView(tv);
        }
    }

    public void onNotificationClick(View view) {
        switch (view.getId()) {
            case R.id.button1:

                break;
            case R.id.button2:

                break;
            case R.id.button3:

                break;
            case R.id.button4:

                break;
            case R.id.button5:

                break;
        }
        Toast.makeText(TestActivity.this, "you got me " + ((FancyButton) view).getText(), Toast.LENGTH_SHORT).show();
    }


}
