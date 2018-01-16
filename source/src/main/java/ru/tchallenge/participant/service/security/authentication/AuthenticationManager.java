package ru.tchallenge.participant.service.security.authentication;

public final class AuthenticationManager {

    public static final AuthenticationManager INSTANCE = new AuthenticationManager();

    public void authorize(final String affectedAccountId) {
        if (!AuthenticationContext.getAuthentication().getAccountId().equals(affectedAccountId)) {
            throw new RuntimeException("Not authorized");
        }
    }

    private AuthenticationManager() {

    }
}
