package ru.tchallenge.participant.service.security.voucher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecurityVoucherInvoice {

    private String email;
    private String backlink;

    public boolean isValid() {
        return email != null && backlink != null;
    }
}
