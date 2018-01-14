package ru.tchallenge.participant.service.security.authentication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Authentication {

    private final String accountId;
    private final String accountEmail;
    private final String method;
    private final String tokenPayload;
    private final String voucherPayload;

    public boolean isByPassword() {
        return method.equals("password");
    }

    public boolean isByToken() {
        return method.equals("token");
    }

    public boolean isByVoucher() {
        return method.equals("voucher");
    }
}
