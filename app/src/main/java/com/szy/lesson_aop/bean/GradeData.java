package com.szy.lesson_aop.bean;

import com.szy.lesson_aop.annotation.DataKey;

import org.json.JSONObject;

public class GradeData extends BaseData {
    @DataKey("name")
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

    @Override
    public  GradeData parseData(JSONObject data) {
        this.setName(data.optString("name"));
        userData = new UserData().parseData(data.optJSONObject("userData"));
        return this;
    }
}
