package com.gosuncn.cu.db;

import android.content.Context;

/**
 * Created by hwj on 2016/7/12.
 */
public class DBManager {
    private DaoMaster.DevOpenHelper helper;
    private DaoSession daoSession;
    private final static String DB_NAME="my-db";
    public DBManager(Context context){
         helper= new DaoMaster.DevOpenHelper(context, DB_NAME, null);
         daoSession=new DaoMaster(helper.getWritableDatabase()).newSession();
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }

    public void close(){
        if(helper!=null){
            helper.close();
        }
        if(daoSession!=null){
            daoSession.clear();
        }
    }


}
