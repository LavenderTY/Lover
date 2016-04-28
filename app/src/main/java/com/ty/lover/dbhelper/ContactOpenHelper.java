package com.ty.lover.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Administrator on 2016/4/4.
 */
public class ContactOpenHelper extends SQLiteOpenHelper {

    public static final String T_CONTACT = "t_contact";
    //创建联系人表
    public class  ContactTable implements BaseColumns{
        public static final String NICKNAME = "nickname";//昵称
        public static final String AVATAR = "avatar";//头像
    }
    public ContactOpenHelper(Context context) {  //构造器
        super(context, "contact.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
