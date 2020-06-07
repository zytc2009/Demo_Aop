package com.szy.aop.bean;

import com.szy.aop.json.JSONBindObject;
import com.zy.binddata.annotations.BindData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserData extends BaseData {
    @BindData("name")
    protected String userName;

    @BindData("age")
    protected int age;

    @BindData("isFree")
    protected boolean isFree;
    @BindData("money")
    protected float money;
    @BindData("score")
    protected long score;

    @BindData("grades")
    protected List<String> grades;


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public List<String> getGrades() {
        return grades;
    }

    public void setGrades(List<String> grades) {
        this.grades = grades;
    }

    //最终要用APT实现，AOP做替换
    @Override
    public UserData parseData(JSONObject data) {
        UserDataParseHelper.bindData(data, this);
        return this;
    }
}
