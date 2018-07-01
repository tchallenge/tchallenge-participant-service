package ru.tchallenge.pilot.service.security.authorization;

import spark.Request;

import ru.tchallenge.pilot.service.security.authentication.Authentication;
import ru.tchallenge.pilot.service.security.authentication.AuthenticationRequestContext;

public final class AuthorizationManager {

    public static final AuthorizationManager INSTANCE = new AuthorizationManager();

    private AuthorizationManager() {

    }

    public void authorize(Request request, String affectedAccountId) {
        final Authentication authentication = new AuthenticationRequestContext(request).getAuthentication();
        if (!authentication.getAccountId().equals(affectedAccountId)) {
            throw notAuthorized();
        }
    }

    private RuntimeException notAuthorized() {
        return new RuntimeException("Not authorized");
    }
}
