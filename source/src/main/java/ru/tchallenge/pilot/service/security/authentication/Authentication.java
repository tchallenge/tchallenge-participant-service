package ru.tchallenge.pilot.service.security.authentication;

import lombok.Builder;
import lombok.Data;

/**
 * Descriptor of a successful authentication.
 *
 * @author Ilia Gubarev
 */
@Data
@Builder
public final class Authentication {

    private final AuthenticationMethod method;
    private final String accountId;
    private final String accountEmail;
    private final String generatedTokenPayload;
    private final boolean passwordUpdated;
    private final String tokenPayload;
    private final String voucherPayload;
}
