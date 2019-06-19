package com.resource.anno;

import java.lang.annotation.*;

/**
 * @Author：xuxin
 * @Date: 2019/6/15 15:52
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Analyze {
    String value() default "";
}
