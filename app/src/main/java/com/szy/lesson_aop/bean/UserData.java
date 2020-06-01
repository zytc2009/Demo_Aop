package com.szy.lesson_aop.bean;

import com.szy.lesson_aop.annotation.DataKey;

import org.json.JSONObject;

public class UserData extends BaseData {
    @DataKey("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public UserData parseData(JSONObject data) {
        return null;
    }
}
