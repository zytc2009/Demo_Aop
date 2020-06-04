package com.szy.aop.bean;

import com.zy.binddata.annotations.BindData;

import org.json.JSONException;
import org.json.JSONObject;

public class GradeData extends BaseData {
    @BindData("name")
    protected String gradeName;

    @BindData("userData")
    protected UserData userData;

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
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
        GradeDataParseHelper.bindData(data, this);
        return this;
    }
}
