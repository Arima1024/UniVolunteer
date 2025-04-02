package com.univolunteer.common.context;

import com.univolunteer.common.domain.dto.UserInfoDTO;

public class UserContext {
    private static final ThreadLocal<UserInfoDTO> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void set(UserInfoDTO userInfo) {
        USER_THREAD_LOCAL.set(userInfo);
    }

    public static UserInfoDTO get() {
        return USER_THREAD_LOCAL.get();
    }

    public static Long getUserId() {
        return get() != null ? get().getUserId() : null;
    }

    public static String getUsername() {
        return get() != null ? get().getUsername() : null;
    }

    public static void clear() {
        USER_THREAD_LOCAL.remove();
    }
}
