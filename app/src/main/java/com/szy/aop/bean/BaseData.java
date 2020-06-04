package com.szy.aop.bean;

import org.json.JSONObject;

public abstract class BaseData {
    public abstract Object parseData(JSONObject data);
}
