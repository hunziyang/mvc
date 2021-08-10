package com.yang.mvc.security.annotation;

import com.yang.mvc.security.annotation.authentication.RequiresRoles;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class AnnotationUtil {
    /**
     * 访问白名单
     *
     * @param handler
     * @return
     */
    public static boolean containClazz(Object handler, Class clazz) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(clazz)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkRequiresRoles(Object handler, List<String> roles) {
        if (!(handler instanceof HandlerMethod)) {
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RequiresRoles requiresRoles = method.getAnnotation(RequiresRoles.class);
        RequiresRoles.Logical logical = requiresRoles.logical();
        String[] values = requiresRoles.values();
        if (RequiresRoles.Logical.AND.equals(logical)) {
            return roles.containsAll(Arrays.asList(values));
        }
        if (RequiresRoles.Logical.OR.equals(logical)) {
            for (String value : values) {
                if (roles.contains(value)) {
                    return true;
                }
            }
        }
        return false;
    }
}
