package com.good.word.redis.interceptor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MappedColumn {
    String key() default "key";
    String value() default "value";
}
