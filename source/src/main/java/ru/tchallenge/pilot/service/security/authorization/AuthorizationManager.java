package ru.tchallenge.pilot.service.security.authorization;

import ru.tchallenge.pilot.service.security.authentication.Authentication;
import ru.tchallenge.pilot.service.security.authentication.AuthenticationContext;
import ru.tchallenge.pilot.service.security.authentication.AuthenticationContextBean;

public final class AuthorizationManager {

    public static final AuthorizationManager INSTANCE = new AuthorizationManager();

    private final AuthenticationContext authenticationContext = AuthenticationContextBean.INSTANCE;

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
