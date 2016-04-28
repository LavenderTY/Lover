package com.ty.lover.classMsg;

import com.avos.avoscloud.AVObject;

/**
 * Created by TDing on 2016/4/27.
 */
public class Merry {

    //创建一个Merry
    public void createDoing(String userId, String merryObjectId) {
        AVObject doing = new AVObject("Merry");
        doing.put("userObjectId", userId);
        doing.put("MerryObjectId", merryObjectId);
        doing.saveInBackground();
    }

}
