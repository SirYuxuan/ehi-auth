package com.yuxuan66.ehi.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Sir丶雨轩
 * @date 2020/6/16
 */
@Target({ ElementType.METHOD,ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    /**
     * 权限校验编码
     * @return 校验编码
     */
    String value() default "";

    /**
     * 是否跳过授权
     * @return 跳过
     */
    boolean skip() default false;

}
