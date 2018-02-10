package ru.tchallenge.participant.service.security.authentication;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

import spark.Request;

import com.google.common.collect.Sets;

import static ru.tchallenge.participant.service.utility.serialization.Json.body;

public final class AuthenticationInterceptor {

    public static final AuthenticationInterceptor INSTANCE = new AuthenticationInterceptor();

    private final Set<EndpointSignature> authorizationByInvoice;
    private final String authorizationHeaderName = "Authorization";
    private final String authorizationHeaderPrefix = "BEARER ";
    private final AuthenticationContext authenticationContext = AuthenticationContext.INSTANCE;
    private final AuthenticationManager authenticationManager = AuthenticationManager.INSTANCE;

    private AuthenticationInterceptor() {
        authorizationByInvoice = Sets.newHashSet(new EndpointSignature("POST", "/security/tokens/"));
    }

    public void authenticate(final Request request) {
        final Authentication authentication = createAuthentication(request);
        authenticationContext.setAuthentication(authentication);
    }

    private Authentication createAuthentication(final Request request) {
        if (shouldAuthenticateByInvoice(request)) {
            return authenticateByInvoice(request);
        } else {
            return authenticateByTokenPayloadIfPossible(request);
        }
    }

    private boolean shouldAuthenticateByInvoice(final Request request) {
        return authorizationByInvoice.contains(EndpointSignature.of(request));
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

    @Data
    @Builder
    private static final class EndpointSignature {

        static EndpointSignature of(final Request request) {
            return EndpointSignature.builder().method(request.requestMethod()).uri(request.uri()).build();
        }

        private final String method;
        private final String uri;
    }
}
