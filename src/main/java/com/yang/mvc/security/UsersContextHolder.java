package com.yang.mvc.security;

public class UsersContextHolder implements AutoCloseable {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setUsersName(String username) {
        contextHolder.set(username);
    }

    public static String getUsersName() {
        return contextHolder.get();
    }

    @Override
    public void close() throws Exception {
        contextHolder.remove();
    }
}
