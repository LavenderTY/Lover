package com.ty.lover.Utils;

import android.os.Handler;

/**
 * Created by Administrator on 2016/3/31.
 */
public class ThreadUtils {
    /**
     * 子线程执行task
     */
    public static void runInThread(Runnable task){
       new  Thread(task).start();
    }

    /**
     * 创建一个主线程中handler
     */
    public static Handler mHandler = new Handler();

    /**
     * UI线程执行task
     */
    public static void runInUIThread(Runnable task) {
        mHandler.post(task);
    }
}
