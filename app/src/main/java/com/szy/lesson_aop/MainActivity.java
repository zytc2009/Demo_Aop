package com.szy.lesson_aop;

import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.szy.lesson_aop.annotation.CheckNet;
import com.szy.lesson_aop.bean.GradeData;
import com.szy.lesson_aop.utils.DataParserUtil;

import org.json.JSONException;
import org.json.JSONObject;

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

    /**
     *  验证自解析要比fast快，省去了反射
     *  由此：我们假设如果数据解析是否可以自动完成，通过apt实现，这样既提升速度，又不给大家工作产生较大影响
     *
     */
    public void dataParseTest(View view) {
        String result = DataParserUtil.readAssetsFileData(this, "json.txt");
        //解析测试
        Log.d("Parse","dataParseTest() system start");
        //系统的解析
        for(int i=0;i<10000;i++){
            try {
                JSONObject object = new JSONObject(result);
                GradeData gradeData = new GradeData().parseData(object);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        Log.d("Parse","dataParseTest() system end");

        for(int i=0;i<10000;i++){
            GradeData gradeData = DataParserUtil.parseObject(result, GradeData.class);
        }
        Log.d("Parse","dataParseTest() fast end");
    }
}
