package com.ty.lover.Service;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FunctionCallback;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.ty.lover.Activity.LoginActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AVService {
    public static void countDoing(String doingObjectId, CountCallback countCallback) {
        //实例化一个查询对象
        AVQuery<AVObject> query = new AVQuery<AVObject>("DoingList");
        //doingListChildObjectId = doingObjectId  使用此方法来添加条件值
        query.whereEqualTo("doingListChildObjectId", doingObjectId);

        Calendar c = Calendar.getInstance();  //实例化一个日历
        c.add(Calendar.MINUTE, -10);   //减10分钟
        //createdAt>c得到的时间     createdAt  创建LeanCloud的时间
        //updatedAt   更新LeanCloud的时间
        query.whereGreaterThan("createdAt", c.getTime());
        //项匹配这个查询对象的数量在一个后台线程。这个不使用缓存。
        query.countInBackground(countCallback);
    }

    //Use callFunctionMethod
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void getAchievement(String userObjectId) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("userObjectId", userObjectId);
        //AVCloud类定义提供了方法[callFunctionInBackground]与AVOSCloud云交互功能
        AVCloud.callFunctionInBackground("hello", parameters,
                new FunctionCallback() {
                    @Override
                    public void done(Object object, AVException e) {
                        if (e == null) {
                            Log.e("at", object.toString());// processResponse(object);
                        } else {
                            // handleError();
                        }
                    }
                });
    }

    public void createDoing(String userId, String doingObjectId) {
        AVObject doing = new AVObject("DoingList");
        doing.put("userObjectId", userId);
        doing.put("doingListChildObjectId", doingObjectId);
        doing.saveInBackground();
    }

    public static void requestPasswordReset(String email, RequestPasswordResetCallback callback) {
        AVUser.requestPasswordResetInBackground(email, callback);
    }

    public static void findDoingListGroup(FindCallback<AVObject> findCallback) {
        AVQuery<AVObject> query = new AVQuery<AVObject>("DoingListGroup");
        //通过给定的键按升序排序结果
        query.orderByAscending("Index");
        //从服务器后台线程检索满足这个查询列表AVObjects
        query.findInBackground(findCallback);
    }

    public static void findChildrenList(String groupObjectId, FindCallback<AVObject> findCallback) {
        AVQuery<AVObject> query = new AVQuery<AVObject>("DoingListChild");
        query.orderByAscending("Index");
        query.whereEqualTo("parentObjectId", groupObjectId);
        query.findInBackground(findCallback);
    }

    public static void initPushService(Context ctx) {
        com.avos.avoscloud.PushService.setDefaultPushCallback(ctx, LoginActivity.class);
        PushService.subscribe(ctx, "public", LoginActivity.class);
        AVInstallation.getCurrentInstallation().saveInBackground();
    }

    //注册方法    leancloud官方提供
    public static void signUp(String username, String password, String email, SignUpCallback signUpCallback) {
        AVUser user = new AVUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(signUpCallback);
    }

    //登出
    public static void logout() {
        AVUser.logOut();   //注销当前登录用户的会话
    }

    public static void createAdvice(String userId, String advice, SaveCallback saveCallback) {
        AVObject doing = new AVObject("SuggestionByUser");
        doing.put("UserObjectId", userId);
        doing.put("UserSuggestion", advice);
        doing.saveInBackground(saveCallback);
    }
}
