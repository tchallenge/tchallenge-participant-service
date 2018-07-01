package ru.tchallenge.pilot.service.security.authentication;

import java.util.Collection;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.pilot.service.utility.validation.ValidationAware;

@Data
@Builder
public final class AuthenticationInvoice implements ValidationAware {

    private final AuthenticationMethod method;
    private final String email;
    private final String password;
    private final String passwordUpdate;
    private final String tokenPayload;
    private final String voucherPayload;

    public boolean isPasswordUpdateRequested() {
        return method == AuthenticationMethod.VOUCHER && passwordUpdate != null;
    }

    @Override
    public void registerViolations(final Collection<String> violations) {
        if (method == null) {
            violations.add("Authentication method is missing or unknown");
        }
        if ((method == AuthenticationMethod.PASSWORD && (email == null || password == null))
                || (method == AuthenticationMethod.VOUCHER && voucherPayload == null)) {
            violations.add("Authentication invoice is invalid");
        }
    }
}
