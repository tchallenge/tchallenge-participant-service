package ru.tchallenge.pilot.service.security.authentication;

import java.util.Collection;

import spark.Request;
import spark.Response;

import com.google.common.collect.Sets;

import ru.tchallenge.pilot.service.security.token.SecurityToken;
import ru.tchallenge.pilot.service.security.token.SecurityTokenContext;
import ru.tchallenge.pilot.service.utility.http.SimpleEndpointSignature;
import ru.tchallenge.pilot.service.utility.serialization.Json;

public final class AuthenticationInterceptorBean implements AuthenticationInterceptor {

    public static final AuthenticationInterceptorBean INSTANCE = new AuthenticationInterceptorBean();

    private final Collection<SimpleEndpointSignature> authorizationByInvoice;
    private final String authorizationHeaderName = "Authorization";
    private final String authorizationHeaderPrefix = "BEARER ";
    private final AuthenticationManager authenticationManager = AuthenticationManager.INSTANCE;
    private final SecurityTokenContext securityTokenContext = SecurityTokenContext.INSTANCE;

    private AuthenticationInterceptorBean() {
        authorizationByInvoice = Sets.newHashSet(SimpleEndpointSignature.builder().method("POST").uri("/security/tokens/").build());
    }

    @Override
    public void after(final Request request, final Response response) {
        provideNewTokenPayloadIfPossible(response);
    }

    @Override
    public void before(final Request request, final Response response) {
        final Authentication authentication = createAuthentication(request);
        new AuthenticationRequestContext(request).setAuthentication(authentication);
    }

    private void provideNewTokenPayloadIfPossible(final Response response) {
        if (securityTokenContext.isNewTokenCreated()) {
            final SecurityToken token = securityTokenContext.getCreatedToken();
            response.header(authorizationHeaderName, authorizationHeaderPrefix + token.getPayload());
        }
    }

    private Authentication createAuthentication(final Request request) {
        if (shouldAuthenticateByInvoice(request)) {
            return authenticateByInvoice(request);
        } else {
            return authenticateByTokenPayloadIfPossible(request);
        }
    }

    private boolean shouldAuthenticateByInvoice(final Request request) {
        return authorizationByInvoice
                .stream()
                .anyMatch((s) -> s.matches(request));
    }

    private Authentication authenticateByInvoice(final Request request) {
        final AuthenticationInvoice invoice = Json.body(AuthenticationInvoice.class, request);
        switch (invoice.getMethod()) {
            case PASSWORD:
                return authenticationManager.authenticateByPassword(invoice);
            case VOUCHER:
                return authenticationManager.authenticateByVoucher(invoice);
            default:
                throw authenticationMethodUnknown();
        }
    }

    private Authentication authenticateByTokenPayloadIfPossible(final Request request) {
        final String header = request.headers(authorizationHeaderName);
        if (header == null || header.isEmpty() || !header.startsWith(authorizationHeaderPrefix)) {
            return null;
        }
        final String tokenPayload = header.split(" ")[1];
        try {
            return authenticationManager.authenticateByToken(tokenPayload);
        } catch (final Exception exception) {
            return null;
        }
    }

    private RuntimeException authenticationMethodUnknown() {
        return new RuntimeException("Authentication method is not supported");
    }
}
