package com.szy.aop.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.View;

/**
 * author: wangliyun
 * date: 2020/4/2
 * description: 上下文包装
 */
public class ContextWrapper {

    /**
     * 获取Context 对象
     * @param object
     * @return
     */
    public static Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;
    }
}
