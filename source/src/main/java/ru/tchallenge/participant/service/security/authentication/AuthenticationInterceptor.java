package ru.tchallenge.participant.service.security.authentication;

import java.util.Set;

import spark.Request;
import spark.Response;

import com.google.common.collect.Sets;

import ru.tchallenge.participant.service.security.token.SecurityToken;
import ru.tchallenge.participant.service.security.token.SecurityTokenContext;
import ru.tchallenge.participant.service.utility.http.SimpleEndpointSignature;
import static ru.tchallenge.participant.service.utility.serialization.Json.body;

public final class AuthenticationInterceptor {

    public static final AuthenticationInterceptor INSTANCE = new AuthenticationInterceptor();

    private final Set<SimpleEndpointSignature> authorizationByInvoice;
    private final String authorizationHeaderName = "Authorization";
    private final String authorizationHeaderPrefix = "BEARER ";
    private final AuthenticationContext authenticationContext = AuthenticationContextBean.INSTANCE;
    private final AuthenticationManager authenticationManager = AuthenticationManager.INSTANCE;
    private final SecurityTokenContext securityTokenContext = SecurityTokenContext.INSTANCE;

    private AuthenticationInterceptor() {
        authorizationByInvoice = Sets.newHashSet(SimpleEndpointSignature.builder().method("POST").uri("/security/tokens/").build());
    }

    public void before(final Request request, final Response response) {
        final Authentication authentication = createAuthentication(request);
        authenticationContext.setAuthentication(authentication);
    }

    public void after(final Request request, final Response response) {
        provideNewTokenPayloadIfPossible(response);
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
        return authorizationByInvoice.contains(SimpleEndpointSignature.of(request));
    }

    private Authentication authenticateByInvoice(final Request request) {
        final AuthenticationInvoice invoice = body(AuthenticationInvoice.class, request);
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
