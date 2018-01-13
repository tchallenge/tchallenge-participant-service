package ru.tchallenge.participant.service.security.token;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecurityTokenInvoice {

    private String method;
    private String email;
    private String password;
    private String voucher;

    public boolean isByPassword() {
        return method != null && method.equalsIgnoreCase("password");
    }

    public boolean isByVoucher() {
        return method != null && method.equalsIgnoreCase("voucher");
    }

    public boolean isValid() {
        return (isByPassword() && email != null && password != null) ||
                (isByVoucher() && voucher != null);
    }
}
