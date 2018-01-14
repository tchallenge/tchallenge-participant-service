package ru.tchallenge.participant.service.security.token;

import ru.tchallenge.participant.service.security.authentication.Authentication;
import ru.tchallenge.participant.service.security.authentication.AuthenticationContext;
import spark.*;

import java.util.*;

public final class TokenFacade {

    private static final Object EMPTY = Collections.EMPTY_MAP;

    public static SecurityToken create(final Request request, final Response response) {
        final Authentication authentication = AuthenticationContext.getAuthentication();
        return TokenManager.create(authentication.getAccountId());
    }

    public static Object delete(final Request request, final Response response) {
        final Authentication authentication = AuthenticationContext.getAuthentication();
        TokenManager.deleteByPayload(authentication.getTokenPayload());
        return EMPTY;
    }

    public static SecurityToken retrieve(final Request request, final Response response) {
        final Authentication authentication = AuthenticationContext.getAuthentication();
        return TokenManager.retrieveByPayload(authentication.getTokenPayload());
    }
}
