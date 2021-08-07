package com.yang.mvc.controller;

import com.yang.mvc.cache.UsersCacheService;
import com.yang.mvc.common.Result;
import com.yang.mvc.common.ResultCode;
import com.yang.mvc.common.vo.LoginSuccessUserInfoVo;
import com.yang.mvc.common.vo.LoginUserInfoVo;
import com.yang.mvc.security.annotation.authentication.UrlPass;
import com.yang.mvc.service.UsersService;
import com.yang.mvc.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersCacheService usersCacheService;

    /**
     * 用户注册
     *
     * @param loginUserInfoVo
     * @return
     */
    @UrlPass
    @PostMapping("/users")
    public Result users(@RequestBody LoginUserInfoVo loginUserInfoVo) {
        usersService.insert(loginUserInfoVo);
        return Result.success();
    }

    /**
     * 登陆
     *
     * @param loginUserInfoVo
     * @param response
     * @return
     */
    @UrlPass
    @PostMapping("/users/login")
    public Result login(@RequestBody LoginUserInfoVo loginUserInfoVo, HttpServletResponse response) {
        LoginSuccessUserInfoVo user = usersService.loadUserByUsername(loginUserInfoVo);
        if (ObjectUtils.isEmpty(user)) {
            return Result.error(ResultCode.UNAUTHENTICATION);
        }
        String token = JwtUtils.sign(user.getUsername());
        response.setHeader(JwtUtils.AUTH_HEADER, JwtUtils.TOKEN_PREFIX + token);
        usersCacheService.insertJWTToken(token,user);
        return Result.success(user);
    }
}
