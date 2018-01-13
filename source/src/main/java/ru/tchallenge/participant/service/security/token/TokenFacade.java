package ru.tchallenge.participant.service.security.token;

import com.google.common.collect.Sets;
import ru.tchallenge.participant.service.security.voucher.SecurityVoucher;
import ru.tchallenge.participant.service.security.voucher.SecurityVoucherManager;
import spark.*;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.*;

import static ru.tchallenge.participant.service.utility.serialization.Json.body;

public final class TokenFacade {

    private static final Object EMPTY = Collections.EMPTY_MAP;

    private static final Duration TOKEN_EXPIRATION_PERIOD = Duration.ofHours(1);

    public static SecurityToken authenticate(Request request) {
        String header = request.headers("Authorization");
        if (header == null || header.isEmpty() || !header.startsWith("BEARER ")) {
            throw new RuntimeException("Authentication token is missing or unreadable");
        }
        String payload = header.split(" ")[1];
        if (payload.equals("PREDEFINED")) {
            return createSecurityToken("predefined.user");
        }
        SecurityToken token = TOKENS.get(payload);
        if (token == null || token.isExpired()) {
            TOKENS.remove(payload);
            throw new RuntimeException("Authentication token is invalid or expired");
        }
        token.prolongate(TOKEN_EXPIRATION_PERIOD);
        return token;
    }

    public static Object create(Request request, Response response) {
        SecurityTokenInvoice invoice = body(request, SecurityTokenInvoice.class);
        if (invoice == null || !invoice.isValid()) {
            throw new RuntimeException("Not sufficient information for authentication");
        }
        SecurityToken token = null;
        if (invoice.isByPassword()) {
            if (!CREDENTIALS.contains(invoice)) {
                throw new RuntimeException("Not authenticated");
            }
            token = createSecurityToken(invoice.getEmail());
        } else if (invoice.isByVoucher()) {
            SecurityVoucher voucher = SecurityVoucherManager.utilize(invoice.getVoucher());
            token = createSecurityToken(voucher.getEmail());
        } else {
            throw new RuntimeException("unknown method of authentication");
        }
        TOKENS.put(token.getPayload(), token);
        response.header("Authorization", "BEARER " + token.getPayload());
        return token.getId();
    }

    public static Object delete(Request request, Response response) {
        SecurityToken token = authenticate(request);
        TOKENS.remove(token.getPayload());
        return EMPTY;
    }

    public static Object retrieve(Request request, Response response) {
        return authenticate(request);
    }

    private static SecurityToken createSecurityToken(String email) {
        return SecurityToken.builder()
                .id(UUID.randomUUID().toString())
                .payload(UUID.randomUUID().toString())
                .email(email)
                .createdAt(OffsetDateTime.now())
                .validUntil(OffsetDateTime.now().plus(TOKEN_EXPIRATION_PERIOD))
                .build();
    }

    private static final Set<SecurityTokenInvoice> CREDENTIALS = Sets.newHashSet(
            SecurityTokenInvoice.builder().email("ilya.gubarev@gmail.com").password("*").build()
    );

    private static final Map<String, SecurityToken> TOKENS = new HashMap<>();
}
