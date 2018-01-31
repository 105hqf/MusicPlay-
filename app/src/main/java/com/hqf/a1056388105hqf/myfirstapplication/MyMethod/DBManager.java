package com.hqf.a1056388105hqf.myfirstapplication.MyMethod;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hqf.a1056388105hqf.myfirstapplication.MyActivity.MyfirstAppMainActivity;
import com.hqf.a1056388105hqf.myfirstapplication.MyDBhelper.DBHelper;
import com.hqf.a1056388105hqf.myfirstapplication.Mybean.UserBean;
import com.hqf.a1056388105hqf.myfirstapplication.Mybean.UserByPhone;
import com.hqf.a1056388105hqf.myfirstapplication.Mybean.musicBean;

/**
 * Created by Administrator on 2017/10/9.
 */
/**
 * 数据库方法
 */
public class DBManager {


    public SQLiteDatabase dbCon;
    public DBHelper dbHelper;
    //  构造函数
    public DBManager(Context context){
        //  实例化一个DBHelper对象
        dbHelper = new DBHelper(context);
    }
    //  调用DBHelper查看数据库
    public UserBean Select_InDatabase(String LoginName, String LoginPWD){
        dbCon = dbHelper.openDatabase();
        Cursor cursor = dbCon.rawQuery("select * from User where LoginName =? and LoginPWD =?",new String[]{LoginName,LoginPWD});
        if(cursor.moveToFirst())
            {
                //  初始化一个UserBean容器，将获得的值赋值，之后返回
                UserBean user = new UserBean(
                        cursor.getString(cursor.getColumnIndex("LoginName"))
                        ,cursor.getString(cursor.getColumnIndex("LoginPWD")),null
                        ,Integer.parseInt(cursor.getString(cursor.getColumnIndex("UserLevel"))));

                dbCon.close();
            return user;
        }
        else {dbCon.close();return null;}

    }

    // 根据数据库中的信息，获取音乐的具体信息
    public musicBean getMusicInfor(String selectMusciPath){
        musicBean temp;
        dbCon = dbHelper.openDatabase();
        //  查询数据库，查询是否存在该地址下的音乐，如果存在，则返回他的基本信息
        Cursor cursor = dbCon.rawQuery("select * from MusicStack where MusicPath =?",new String[]{selectMusciPath});
        if(cursor.moveToFirst()){
            //  获取数据库中的所有字段并赋值
            temp = new musicBean(cursor.getString(cursor.getColumnIndex("MusicName"))
                    ,cursor.getString(cursor.getColumnIndex("MusicAuthor"))
                    ,cursor.getString(cursor.getColumnIndex("MusicAblum"))
                    ,cursor.getString(cursor.getColumnIndex("MusicImage"))
                    ,cursor.getString(cursor.getColumnIndex("MusicPath")));
            return temp;
        }
        else return null;
    }

    //  用手机号码登录的用户注册
    public UserByPhone InsertUserByPhoneNumber(String PhoneNumber,String PassWord){
        dbCon = dbHelper.openDatabase();  //  打开数据库

        //  利用游标查询数据库
        Cursor cursor = dbCon.rawQuery("select * from UserByPhone where PhoneNumber =? and LoginPWD =?",new String[]{
           PhoneNumber,PassWord
        });

        if(cursor.moveToFirst()){
            //  当查询到数据库中有元素存在时，返回该用户
            UserByPhone userbyphone = new UserByPhone(cursor.getString(cursor.getColumnIndex("UserName"))
                    ,cursor.getString(cursor.getColumnIndex("PhoneNumber"))
                    ,cursor.getString(cursor.getColumnIndex("UserPassword"))
                    ,cursor.getString(cursor.getColumnIndex("UserImage"))
                    ,cursor.getString(cursor.getColumnIndex("UserTitle")));
            return userbyphone;
        }
        return null;
    }
}
