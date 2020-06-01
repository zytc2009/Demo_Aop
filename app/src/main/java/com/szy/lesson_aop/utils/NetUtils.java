package com.szy.lesson_aop.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * author: wangliyun
 * date: 2020/4/2
 * description:
 */
public class NetUtils {


    public static boolean isNetWorkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            if (networkInfos != null && networkInfos.length > 0){
                for (int i = 0 ; i< networkInfos.length; i++){
                    if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
