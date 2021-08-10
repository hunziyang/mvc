package com.yang.mvc.security.annotation.authentication;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresRoles {

    String[] values() default "users";

    String logical() default "and";
}
