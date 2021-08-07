package com.yang.mvc.security;

public class UsersContextHolder implements AutoCloseable {
    private static final ThreadLocal<String> username = new ThreadLocal<>();
    private static final ThreadLocal<String> token = new ThreadLocal<>();

    public static void setUsersName(String username) {
        UsersContextHolder.username.set(username);
    }

    public static String getUsersName() {
        return username.get();
    }

    public static void setToken(String token) {
        UsersContextHolder.token.set(token);
    }

    public static String getToken() {
        return token.get();
    }

    @Override
    public void close() throws Exception {
        username.remove();
        token.remove();
    }
}
