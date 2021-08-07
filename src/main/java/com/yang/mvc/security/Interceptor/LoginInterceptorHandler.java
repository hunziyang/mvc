package com.yang.mvc.security.Interceptor;

import com.alibaba.fastjson.JSON;
import com.yang.mvc.cache.UsersCacheService;
import com.yang.mvc.common.ApplicationContextAwareImpl;
import com.yang.mvc.common.Result;
import com.yang.mvc.common.ResultCode;
import com.yang.mvc.common.vo.LoginSuccessUserInfoVo;
import com.yang.mvc.security.UsersContextHolder;
import com.yang.mvc.security.annotation.AnnotationUtil;
import com.yang.mvc.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

public class LoginInterceptorHandler implements HandlerInterceptor {
    private static final UsersCacheService usersCacheService = ApplicationContextAwareImpl.getBean(UsersCacheService.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (AnnotationUtil.containUrlPass(handler)) {
            return true;
        }
        String token = request.getHeader(JwtUtils.AUTH_HEADER);
        if (StringUtils.isEmpty(token)) {
            unAuthentication(response);
            return false;
        }
        token = StringUtils.removeStart(token, JwtUtils.TOKEN_PREFIX);
        if (!usersCacheService.hasJWTToken(token)){
            unAuthentication(response);
            return false;
        }
        // TODO 模拟 principal
        setUserInfo(token);
        refreshToken(token, response);
        return true;
    }

    private void unAuthentication(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(Result.error(ResultCode.UNAUTHENTICATION)));
    }

    private void setUserInfo(String token) {
        String subject = JwtUtils.getSubject(token);
        UsersContextHolder.setUsersName(subject);
    }

    private void refreshToken(String token, HttpServletResponse response) {
//        if (!isAlmostExpired(token)) {
//            return;
//        }
        String newToken = JwtUtils.refreshTokenExpired(token);
        LoginSuccessUserInfoVo loginSuccessUserInfoVo = usersCacheService.getLoginSuccessUserInfoVo(token);
        if (ObjectUtils.isEmpty(loginSuccessUserInfoVo)) {
            return;
        }
        response.setHeader(JwtUtils.REFRESH_TOKEN, JwtUtils.TOKEN_PREFIX + newToken);
        usersCacheService.insertJWTToken(newToken, loginSuccessUserInfoVo);
        UsersContextHolder.setToken(token);
    }

    private boolean isAlmostExpired(String token) {
        Date expiresAt = JwtUtils.getExpiresAt(token);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expiresAt);
        calendar.add(Calendar.MILLISECOND, -(int) JwtUtils.REFRESH_TIME);
        return calendar.after(new Date());
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String token = UsersContextHolder.getToken();
        if (StringUtils.isEmpty(token)){
            return;
        }
        usersCacheService.deleteJWTToken(token);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
