package com.zy.binddata.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) // 目标
public @interface BindData {
    String value();
}
