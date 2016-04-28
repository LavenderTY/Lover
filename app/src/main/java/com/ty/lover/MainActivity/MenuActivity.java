package com.ty.lover.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ty.lover.Activity.LoginActivity;
import com.ty.lover.R;
import com.ty.lover.Utils.ThreadUtils;
import com.ty.lover.data.AnyTimeActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.ty.lover.Service.AVService.logout;

public class MenuActivity extends AnyTimeActivity {

    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.single_msg)
    TextView singleMsg;
    @InjectView(R.id.other_account)
    TextView otherAccount;
    @InjectView(R.id.quite_current)
    TextView quiteCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.inject(this);
    }

    /**
     * 个人资料编辑
     *
     * @param v
     */
    public void SingleMsg(View v) {

    }

    /**
     * 绑定另一半账号
     *
     * @param v
     */
    public void OtherAccount(View v) {

    }

    /**
     * 实现退出当前用户
     *
     * @param v
     */
    public void QuiteCurrent(View v) {
        quiteCurrent.setClickable(true);
        logout();
        ThreadUtils.runInThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
                activity.finish();
            }
        });
    }
}
