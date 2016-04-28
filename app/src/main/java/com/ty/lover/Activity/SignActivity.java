package com.ty.lover.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SignUpCallback;
import com.ty.lover.R;
import com.ty.lover.Service.AVService;
import com.ty.lover.Utils.ThreadUtils;
import com.ty.lover.data.AnyTimeActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static java.lang.Thread.sleep;

public class SignActivity extends AnyTimeActivity {

    @InjectView(R.id.username)
    EditText username;
    @InjectView(R.id.userpwd)
    EditText userpwd;
    @InjectView(R.id.userrepwd)
    EditText userrepwd;
    @InjectView(R.id.editText_register_email)
    EditText registeremail;

    String userName = "";
    String password = "";
    String repassword = "";
    String RegisterEmail = "";
    private ProgressDialog progressDialog;  //进度对话框

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ButterKnife.inject(this);
//        // 给左上角图标的左边加上一个返回的图标
//        this.getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 注册功能按钮
     *
     * @param v
     */
    public void sign(View v) {
        userName = username.getText().toString();
        password = userpwd.getText().toString();
        repassword = userrepwd.getText().toString();
        RegisterEmail = registeremail.getText().toString();

        if (password.equals(repassword)) {
            if (!userName.isEmpty()) {
                if (!password.isEmpty()) {
                    if (!RegisterEmail.isEmpty()) {
                        if (!EmailFormat(RegisterEmail)) {
                            showError(activity.getString(R.string.error_register_email_format));
                        } else {
                            progressDialogShow();
                            ThreadUtils.runInThread(new Runnable() {
                                @Override
                                public void run() {
                                    register();
                                }
                            });
                        }
                    } else {
                        showError(activity
                                .getString(R.string.error_register_email_address_null));
                    }
                } else {
                    showError(activity
                            .getString(R.string.error_register_password_null));
                }
            } else {
                showError(activity
                        .getString(R.string.error_register_user_name_null));
            }
        } else {
            showError(activity
                    .getString(R.string.error_register_password_not_equals));
        }
    }

    /**
     * 邮箱判断正则表达式
     *
     * @param eMAIL1
     * @return
     */
    private boolean EmailFormat(String eMAIL1) {
        Pattern pattern = Pattern
                .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher mc = pattern.matcher(RegisterEmail);
        return mc.matches();
    }

    /**
     * 设置项目选项
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent LoginIntent = new Intent(this, LoginActivity.class);
            startActivity(LoginIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 进度提示对话框
     * 使用静态方式创建并显示，
     * 这种进度条只能是圆形条,倒数第二个参数boolean indeterminate设置是否是不明确的状态
     * 这里最后一个参数boolean cancelable 设置是否进度条是可以取消的
     */
    private void progressDialogShow() {
        progressDialog = ProgressDialog
                .show(activity,
                        activity.getResources().getText(
                                R.string.dialog_message_title),
                        activity.getResources().getText(
                                R.string.dialog_text_wait), true, false);

    }

    /**
     * 取消进度对话框
     */
    private void progressDialogDismiss() {
        if (progressDialog != null)
            progressDialog.dismiss();  //取消对话框
    }

    /**
     * 展示注册成功
     */
    private void showRegisterSuccess() {
        new AlertDialog.Builder(activity)
                .setTitle(
                        activity.getResources().getString(
                                R.string.dialog_message_title))
                .setMessage(
                        activity.getResources().getString(
                                R.string.success_register_success))
                .setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                ThreadUtils.runInThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            sleep(8000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                dialog.dismiss();   //取消对话框
                            }
                        }).show();
    }

    /**
     * 注册
     */
    public void register() {
        //SignUpCallback  注册回收
        SignUpCallback signUpCallback = new SignUpCallback() {
            public void done(AVException e) {
                progressDialogDismiss();  //取消进度对话框
                if (e == null) {
                    showRegisterSuccess();  //显示注册成功对话框
                    Intent loginIntent = new Intent(activity, LoginActivity.class);   //跳转至登录界面
                    startActivity(loginIntent);
                    activity.finish();
                } else {
                    switch (e.getCode()) {
                        case 202:
                            showError(activity
                                    .getString(R.string.error_register_user_name_repeat));
                            break;
                        case 203:
                            showError(activity
                                    .getString(R.string.error_register_email_repeat));
                            break;
                        default:
                            showError(activity
                                    .getString(R.string.network_error));
                            break;
                    }
                }
            }
        };
        AVService.signUp(userName, password, RegisterEmail, signUpCallback);
    }
}
