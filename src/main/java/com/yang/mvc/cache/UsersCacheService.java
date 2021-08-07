package com.yang.mvc.cache;

import com.yang.mvc.common.vo.LoginSuccessUserInfoVo;
import com.yang.mvc.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UsersCacheService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 进行数据验证
     *
     * @param token
     * @param loginSuccessUserInfoVo
     */
    public void insertJWTToken(String token, LoginSuccessUserInfoVo loginSuccessUserInfoVo) {
        redisTemplate.opsForValue().set(KeyMap.JWT_TOKEN + token, loginSuccessUserInfoVo);
        redisTemplate.expire(KeyMap.JWT_TOKEN + token, JwtUtils.EXPIRE_TIME, TimeUnit.MILLISECONDS);
    }

    public boolean hasJWTToken(String token) {
        return redisTemplate.hasKey(KeyMap.JWT_TOKEN + StringUtils.removeStart(token, JwtUtils.TOKEN_PREFIX));
    }
}
