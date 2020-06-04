package com.szy.aop.aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * author: wangliyun
 * date: 2020/5/7
 * description:
 */

/**
 * 个切面是一个独立的功能实现，一个程序可以定义多个切面，
 * 定义切面需要新建一个类并加上@Aspect注解。
 * 例如：Demo2就是一个切面 ，这个切面实现了打印所有Activity中OnCreate方法耗时的功能。
 *
 *
 * */
@Aspect
public class Demo2 {

//    @Before("execution(void android.support.v4.app.FragmentActivity+.onCreate(..))")
//    public void before(JoinPoint joinPoint) throws Exception{
//        String key = joinPoint.getSignature().toString();
//        Log.d("Aop", "onActivityMethodBefore: " + key);
//    }
//
//    @After("execution(void android.support.v4.app.FragmentActivity+.onCreate(..))")
//    public void after(JoinPoint joinPoint) throws Exception{
//        String key = joinPoint.getSignature().toString();
//        Log.d("Aop", "onActivityMethodAfter: " + key);
//    }


    /**
     * 监听声明周期方法，同一点的切点会被覆盖，你可以打开上面的两个方法
     * @param joinPoint
     * @return
     * @throws Exception
     */
    @Around("execution(* *..FragmentActivity+.on**(..))")
    public Object activityOnCreateTime(ProceedingJoinPoint joinPoint) throws Exception{
        long startTime = System.currentTimeMillis();
        Object object = null;
        try {
            object = joinPoint.proceed();
            Log.d("Aop","activityOnCreateTime: method="+joinPoint.getSignature().getName()+",className="+joinPoint.getSignature().getDeclaringType().getName());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Log.d("Aop","activityOnCreateTime:"+(System.currentTimeMillis() - startTime));
        return object;
    }
}
