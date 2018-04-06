package ru.tchallenge.participant.service.security.authentication;

import java.util.Objects;
import java.util.Optional;

public final class AuthenticationContextBean implements AuthenticationContext {

    public static final AuthenticationContextBean INSTANCE = new AuthenticationContextBean();

    private final ThreadLocal<Authentication> authenticationContainer = new ThreadLocal<>();

    private AuthenticationContextBean() {

    }

    @Override
    public boolean isAuthenticationAvailable() {
        return getAuthenticationIfAvailable().isPresent();
    }

    @Override
    public Authentication getAuthentication() {
        return getAuthenticationIfAvailable().orElseThrow(this::notAuthenticated);
    }

    @Override
    public Optional<Authentication> getAuthenticationIfAvailable() {
        return Optional.ofNullable(authenticationContainer.get());
    }

    @Override
    public void removeAuthentication() {
        authenticationContainer.remove();
    }

    @Override
    public void setAuthentication(final Authentication authentication) {
        Objects.requireNonNull(authentication, "Authentication cannot be null");
        authenticationContainer.set(authentication);
    }

    private RuntimeException notAuthenticated() {
        return new RuntimeException("No authentication available");
    }
}
