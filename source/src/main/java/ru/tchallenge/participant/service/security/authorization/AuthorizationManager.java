package ru.tchallenge.participant.service.security.authorization;

import ru.tchallenge.participant.service.security.authentication.Authentication;
import ru.tchallenge.participant.service.security.authentication.AuthenticationContext;

public final class AuthorizationManager {

    public static final AuthorizationManager INSTANCE = new AuthorizationManager();

    private final AuthenticationContext authenticationContext = AuthenticationContext.INSTANCE;

    private AuthorizationManager() {

    }

    public void authorize(final String affectedAccountId) {
        final Authentication authentication = authenticationContext.getAuthentication();
        if (!authentication.getAccountId().equals(affectedAccountId)) {
            throw notAuthorized();
        }
    }

    private RuntimeException notAuthorized() {
        return new RuntimeException("Not authorized");
    }
}
