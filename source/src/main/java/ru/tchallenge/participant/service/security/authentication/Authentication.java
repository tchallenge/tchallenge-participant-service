package ru.tchallenge.participant.service.security.authentication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Authentication {

    private final AuthenticationMethod method;
    private final String accountId;
    private final String accountEmail;
    private final String tokenPayload;
    private final String voucherPayload;
}
