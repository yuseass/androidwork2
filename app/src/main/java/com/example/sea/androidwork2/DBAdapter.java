package com.example.sea.androidwork2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBAdapter {
    //数据库基本信息
    private static final String DB_NAME="people.db";   //数据库名
    private static final String DB_TABLE="info";  //表名
    private static final int   DB_VERSION=1;	  //数据库版本
    //字段名称
    public static  final  String KEY_ID="id";
    public static  final  String KEY_NAME="name";
    public static  final  String KEY_AGE="age";
    public static  final  String KEY_HEIGHT="height";

    private SQLiteDatabase db=null;  //数据库对象
    private Context context; //记住由构造函数传入的上下文对象

    //数据库辅助操作对象
    private MyDBOpenHelper helper;

    public DBAdapter(Context context){
        this.context=context;  //记住由构造函数传入的上下文对象
        helper=new MyDBOpenHelper(context, DB_NAME, null, DB_VERSION); //若在此处更改数据库版本,则会触发升级动作
    }

    //创建/打开数据库
    public boolean createDB(){
        try{
            //打开/创建指定文件夹下的数据库,需要相应路径的写权限
            db=helper.getWritableDatabase();
        }catch(SQLiteException e){
            return false;
        }
        return true;
    }

    //创建表--此处不需要作任何处理, 在创建数据库时表会随之创建
    public boolean createTable(){
        return true;
    }




    //添加新数据,插入3条记录
    public boolean insertAll(){
        ContentValues cv=new ContentValues();

        //设置插入的字段数值,对于自增的字段不需要设置
        cv.put(KEY_NAME, "Tom");
        cv.put(KEY_AGE, 21);
        cv.put(KEY_HEIGHT, 1.75);
        if (db.insert(DB_TABLE, null, cv)==-1)
            return false;

        cv.put(KEY_NAME, "Jack");
        cv.put(KEY_AGE, 22);
        cv.put(KEY_HEIGHT, 1.80);
        if (db.insert(DB_TABLE, null, cv)==-1)
            return false;

        cv.put(KEY_NAME, "Lily");
        cv.put(KEY_AGE, 20);
        cv.put(KEY_HEIGHT, 1.70);
        if (db.insert(DB_TABLE, null, cv)==-1)
            return false;

        return true;
    }

    //添加自定义数据
    public boolean insert(String name,int age,float height){
        ContentValues cv=new ContentValues();

        //设置插入的字段数值,对于自增的字段不需要设置
        cv.put(KEY_NAME, name);
        cv.put(KEY_AGE, age);
        cv.put(KEY_HEIGHT, height);
        if (db.insert(DB_TABLE, null, cv)==-1)
            return false;

        return true;
    }










    //查询数据库中所有数据
    public Cursor queryAll(){

        Cursor cursor=db.query(DB_TABLE,null,null,null,null,null,null);
        return cursor;

    }

    //查询数据
    public String query(int age){
        String rlt=""; //结果字符串

        //查询---可以使用占位符--SQL中的?对应后面字符串数组中的字符串,此处不用,第二个参数设置为null
        Cursor cursor=db.query(DB_TABLE,new String[]{KEY_ID,KEY_NAME,KEY_AGE,KEY_HEIGHT},
                "age=?",new String[]{""+age},null,null,null); //如果要查询的表不存在则会产生异常
        if (cursor.getCount()==0) //查询结果为空则返回
            return "查询结果为空!";
        while (cursor.moveToNext()){ //循环取出游标中的查询结果
            //取出本行各列的值
            int id=cursor.getInt(0);
            String name=cursor.getString(1);

            float height=cursor.getFloat(3);
            //写到结果字符串中
            rlt+="id："+id+"\t"+"姓名："+name+"\t"+"年龄："+age+"\t"+"身高："+height+"\n";
        }; //继续取下一行

        cursor.close(); //关闭游标

        return rlt;
    }

    //修改
    public boolean update(int id,String name,int age,float height){
        ContentValues cv=new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_AGE,age);
        cv.put(KEY_HEIGHT, height); //设定要修改的值

        if (db.update(DB_TABLE, cv, KEY_ID+"=?", new String[]{""+id})>0)
            return true;
        else
            return false;
    }

    //删除
    public boolean delete(int id){
        if (db.delete(DB_TABLE, KEY_ID+"=?", new String[]{""+id})>0)
            return true;
        else
            return false;
    }


    public boolean deleteAll(){

        db.delete(DB_TABLE,null,null);
        return true;
    }

    //删除表
    public boolean drop(){
        String dropSQL="drop table ["+DB_TABLE+"] ;";
        try{
            db.execSQL(dropSQL); //执行删除表语句
        }catch(SQLException e){
            return false;
        }

        return true;
    }

    //关闭数据库对象
    public void close(){
        if (db!=null){
            db.close();
            db=null;
        }
    }










}
