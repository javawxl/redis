package com.good.word.redis.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDataRepeated.class)
public @interface ValidRepeated {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

