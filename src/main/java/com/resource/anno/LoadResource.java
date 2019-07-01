package com.resource.anno;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 11:26
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface LoadResource {
    /** 资源位置*/
    String value() default "";

}
