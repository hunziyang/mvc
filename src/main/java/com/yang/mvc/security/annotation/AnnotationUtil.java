package com.yang.mvc.security.annotation;

import com.yang.mvc.security.annotation.authentication.UrlPass;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationUtil {
    /**
     * 访问白名单
     *
     * @param handler
     * @return
     */
    public static boolean containUrlPass(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(UrlPass.class)) {
                    return true;
                }
            }
        }
        return false;
    }
}
