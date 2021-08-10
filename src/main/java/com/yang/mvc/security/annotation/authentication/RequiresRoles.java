package com.yang.mvc.security.annotation.authentication;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresRoles {

    public enum Logical{ AND,OR};

    String[] values() default "users";

    Logical logical() default Logical.AND;
}
