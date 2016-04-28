package com.ty.lover.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.ty.lover.Activity.MainActivity;
import com.ty.lover.R;
import com.ty.lover.Utils.ThreadUtils;
import com.ty.lover.data.AnyTimeActivity;

import java.util.List;

public class MerryActivity extends AnyTimeActivity {
    private List list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merry);
//        MerryQuery();
    }

    /**
     * 实现添加备忘录功能
     */
    public void Add(View v) {
        ThreadUtils.runInThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MerryActivity.this, AddMerryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 查询数据方法
     */
    private void MerryQuery() {
        ThreadUtils.runInThread(new Runnable() {
            @Override
            public void run() {
                AVQuery.doCloudQueryInBackground("select time,content from Merry", new CloudQueryCallback<AVCloudQueryResult>() {
                    @Override
                    public void done(AVCloudQueryResult result, AVException cqlException) {
                        if (cqlException == null) {
                            list = result.getResults();//这里是你查询到的结果
                            System.out.println(list);
                            ListView listView = (ListView) findViewById(R.id.merry_show);
                            listView.setAdapter(new MyAdapter());
                        }
                    }
                });
            }
        });
    }

    class MyAdapter extends BaseAdapter {
        //返回要显示的条目的数量
        @Override
        public int getCount() {
            return list.size();
        }

        //获取条目
        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        //获取条目的id
        @Override
        public long getItemId(int position) {
            return position;
        }

        //返回一个view对象，会作为listView的一个条目显示在界面上
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = null;
            ViewHolder mHolder = null;
            if (convertView == null) {   //如果缓存为空，则重新填充布局文件
                v = View.inflate(MerryActivity.this, R.layout.list_item_merry, null);  //采用填充的方式
                //创建ViewHolder，封装所有条目使用的组件
                mHolder = new ViewHolder();

                mHolder.tv_time = (TextView) v.findViewById(R.id.time_merry);  //时间
                mHolder.tv_content = (TextView) v.findViewById(R.id.content_merry); //内容
                //将ViewHolder封装至View对象中，这样View被缓存时，ViewHolder也被缓存了
                v.setTag(mHolder); //设置一个标签，将一个对象存到View中，是一个Object类型
            } else {   //如果缓存不为空，直接调用缓存
                v = convertView;   //convertView 缓存的条目
                //从View中取出保存的ViewHolder，ViewHolder中就有所有组件对象，不需要再去findViewById
                mHolder = (ViewHolder) v.getTag();
            }
            // 改变每个条目显示的内容，找到布局文件中对应的组件
            mHolder.tv_time.setText("");
            mHolder.tv_content.setText("");
            return v;
        }
    }

    //把条目需要使用到的所有组件封装到这个类中
    class ViewHolder {
        TextView tv_time;
        TextView tv_content;
    }

//    class MyAdapter extends SimpleAdapter {
//        int count = 0;
//        private List<Map<String, AVObject>> mlist;
//
//        //构造方法
//        public MyAdapter(FindCallback<AVObject> context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
//            super(activity, data, resource, from, to);
//            mlist = (List<Map<String, AVObject>>) data;
//            if (data == null) {   //如果没有数据
//                count = 0;
//            } else {   //否则就将data的大小赋值给count
//                count = data.size();
//            }
//        }
//
//        //返回条目数量
//        public int getCount() {
//            return mlist.size();
//        }
//
//        //返回条目
//        public Object getItem(int position) {
//            return position;
//        }
//
//        //返回条目id
//        public long getItemId(int position) {
//            return position;
//        }
//        //实现具体显示
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            Map<String, AVObject> map = mlist.get(position);
//            View v = null;
//            ViewHolder mHolder = null;
//            if (convertView == null) {   //如果缓存为空，则重新填充布局文件
//                v = View.inflate(MerryActivity.this, R.layout.list_item_merry, null);  //采用填充的方式
//                //创建ViewHolder，封装所有条目使用的组件
//                mHolder = new ViewHolder();
//
//                mHolder.tv_time = (TextView) v.findViewById(R.id.time_merry);  //时间
//                mHolder.tv_content = (EditText) v.findViewById(R.id.content_merry); //内容
//
//                //将ViewHolder封装至View对象中，这样View被缓存时，ViewHolder也被缓存了
//                v.setTag(mHolder); //设置一个标签，将一个对象存到View中，是一个Object类型
//            } else {   //如果缓存不为空，直接调用缓存
//                v = convertView;   //convertView 缓存的条目
//                //从View中取出保存的ViewHolder，ViewHolder中就有所有组件对象，不需要再去findViewById
//                mHolder = (ViewHolder) v.getTag();
//            }
//            // 改变每个条目显示的内容，找到布局文件中对应的组件
//            mHolder.tv_time.setText("" + position);
//            mHolder.tv_content.setText("" + position);
//            return v;
//        }
//    }
//
//    //把条目需要使用到的所有组件封装到这个类中
//    class ViewHolder {
//        TextView tv_time;
//        EditText tv_content;
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {   //如果back被按下
            Intent intent = new Intent(MerryActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }
}
