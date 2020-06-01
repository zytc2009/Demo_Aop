package com.szy.lesson_aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description: 标定需要解析的key
 */
@Retention(RetentionPolicy.RUNTIME) //运行期
@Target(ElementType.FIELD) // 目标
public @interface BindData {
    String value();
}
