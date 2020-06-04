package com.szy.aop.bean;

import com.zy.binddata.annotations.BindData;

import org.json.JSONObject;

public class UserData extends BaseData {
    @BindData("name")
    protected String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //最终要用APT实现，AOP做替换
    @Override
    public UserData parseData(JSONObject data) {
        UserDataParseHelper.bindData(data, this);
        return this;
    }
}
