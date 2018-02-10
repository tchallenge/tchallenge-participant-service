package ru.tchallenge.participant.service.security.authentication;

public final class AuthenticationContext {

    public static final AuthenticationContext INSTANCE = new AuthenticationContext();

    private final ThreadLocal<Authentication> authenticationContainer = new ThreadLocal<>();

    private AuthenticationContext() {

    }

    public boolean isAuthenticated() {
        return authenticationContainer.get() != null;
    }

    public Authentication getAuthentication() {
        final Authentication result = authenticationContainer.get();
        if (result == null) {
            throw notAuthenticated();
        }
        return result;
    }

    public void setAuthentication(final Authentication authentication) {
        authenticationContainer.set(authentication);
    }

    private RuntimeException notAuthenticated() {
        return new RuntimeException("Not authenticated");
    }
}
