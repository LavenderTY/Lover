package com.ty.lover.data;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by TDing on 2016/4/15.
 */
@AVClassName("Post")
public class Post extends AVObject {
    public String getContent() {
        return getString("content");
    }

    public void setContent(String value) {
        put("content", value);
    }
}
