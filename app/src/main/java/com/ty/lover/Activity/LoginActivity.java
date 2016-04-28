package com.ty.lover.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.ty.lover.R;
import com.ty.lover.Service.AVService;
import com.ty.lover.Utils.ThreadUtils;
import com.ty.lover.data.AnyTimeActivity;

public class LoginActivity extends AnyTimeActivity {
    private EditText user_name;
    private EditText user_pwd;
    private String userName = "";
    private String userpwd = "";
    private CheckBox checkBox;
    private ProgressDialog progressDialog;    //进度条对话框

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        getData();
        //AVAnalytics是统计的核心类，本身不需要实例化，所有方法以类方法的形式提供.
        //trackAppOpened  跟踪这个应用程序启动
        AVAnalytics.trackAppOpened(getIntent());
        AVService.initPushService(this);
        if (getUserId() != null) {
            Intent mainIntent = new Intent(activity, MainActivity.class);
            startActivity(mainIntent);
            activity.finish();
        }
    }
    /**
     * 注册按钮
     */
    public void Sign(View v){
        Intent intent = new Intent(LoginActivity.this,SignActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 登录按钮
     */
    @SuppressLint("NewApi")
    public void Login(View v) {
        userName = user_name.getText().toString();  //得到用户名
        userpwd = user_pwd.getText().toString();    //得到密码
        //判断用户名是否为空
        if (TextUtils.isEmpty(userName)) {
            user_name.setError("用户名不能为空");
            return;
        }
        //判断密码是否为空
        if (TextUtils.isEmpty(userpwd)) {
            user_pwd.setError("密码不能为空");
            return;
        }
        if (checkBox.isChecked()) {
            saveData(userName, userpwd);
        }
        progressDialogShow();
        ThreadUtils.runInThread(new Runnable() {
            @Override
            public void run() {
                AVUser.logInInBackground(userName,
                        userpwd,
                        new LogInCallback() {
                            public void done(AVUser user, AVException e) {
                                if (user != null) {
                                    Intent mainIntent = new Intent(activity,
                                            MainActivity.class);
                                    startActivity(mainIntent);
                                    activity.finish();
                                } else {
                                    progressDialogDismiss();
                                    showLoginError();
                                }
                            }
                        });
            }
        });
    }

    /**
     * 解除进度条对话框
     */
    private void progressDialogDismiss() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    /**
     * 展示进度条对话框
     */
    private void progressDialogShow() {
        progressDialog = ProgressDialog.show(activity,
                activity.getResources().getText(
                        R.string.dialog_message_title),
                activity.getResources().getText(
                        R.string.dialog_text_wait), true, false);
    }

    /**
     * 展示登录错误
     */
    private void showLoginError() {
        new AlertDialog.Builder(activity)
                .setTitle(
                        activity.getResources().getString(
                                R.string.dialog_error_title))
                .setMessage(
                        activity.getResources().getString(
                                R.string.error_login_error))
                .setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }

    /**
     * 忘记密码
     */
    public void ForGetPwd(View v) {
        Intent intent = new Intent(LoginActivity.this, ForGetActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 显示密码与账户名
     */
    public void getData() {  //得到数据
        initView();
        SharedPreferences sp = getSharedPreferences("user_info", 0);//获取SharedPreferences
        //取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        userName = sp.getString("User", "");
        userpwd = sp.getString("Pwd", "");
        user_name.setText(userName);
        user_pwd.setText(userpwd);
    }

    /**
     * 实现控件初始化
     */
    public void initView() {
        user_name = (EditText) findViewById(R.id.name);
        user_pwd = (EditText) findViewById(R.id.pwd);
        checkBox = (CheckBox) findViewById(R.id.rempwd);
    }

}
