package com.szy.aop.bean;

import com.szy.aop.json.JSONBindObject;
import com.zy.binddata.annotations.BindData;

import org.json.JSONException;
import org.json.JSONObject;

public class GradeData extends BaseData {
    @BindData("name")
    protected String gradeName;

    @BindData("gradeNum")
    protected int gradeNum;

    @BindData("userData")
    protected UserData userData;

    @BindData("teacher")
    protected Teacher teacher;

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }


    public int getGradeNum() {
        return gradeNum;
    }

    public void setGradeNum(int gradeNum) {
        this.gradeNum = gradeNum;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    //最终要用APT实现，AOP做替换
    @Override
    public  GradeData parseData(JSONObject data) {
        GradeDataParseHelper.bindData(data, this);
        return this;
    }
}
