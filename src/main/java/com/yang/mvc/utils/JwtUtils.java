package com.yang.mvc.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;

public class JwtUtils {
    // 过期时间5分钟
    public static final long EXPIRE_TIME = 30 * 60 * 1000;

    public static final long REFRESH_TIME = 5 * 30 * 1000;

    // 私钥
    public static String SECRET = "SECRET_YANG";

    // 请求头
    public static final String AUTH_HEADER = "Authentication";

    public static final String REFRESH_TOKEN = "Refresh_Token";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static String getSubject(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

    public static String sign(String username) {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        Date expireDate = new Date(time + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create().withSubject(username).withIssuedAt(date).withExpiresAt(expireDate).sign(algorithm);
    }

    public static Date getIssuedAt(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getIssuedAt();
    }

    public static Date getExpiresAt(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt();
    }


    /**
     * 验证 token是否过期
     */
    public static boolean isTokenExpired(String token) {
        Date now = Calendar.getInstance().getTime();
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().before(now);
    }

    /**
     * 刷新 token的过期时间
     */
    public static String refreshTokenExpired(String token) {
        DecodedJWT jwt = JWT.decode(token);
        String subject = jwt.getSubject();
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        Date expire = new Date(time + EXPIRE_TIME);
        JWTCreator.Builder builer = JWT.create().withExpiresAt(expire).withIssuedAt(date);
        builer.withSubject(subject);
        return builer.sign(algorithm);
    }
}
