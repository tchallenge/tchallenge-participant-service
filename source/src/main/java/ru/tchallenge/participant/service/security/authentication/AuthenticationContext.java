package ru.tchallenge.participant.service.security.authentication;

public final class AuthenticationContext {

    public static boolean isAuthenticated() {
        return AUTHENTICATION_CONTAINER.get() != null;
    }

    public static Authentication getAuthentication() {
        Authentication result = AUTHENTICATION_CONTAINER.get();
        if (result == null) {
            throw new RuntimeException("Not authenticated");
        }
        return result;
    }

    public static void setAuthentication(Authentication authentication) {
        AUTHENTICATION_CONTAINER.set(authentication);
    }

    private static final ThreadLocal<Authentication> AUTHENTICATION_CONTAINER = new ThreadLocal<>();
}
