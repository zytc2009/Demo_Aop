package com.szy.lesson_aop.aspect;

import android.content.Context;
import android.widget.Toast;
import com.szy.lesson_aop.annotation.CheckNet;
import com.szy.lesson_aop.utils.NetUtils;
import com.szy.lesson_aop.utils.ContextWrapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;


/**
 * author: wangliyun
 * date: 2020/3/22
 * description: 用户统计的切面
 */
@Aspect
public class BehaviorAspect {

    /**
     * 找到处理的切点
     * * *(..) 处理所有方法
     */
    @Pointcut("execution(@com.szy.lesson_aop.annotation.CheckNet * *(..))")
    public void checkNetBehavior() {

    }

    /**
     * 通知
     * 处理切面
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("checkNetBehavior()")
    public Object weaveJointPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        CheckNet checkNet = methodSignature.getMethod().getAnnotation(CheckNet.class);
        if (checkNet!= null){
            //如何获取Context 对象
            Object object = joinPoint.getThis();
            Context context = ContextWrapper.getContext(object);
            if (context != null){
                if (!NetUtils.isNetWorkAvailable(context)){
                    Toast.makeText(context,checkNet.value() + "功能，网络异常, 请检查网络",Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
        }
        return joinPoint.proceed();
    }

    //修改函数返回值
    @Around("execution(* com.szy.lesson_aop.MainActivity.hasClicked(..))")
    public Object hasClickJointPoint(ProceedingJoinPoint joinPoint) throws Throwable {
            //如何获取Context 对象
            Object object = null;
            try {
                object = joinPoint.proceed();
            }catch (Throwable e){
                e.printStackTrace();
            }
            object = true;
            return object;
    }
}
