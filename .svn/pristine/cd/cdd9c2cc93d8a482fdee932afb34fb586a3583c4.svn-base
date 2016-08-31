package com.gosuncn.cu.module.greendao.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.cu.R;
import com.gosuncn.cu.app.MyApplication;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 简单演示了greenDAO的增删查改
 * 更多猛戳：http://greenrobot.org/greendao/documentation/how-to-get-started/
 */
public class GreenDaoActivity extends BaseActivity implements GreenDaoContract.View {
    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @Inject
    GreenDaoPresenter greenDaoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        ButterKnife.bind(this);
        //MyApplication.getInstance().getActivityComponent(this).inject(this);
        DaggerGreenDaoComponent.builder().appComponent(MyApplication.getInstance().getAppComponent()).greenDaoModule(new GreenDaoModule(this)).build().inject(this);
        //greenDaoPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        greenDaoPresenter.exit();
        super.onDestroy();
    }

    /**
     * 在此处处理intent传值
     */
    @Override
    protected void processExtraData() {
    }

    public void onGetFromDBClick(View view) {
        greenDaoPresenter.loadUser();
    }

    public void onSaveToDBClick(View view) {
        greenDaoPresenter.saveUser();
    }

    public void onUpdateToDBClick(View view) {
        greenDaoPresenter.updateUser();
    }

    public void onDeleteFromDBClick(View view) {
        greenDaoPresenter.deleteUser();
    }

    public void onQueryCountClick(View view) {
        greenDaoPresenter.showRecordCount();
    }


    @Override
    public void setPresenter(GreenDaoContract.Presenter presenter) {
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

    /**
     * 获得输入的用户id
     *
     * @return
     */
    @Override
    public String getUserId() {
        return etId.getText().toString();
    }

    /**
     * 获得输入的用户名称
     *
     * @return
     */
    @Override
    public String getUserName() {
        return etName.getText().toString();
    }

    /**
     * 获得输入的用户年龄
     *
     * @return
     */
    @Override
    public String getUserAge() {
        return etAge.getText().toString();
    }

    /**
     * 设置用户id
     *
     * @return
     */
    @Override
    public void setUserId(String id) {
        etId.setText(id);
    }

    /**
     * 设置用户名称
     *
     * @param name
     */
    @Override
    public void setUserName(String name) {
        etName.setText(name);
    }

    /**
     * 设置用户年龄
     *
     * @param age
     */
    @Override
    public void setUserAge(String age) {
        etAge.setText(age);
    }

    /**
     * 设置记录总数
     *
     * @param count
     */
    @Override
    public void setRecordCount(long count) {
        tvRecord.setText("记录总数：" + count);
    }
}
