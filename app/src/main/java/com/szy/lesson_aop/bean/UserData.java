package com.szy.lesson_aop.bean;

import com.szy.lesson_aop.annotation.BindData;

import org.json.JSONObject;

public class UserData extends BaseData {
    @BindData("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //最终要用APT实现，AOP做替换
    @Override
    public UserData parseData(JSONObject data) {
        return this;
    }
}
