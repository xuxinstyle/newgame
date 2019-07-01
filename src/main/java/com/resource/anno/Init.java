package com.resource.anno;

import java.lang.annotation.*;

/**
 * @Author：xuxin
 * @Date: 2019/7/1 22:29
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Init {
    String value() default "";
}
