package com.gosuncn.cu.db.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * greenDAO提供自动生成实体类和dao的方式
 * 以下是简单的demo演示
 */
public class GreenDaoGenerator {

    static int version=1;
    static String entityPackName="com.gosuncn.cu.db.entity";//指定实体类的位置
    static String daoPackName="com.gosuncn.cu.db.dao";//指定dao类的位置
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(version, entityPackName);
        schema.setDefaultJavaPackageDao(daoPackName);

        //下面是创建一个实体类的设置过程
        Entity entity = schema.addEntity("User");
        entity.addIdProperty();//主键
        entity.addStringProperty("name").notNull();
        entity.addIntProperty("age");
        entity.addBooleanProperty("isMan");
        entity.addDateProperty("birthday");
        entity.setClassNameDao("UserDao");//非必须，设置dao类的名称
        entity.setJavaDoc("auto greenDao generate javaBean by hwj");//非必须
        entity.setTableName("tb_user");//非必须,设置表名,默认是Entity ClassName的大写形式


        Entity entity2 = schema.addEntity("Note");
        entity2.addIdProperty();//主键
        entity2.addStringProperty("name").notNull();
        entity2.addStringProperty("content");

        //最后生成到指定位置，此位置需要根据自身项目情况设置
        new DaoGenerator().generateAll(schema, "F:\\AS_workspace3\\CommonUtil\\app\\src\\main\\java");
    }
}
