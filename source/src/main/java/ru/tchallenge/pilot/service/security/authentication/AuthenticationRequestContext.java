package ru.tchallenge.pilot.service.security.authentication;

import java.util.Optional;

import spark.Request;

public class AuthenticationRequestContext {

    private static final String AUTHENTICATION_ATTRIBUTE_KEY = "tchallenge-authentication";

    private final Request request;

    public AuthenticationRequestContext(Request request) {
        this.request = request;
    }

    public boolean isAuthenticationAvailable() {
        return getAuthenticationIfAvailable().isPresent();
    }

    public Authentication getAuthentication() {
        return getAuthenticationIfAvailable().orElseThrow(this::notAuthenticated);
    }

    public Optional<Authentication> getAuthenticationIfAvailable() {
        return Optional.ofNullable(this.request.attribute(AUTHENTICATION_ATTRIBUTE_KEY));
    }

    public void removeAuthentication() {
        this.request.attribute(AUTHENTICATION_ATTRIBUTE_KEY, null);
    }

    public void setAuthentication(Authentication authentication) {
        this.request.attribute(AUTHENTICATION_ATTRIBUTE_KEY, authentication);
    }

    private RuntimeException notAuthenticated() {
        return new RuntimeException("No authentication available");
    }
}
