package ru.tchallenge.participant.service.security.authentication;

import lombok.Data;

@Data
public final class AuthenticationInvoice {

    private String method;
    private String email;
    private String password;
    private String voucherPayload;

    public boolean isByPassword() {
        return method != null && method.equalsIgnoreCase("password");
    }

    public boolean isByVoucher() {
        return method != null && method.equalsIgnoreCase("voucher");
    }

    public boolean isValid() {
        return (isByPassword() && email != null && password != null) ||
                (isByVoucher() && voucherPayload != null);
    }
}
