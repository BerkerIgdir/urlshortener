package com.urlshortener.config.security.usercontext;


import java.util.Objects;
import java.util.Optional;

public class UserContextHolder {
    private static final ThreadLocal<UserContext> userContextThreadLocal = new ThreadLocal<>();

    public static UserContext getUserContextThreadLocal() {
        return Optional.ofNullable(userContextThreadLocal.get())
                .orElseGet(UserContext::new);
    }

    public static void setUserContextThreadLocal(final UserContext userContext) {
        Objects.requireNonNull(userContext);
        userContextThreadLocal.set(userContext);
    }

    public static void clean() {
        userContextThreadLocal.remove();
    }
}
