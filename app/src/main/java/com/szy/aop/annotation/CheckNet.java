package com.szy.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME) //运行期
@Target(ElementType.METHOD) // 目标
public @interface CheckNet {
    String value();
}
