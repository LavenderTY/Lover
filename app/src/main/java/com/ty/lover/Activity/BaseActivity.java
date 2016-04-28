package com.ty.lover.Activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/4/1.
 */
public class BaseActivity extends AppCompatActivity{
    public void initView(){

    }
    public void initLisener(){

    }
    public void saveData(String userName,String passWord) {//保存数据
        SharedPreferences sp = getSharedPreferences("user_info", 0);//获取SharedPreferences
        SharedPreferences.Editor editor = sp.edit();   //打来SharedPreferences编辑状态
        editor.putString("User", userName);
        editor.putString("Pwd", passWord);
        editor.commit();//保或者提交数据
    }

}
