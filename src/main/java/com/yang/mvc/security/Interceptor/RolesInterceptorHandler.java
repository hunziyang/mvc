package com.yang.mvc.security.Interceptor;

import com.alibaba.fastjson.JSON;
import com.yang.mvc.cache.UsersCacheService;
import com.yang.mvc.common.ApplicationContextAwareImpl;
import com.yang.mvc.common.Result;
import com.yang.mvc.common.ResultCode;
import com.yang.mvc.common.vo.LoginSuccessUserInfoVo;
import com.yang.mvc.security.annotation.AnnotationUtil;
import com.yang.mvc.security.annotation.authentication.RequiresRoles;
import com.yang.mvc.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RolesInterceptorHandler implements HandlerInterceptor {
    private static final UsersCacheService usersCacheService = ApplicationContextAwareImpl.getBean(UsersCacheService.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!AnnotationUtil.containClazz(handler, RequiresRoles.class)) {
            return true;
        }
        String token = request.getHeader(JwtUtils.AUTH_HEADER);
        token = StringUtils.removeStart(token, JwtUtils.TOKEN_PREFIX);
        if (!usersCacheService.hasJWTToken(token)) {
            unAuthentication(response);
            return false;
        }
        LoginSuccessUserInfoVo loginSuccessUserInfoVo = usersCacheService.getLoginSuccessUserInfoVo(token);
        if (!AnnotationUtil.checkRequiresRoles(handler, loginSuccessUserInfoVo.getRoles())){
            unAuthentication(response);
            return false;
        }
        return true;
    }

    private void unAuthentication(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(Result.error(ResultCode.UNAUTHORIZATION)));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
