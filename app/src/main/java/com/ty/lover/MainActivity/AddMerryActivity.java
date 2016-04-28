package com.ty.lover.MainActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.ty.lover.R;
import com.ty.lover.classMsg.Merry;
import com.ty.lover.data.AnyTimeActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddMerryActivity extends AnyTimeActivity {
    private TextView textTime;
    private TextView textComplete;
    private EditText editContent;
    private Merry merry;

    @Override
    public void initView() {
        textTime = (TextView) findViewById(R.id.merry_time);
        editContent = (EditText) findViewById(R.id.merry_content);
        textComplete = (TextView) findViewById(R.id.merry_complete);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_merry);
        merry = new Merry();
        initView();
        //获取系统时间   大写的HH表示24小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        String date = sdf.format(new Date());
        textTime.setText(date);
    }

    /**
     * 完成按钮事件
     *
     * @param v
     */
    public void Complete_Merry(View v) {
        textComplete.setClickable(true);

        if (editContent.getText().toString().length() != 0) {  //判断内容框中是否有内容
            new Thread() {
                @Override
                public void run() {
                    // 构造方法传入的参数，对应的就是控制台中的 Class Name
                    final AVObject merry = new AVObject("Merry");   //实例化一个保存对象
                    merry.put("time", textTime.getText().toString());
                    merry.put("content", editContent.getText().toString());
                    merry.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                // 存储成功
                                //更新数据
                                merry.put("time", textTime.getText().toString());
                                merry.put("content", editContent.getText().toString());
                                merry.saveInBackground();  //保存后有返回值

                                //得到ObjectId
                                String objectId = merry.getObjectId();
                                System.out.println(objectId);
                                //查询操作
                                AVQuery<AVObject> query = new AVQuery<>("Todo");
                                query.whereEqualTo("objectId", objectId);
                                query.findInBackground(new FindCallback<AVObject>() {
                                    @Override
                                    public void done(List<AVObject> list, AVException e) {
                                        List<AVObject> priorityEqualsZeroTodos = list;// 符合 objectId = objectId 的 Todo 数组
                                        System.out.println(priorityEqualsZeroTodos);
//                                        Intent intent = new Intent(AddMerryActivity.this, MerryActivity.class);
//                                        startActivity(intent);
//                                        finish();
                                    }
                                });

                            } else {
                                // 失败的话，请检查网络环境以及 SDK 配置是否正确
                            }
                        }
                    });
                }
            }.start();
        } else {
            SystemShow();
        }
    }

//    /**
//     * 完成按钮事件
//     *
//     * @param v
//     */
//    public void Complete_Merry(View v) {
//        textComplete.setClickable(true);
//
//        if (editContent.getText().toString().length() != 0) {  //判断内容框中是否有内容
//            new Thread() {
//                @Override
//                public void run() {
//                    // 构造方法传入的参数，对应的就是控制台中的 Class Name
//                    final AVObject merry = new AVObject("Merry");   //实例化一个保存对象
//                    merry.put("time", textTime.getText().toString());
//                    merry.put("content", editContent.getText().toString());
//                    merry.saveInBackground(new SaveCallback() {
//                        @Override
//                        public void done(AVException e) {
//                            if (e == null) {
//                                // 存储成功
//                                //更新数据
//                                merry.put("time", textTime.getText().toString());
//                                merry.put("content", editContent.getText().toString());
//                                merry.saveInBackground();  //保存后有返回值
//
//                                //得到ObjectId
//                                String objectId = merry.getObjectId();
//                                System.out.println(objectId);
//                                //查询操作
//                                AVQuery<AVObject> query = new AVQuery<>("Todo");
//                                query.whereEqualTo("objectId", objectId);
//                                query.findInBackground(new FindCallback<AVObject>() {
//                                    @Override
//                                    public void done(List<AVObject> list, AVException e) {
//                                        List<AVObject> priorityEqualsZeroTodos = list;// 符合 objectId = objectId 的 Todo 数组
//                                        System.out.println(priorityEqualsZeroTodos);
////                                        Intent intent = new Intent(AddMerryActivity.this, MerryActivity.class);
////                                        startActivity(intent);
////                                        finish();
//                                    }
//                                });
//
//                            } else {
//                                // 失败的话，请检查网络环境以及 SDK 配置是否正确
//                            }
//                        }
//                    });
//                }
//            }.start();
//        } else {
//            SystemShow();
//        }
//    }

    /**
     * 系统提示
     */
    private void SystemShow() {
        new AlertDialog.Builder(activity)
                .setTitle(
                        activity.getResources().getString(
                                R.string.dialog_message_title))
                .setMessage(
                        activity.getResources().getString(
                                R.string.content_not_null))
                .setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {   //如果back被按下
            Intent intent = new Intent(AddMerryActivity.this, MerryActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }
}
