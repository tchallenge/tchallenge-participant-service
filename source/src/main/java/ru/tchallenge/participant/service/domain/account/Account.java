package ru.tchallenge.participant.service.domain.account;

import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.utility.data.IdAware;

import java.time.Instant;

@Data
@Builder
public final class Account implements IdAware {

    private final String id;
    private final String email;
    private final String passwordHash;
    private final AccountPersonality personality;
    private final String status;
    private final Instant registeredAt;
}
