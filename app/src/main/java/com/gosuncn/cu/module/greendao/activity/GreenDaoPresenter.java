package com.gosuncn.cu.module.greendao.activity;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.gosuncn.core.utils.L;
import com.gosuncn.cu.bean.common.Person;
import com.gosuncn.cu.db.DBManager;
import com.gosuncn.cu.db.dao.UserDao;
import com.gosuncn.cu.db.entity.User;
import com.gosuncn.cu.di.scope.ActivityScope;

import javax.inject.Inject;

/**
 * Created by hwj on 2016/7/8.
 */
@ActivityScope
public class GreenDaoPresenter implements GreenDaoContract.Presenter {
    GreenDaoContract.View view;
    UserDao userDao;
    DBManager dbManager;

    @Inject
    Person person;


    //Lazy<DBManager>是懒加载注入的写法只有一个get方法
    //如果不调用，dagger不会创建对象
    //调用之后，创建对象并保存
    //再次调用，返回相同对象
    //标注有@Inject的执行顺序是先构造函数，再成员变量，再执行方法（多个方法按在代码中的先后顺序执行），在这里体现为先GreenDaoPresenter，再person,再执行init
    @Inject
    public GreenDaoPresenter(@NonNull GreenDaoContract.View view,DBManager dbManager) {
        this.view = view;
        L.e("123456","GreenDaoPresenter");
        //person.name="2";//由于是先执行构造，因此这句会报空指针遗产
        this.dbManager = dbManager;//new DBManager(MyApplication.getInstance().getApplicationContext());
        userDao = dbManager.getDaoSession().getUserDao();
    }

    @Override
    @Inject
    public void init() {
        L.e("123456","init");
    }


    /**
     * 退出释放相关资源
     */
    @Override
    public void exit() {
        L.e("123456","exit");
        if (dbManager != null) {
            dbManager.close();
        }
    }


    /**
     * 保存用户数据
     */
    @Override
    public void saveUser() {
        if (TextUtils.isEmpty(view.getUserName())) {
            view.showHint("姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(view.getUserAge())) {
            view.showHint("年龄不能为空");
            return;
        }
        User user = new User();
        user.setName(view.getUserName());
        user.setAge(Integer.parseInt(view.getUserAge()));
        try {
            userDao.insert(user);
            view.showHint("插入成功");
        } catch (Exception e) {
            e.printStackTrace();
            view.showHint("插入失败");
        }

    }

    /**
     * 删除用户数据
     */
    @Override
    public void deleteUser() {

        if (TextUtils.isEmpty(view.getUserId())) {
            view.showHint("id不能为空");
            return;
        }
        User user = new User(Long.parseLong(view.getUserId()));
        try {
            userDao.delete(user);
            view.showHint("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            view.showHint("删除失败");
        }
    }

    /**
     * 更新用户信息
     */
    @Override
    public void updateUser() {
        if (TextUtils.isEmpty(view.getUserId())) {
            view.showHint("id不能为空");
            return;
        }
        if (TextUtils.isEmpty(view.getUserName())) {
            view.showHint("姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(view.getUserAge())) {
            view.showHint("年龄不能为空");
            return;
        }
        User user = new User(Long.parseLong(view.getUserId()));
        user.setName(view.getUserName());
        user.setAge(Integer.parseInt(view.getUserAge()));
        try {
            userDao.update(user);
            view.showHint("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            view.showHint("更新失败");
        }
    }

    /**
     * 加载用户数据
     */
    @Override
    public void loadUser() {
        if (TextUtils.isEmpty(view.getUserId())) {
            view.showHint("id不能为空");
            return;
        }
        try {
            User user = userDao.load(Long.parseLong(view.getUserId()));
            if (user != null) {
                view.setUserName(user.getName());
                view.setUserAge(user.getAge() + "");
            } else {
                view.showHint("查询不到");
            }

        } catch (Exception e) {
            e.printStackTrace();
            view.showHint("查询失败");
        }
    }

    /**
     * 显示总记录数
     */
    @Override
    public void showRecordCount() {
        view.setRecordCount(userDao.count());
    }
}
