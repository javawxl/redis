package com.good.word.redis.valid;

import lombok.SneakyThrows;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ValidDataRepeated implements ConstraintValidator<ValidRepeated, List<?>> {

    @Override
    public void initialize(ValidRepeated constraintAnnotation) {
    }

    @Override
    public boolean isValid(List list, ConstraintValidatorContext context) {
        if (list == null || list.size() < 2)
            return true;
        Set<String> keySet = new HashSet<>();
        ConstraintValidatorContextImpl constraintValidatorContext = (ConstraintValidatorContextImpl) context;
        for (Object o : list) {
            RepeatedKeyDefinition definition = (RepeatedKeyDefinition) o;
            String key = definition.uniqKey();
            if (!keySet.contains(key))
                keySet.add(key);
            else {
                Field[] fields = o.getClass().getDeclaredFields();
                for (Field f : fields) {
                    f.setAccessible(true);
                    try {
                        constraintValidatorContext.addMessageParameter(f.getName(), f.get(o));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        }
        return true;
    }
}
