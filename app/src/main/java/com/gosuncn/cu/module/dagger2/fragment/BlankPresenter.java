package com.gosuncn.cu.module.dagger2.fragment;

import com.gosuncn.cu.bean.common.Person;
import com.gosuncn.cu.db.DBManager;
import com.gosuncn.cu.di.scope.FragmentScoped;
import com.gosuncn.cu.net.TestService;

import javax.inject.Inject;

/**
 * Created by hwj on 2016/7/13.
 */
@FragmentScoped
public class BlankPresenter implements BlankContract.Presenter {

    BlankContract.View view;
    @Inject
    public BlankPresenter(BlankContract.View view,DBManager dbManager, TestService testService, Person person){
        this.view=view;
        dbManager.getDaoSession();
        testService.getWeb();
        person.name="123";
    }

    /**
     * 初始化
     */
    @Override
    public void init() {
    }

    /**
     * 退出释放相关资源
     */
    @Override
    public void exit() {

    }

    @Override
    public void test() {

        view.showHint("test");
    }
}
