package com.szy.aop.bean;

import org.json.JSONObject;

import java.io.Serializable;

public abstract class BaseData implements Serializable {
    public abstract Object parseData(JSONObject data);
}
