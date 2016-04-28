package com.ty.lover.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ty.lover.MainActivity.MenuActivity;
import com.ty.lover.MainActivity.MerryActivity;
import com.ty.lover.R;
import com.ty.lover.Utils.ThreadUtils;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 菜单按钮功能
     *
     * @param v
     */
    public void Menu(View v) {
        ThreadUtils.runInThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 备忘录功能
     * @param v
     */
    public void Merry(View v) {
        ThreadUtils.runInThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MerryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
