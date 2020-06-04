package com.szy.aop.utils;

import com.szy.aop.bean.BaseData;

import org.json.JSONObject;

public class BindDataHelper {
    //最终要用APT实现，AOP做替换
    public  static <T extends BaseData> T parseData(JSONObject data, Class<T> valueType) {
        try {
            T obj = valueType.newInstance();
            return (T) obj.parseData(data);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
