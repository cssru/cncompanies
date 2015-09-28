package com.cssru.cncompanies.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MappedAs {
    String propertyName();
}
