package com.netty.dispatcher.anno;

import java.lang.annotation.*;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 17:47
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface HandlerAnno {
}
