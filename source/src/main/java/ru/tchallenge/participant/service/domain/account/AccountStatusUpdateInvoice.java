package ru.tchallenge.participant.service.domain.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountStatusUpdateInvoice {

    private String newStatus;

    public boolean isValid() {
        return newStatus != null && newStatus.equals("DELETED");
    }
}
