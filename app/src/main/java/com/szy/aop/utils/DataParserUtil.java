package com.szy.aop.utils;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 数据解析处理类
 * Created by wanghb
 */
public class DataParserUtil {
    public static <T> T parseObject(String result, Class<T> valueType) {
        try {
            return JSON.parseObject(result, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T parseObject(JSONObject result, Class<T> valueType) {
        try {
            return result.toJavaObject(valueType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T parseObject(JSONArray result, Class<T> valueType) {
        try {
            return JSON.parseObject(result.toJSONString(), valueType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject parseAsJSONObject(String result) {
        try {
            return JSON.parseObject(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> parseArray(String result, Class<T> valueType) {
        try {
            return JSONArray.parseArray(result, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> parseArrayList(JSONArray result, Class<T> valueType) {
        try {
            return parseArray(result.toJSONString(), valueType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T parseObject(String result, TypeReference<T> type) {
        try {
            return JSON.parseObject(result, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T parseObject(String result, Type type) {
        try {
            return JSON.parseObject(result, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //以下是获取子内容的方法
    public static String parseToJson(Object obj) {
        try {
            if(obj instanceof String){
                return (String) obj;
            }
            return JSON.toJSONString(obj);
        }catch (Throwable e){
            e.printStackTrace();
            return "";
        }
    }

    public static int getJsonInt(JSONObject result, String key) {
        if (null == result) {
            return 0;
        }
        if (!result.containsKey(key)) {
            return 0;
        }
        return result.getIntValue(key);
    }

    public static long getJsonLong(JSONObject result, String key) {
        if (null == result) {
            return 0;
        }
        if (!result.containsKey(key)) {
            return 0;
        }
        return result.getLong(key);
    }

    public static String getJsonStr(JSONObject result, String key) {
        if (null == result) {
            return null;
        }
        if (!result.containsKey(key)) {
            return null;
        }
        return result.getString(key);
    }

    public static String getJsonStr(JSONObject result, String key, String defValue) {
        if (null == result) {
            return defValue;
        }
        if (!result.containsKey(key)) {
            return defValue;
        }
        return result.getString(key);
    }

    public static boolean getJsonBoolean(JSONObject result, String key, boolean defaultValue) {
        if (null == result) {
            return defaultValue;
        }
        if (!result.containsKey(key)) {
            return defaultValue;
        }
        return result.getBoolean(key);
    }

    public static JSONObject getJsonObj(JSONObject result, String key) {
        if (null == result) {
            return null;
        }
        if (!result.containsKey(key)) {
            return null;
        }
        return result.getJSONObject(key);
    }

    public static JSONArray getJsonArr(JSONObject result, String key) {
        if (null == result) {
            return null;
        }
        if (!result.containsKey(key)) {
            return null;
        }
        return result.getJSONArray(key);
    }


    /**
     * Json数据转成字符串
     *
     * @param object
     * @return
     */
    public static String objectToString(Object object) {
        return JSON.toJSONString(object);
    }



    /**
     * 读取Assets下的文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String readAssetsFileData(Context context, String fileName) {
        InputStream is = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        try {
            is = context.getAssets().open(fileName);
            reader = new InputStreamReader(is);
            bufferedReader = new BufferedReader(reader);
            StringBuffer buffer = new StringBuffer("");
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
                buffer.append("\n");
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                    bufferedReader = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (reader != null){
                try {
                    reader.close();
                    reader = null;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (is != null){
                try {
                    is.close();
                    is = null;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
