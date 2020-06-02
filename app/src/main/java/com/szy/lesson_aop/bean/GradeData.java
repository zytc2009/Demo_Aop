package com.szy.lesson_aop.bean;


import com.szy.lesson_aop.annotation.BindData;

import org.json.JSONException;
import org.json.JSONObject;

public class GradeData extends BaseData {
    @BindData("name")
    private String name;

    private UserData userData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    //最终要用APT实现，AOP做替换
    @Override
    public  GradeData parseData(JSONObject data) {
        name = data.optString("name");


        try {
            userData = new UserData().parseData(data.getJSONObject("userData"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }
}
