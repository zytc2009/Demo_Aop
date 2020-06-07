package com.szy.aop.bean;

import com.zy.binddata.annotations.BindData;

import org.json.JSONObject;

public class Teacher extends BaseData {
    @BindData("name")
    protected String name;

    @BindData("age")
    protected int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public Teacher parseData(JSONObject data) {
        TeacherParseHelper.bindData(data, this);
        return this;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
