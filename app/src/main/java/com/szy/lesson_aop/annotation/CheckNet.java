package com.szy.lesson_aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: wangliyun
 * date: 2020/3/22
 * description: 标志特定的方法进行行为统计
 */
@Retention(RetentionPolicy.RUNTIME) //运行期
@Target(ElementType.METHOD) // 目标
public @interface CheckNet {
    String value();
}
