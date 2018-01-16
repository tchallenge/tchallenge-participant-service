package ru.tchallenge.participant.service.security.authentication;

import lombok.Data;
import ru.tchallenge.participant.service.utility.validation.ValidationAware;

import java.util.Collection;

@Data
public final class AuthenticationInvoice implements ValidationAware {

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

    @Override
    public void registerViolations(final Collection<String> violations) {
        if ((isByPassword() && email != null && password != null) || (isByVoucher() && voucherPayload != null)) {
            violations.add("Authentication invoice is invalid");
        }
    }
}
