package com.ty.lover;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.ty.lover.Activity.BaseActivity;
import com.ty.lover.Activity.LoginActivity;
import com.ty.lover.Utils.ThreadUtils;


public class SplishActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splish);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "7CBOOMYsC5iEG0dAIW58FUyS-gzGzoHsz", "yEFV00O3W897rQmPHXnn7urQ");
        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d("saved", "success!");
                }
            }
        });
        ThreadUtils.runInThread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);  //暂停2S
                Intent intent = new Intent(SplishActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
