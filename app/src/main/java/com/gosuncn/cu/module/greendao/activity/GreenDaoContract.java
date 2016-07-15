package com.gosuncn.cu.module.greendao.activity;

import com.gosuncn.core.base.BasePresenter;
import com.gosuncn.core.base.BaseView;

/**
 * Created by hwj on 2016/7/8.
 */
public class GreenDaoContract {

    public interface View extends BaseView<Presenter> {
        /**
         * 获得输入的用户id
         *
         * @return
         */
        String getUserId();

        /**
         * 获得输入的用户名称
         *
         * @return
         */
        String getUserName();

        /**
         * 获得输入的用户年龄
         *
         * @return
         */
        String getUserAge();

        /**
         * 设置用户id
         *
         * @return
         */
        void setUserId(String id);

        /**
         * 设置用户名称
         *
         * @param name
         */
        void setUserName(String name);

        /**
         * 设置用户年龄
         *
         * @param age
         */
        void setUserAge(String age);

        /**
         * 设置记录总数
         *
         * @param count
         */
        void setRecordCount(long count);
    }

    public interface Presenter extends BasePresenter<View> {

        /**
         * 保存用户数据
         */
        void saveUser();

        /**
         * 删除用户数据
         */
        void deleteUser();

        /**
         * 更新用户信息
         */
        void updateUser();

        /**
         * 加载用户数据
         */
        void loadUser();

        /**
         * 显示总记录数
         */
        void showRecordCount();


    }


}
