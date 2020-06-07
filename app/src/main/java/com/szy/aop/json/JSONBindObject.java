package com.szy.aop.json;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONBindObject {
    private JSONObject data;
    public JSONBindObject(JSONObject data) {
    }

    public int optInt(String name) {
        return data.optInt(name);
    }

    public boolean optBoolean(String name) {
        return data.optBoolean(name);
    }

    public long optLong(String name){
        return data.optLong(name);
    }

    public String optString(String name) {
        return data.optString(name);
    }

    public double optDouble(String name) {
        return data.optDouble(name);
    }

    public JSONArray optJSONArray(String name){
        return data.optJSONArray(name);
    }

    public JSONObject optJSONObject(String name) {
        return data.optJSONObject(name);
    }

    //主要处理基本类型
    public Object opt(String name){
        return data.opt(name);
    }

}
