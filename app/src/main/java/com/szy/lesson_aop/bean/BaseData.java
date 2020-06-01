package com.szy.lesson_aop.bean;

import org.json.JSONObject;

public abstract class BaseData {
    public abstract Object parseData(JSONObject data);
}
