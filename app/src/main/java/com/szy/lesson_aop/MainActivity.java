package com.szy.lesson_aop;

import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.szy.lesson_aop.annotation.CheckNet;
public class MainActivity extends FragmentActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     *  我们希望在这个方法调用时，增加网络检查判断，如果有网则执行，无网不执行
     *  当然，我们也可以在方法执行时，做快速点击处理，防止误操作
     */
    @CheckNet("点击摇一摇")
    public void clickShake(View view) {
        Log.i("Aop","clickShake()" + hasClicked());
    }

    //测试方法，固定返回false，我们用AOP修改函数返回值
    public boolean hasClicked(){
        return false;
    }

}
