package com.ty.lover.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVUser;
import com.ty.lover.R;

public class AnyTimeActivity extends Activity {

    public AnyTimeActivity activity;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        userId = null;   //用户id
        //得到当前用户
        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getObjectId();
        }
    }

    public String getUserId() {
        return userId;
    }

    /**
     * 展示错误信息
     * @param errorMessage
     */
    protected void showError(String errorMessage) {
        showError(errorMessage, activity);
    }

    /**
     *展示错误信息的重载方法
     * @param errorMessage
     * @param activity
     */
    public void showError(String errorMessage, Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle(
                        activity.getResources().getString(
                                R.string.dialog_message_title))
                .setMessage(errorMessage)
                .setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }

    /**
     * 暂停
     */
    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    /**
     * 重新运行
     */
    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
    }

    /**
     * 保存数据
     * @param userName
     * @param passWord
     */
    public void saveData(String userName,String passWord) {//保存数据
        SharedPreferences sp = getSharedPreferences("user_info", 0);//获取SharedPreferences
        SharedPreferences.Editor editor = sp.edit();   //打来SharedPreferences编辑状态
        editor.putString("User", userName);
        editor.putString("Pwd", passWord);
        editor.commit();//保或者提交数据
    }

    /**
     * 初始化组件
     */
    public void initView(){

    }

    /**
     * 系统Back键处理
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){   //如果back被按下
            return true;
        }
        return false;
    }
}
